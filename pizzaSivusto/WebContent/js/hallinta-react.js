// Palauttaa yksittäisen täytetaulukon rivin
var Tayte = React.createClass({
	muokkaaTaytetta: function() {
		var tayte = {id: this.props.id, nimi: this.props.nimi, saatavilla: this.props.saatavilla };
		this.props.muokkaaTaytetta(tayte);
	},
	render: function() {
		var saatavilla = "";
		var tyyli = {};
		if (this.props.saatavilla == true) {
			saatavilla = "Saatavilla";
			tyyli = {"className": "taulukkorivi"}
		}
		else {
			saatavilla = "Ei saatavilla";
			tyyli = {"className": "taulukkorivi red lighten-5"};
		}
		return (
			<tr {... tyyli}>
			<td>{this.props.nimi }</td>
			<td>{saatavilla }</td>
			<td><button className="waves-effect waves-light btn tooltipped right" onClick={this.muokkaaTaytetta }  data-position="right" data-delay="500" data-tooltip="Muokkaa täytettä"><i className="material-icons">edit</i></button></td>
			</tr>
		);
	}
});

// Palauttaa kaikkien täytteiden taulukon
var Taytelista = React.createClass({
	render: function() {
		return (
			<div className="col s12 m12 l6 pull-l5">
			<h2>Täytteet</h2>
			<table id="taytelista" className="bordered">
			<thead>
			<tr>
			<th>Täyte</th>
			<th>Saatavuus</th>
			<th></th>
			</tr>
			</thead>
			<tbody>
			{this.props.taytteet.map((o, i) => <Tayte key={o.id} id={o.id} nimi={o.nimi} saatavilla={o.saatavilla} muokkaaTaytetta={this.props.muokkaaTaytetta }/>)}
			</tbody>
			</table>
			</div>
		);
	}
});

// Täytteen lisäys formi
var TaytteenLisays = React.createClass({
	getInitialState: function() {
		return ({taytenimi: ""});
	},
	componentDidMount: function() {
		$("#submittayte").attr("disabled", true);
	},
	paivitanimi: function(e) {
		this.setState({taytenimi: e.target.value }, function() { this.paivitanappi() });
	},
	paivitanappi: function() {
		if (this.state.taytenimi.length > 2) {
			$("#submittayte").attr("disabled", false);
		}
		else {
			$("#submittayte").attr("disabled", true);
		}
	},
	componentWillReceiveProps: function(propsit) {
		if (propsit.tayteLisaysStatus) {
			if (propsit.tayteLisaysStatus == "success") {
				this.setState({taytenimi: ""}, function() { this.paivitanappi() });
			}
		}
	},
	render: function() {
		return (
			<div className="col s12 m12 l5 push-l7 " id="taytel">
			<h2>Lisää täyte</h2>
			<div className="row">
			<form id="tayteformi">
			<div className="row">
			<div className="col s12 input-field">
			<input type="text" name="taytenimi" id="taytenimi"
			className="fieldi" value={this.state.taytenimi } onChange={this.paivitanimi }/> <label
			htmlFor="taytenimi">Täytteen nimi</label>
			</div>
			</div>
			<div className="row">
			<div className="col s6">
			<input name="taytesaatavilla" type="radio" id="saatavilla"
			value="1" defaultChecked />
			<label htmlFor="saatavilla">Saatavilla</label>
			</div>
			<div className="col s6">
			<input name="taytesaatavilla" type="radio" id="eisaatavilla"
			value="0" />
			<label htmlFor="eisaatavilla">Ei
			saatavilla</label>
			</div>
			</div>
			<div className="row">
			<div className="col s12">
			<button className="btn waves-effect waves-light btn-large"
			type="button" id="submittayte" onClick={this.props.lahetaLisays }>Lisää
			täyte</button>
			</div>
			</div>
			</form>
			</div>
			</div>
		);
	}
});

// Tiettyä täytettä käyttävät pizzat
var TaytettaKayttavatPizzat = React.createClass({
	render: function() {
		if (this.props.pizzat.length == 0) {
		return (
			<div className="col s12">
			<h5>Yksikään pizza ei käytä tätä täytettä</h5>
			</div>
		);
		}
		else {
			return (
				<div className="col s12">
				<h5>Tätä täytettä käyttävät pizzat</h5>
				<ul className="collection">
				{this.props.pizzat.map((o, i) => <li className="collection-item center-align" key={"rivi" + i}>{o.nimi}</li>)}
				</ul>
				</div>
			);
		}
	}
});

// Täytteen muokkaus formi
var TaytteenMuokkaus = React.createClass({
	getInitialState: function() {
		return ({taytenimi: this.props.tayte.nimi, taytesaatavilla: this.props.tayte.saatavilla, pizzalista: [] });
	},
	scrollaaKohdalle: function() {
		$('html, body').animate({scrollTop: $("#taytem").offset().top - 50}, 500);
	},
	componentDidMount: function() {
		this.haePizzat();
		this.scrollaaKohdalle();
	},
	haePizzat: function() {
		$.get("hallinta", {"pizzat-taytteella": this.props.tayte.id, "json": "true"}).done(
			function(json) {
				this.setState({pizzalista: json});
			}.bind(this)).fail(
				function(jqxhr, textStatus, error) {
					var errori = textStatus + ", " + error;
					console.log("Faili: " + errori);
					console.log(JSON.stringify(json));
					naytaVirhe("Virhe javascriptissa!")
				});
	},
	paivitanimi: function(e) {
		this.setState({taytenimi: e.target.value }, function() { this.paivitanappi() });
	},
	paivitanappi: function() {
		if (this.state.taytenimi.length > 2) {
			$("#paivitatayte").attr("disabled", false);
		}
		else {
			$("#paivitatayte").attr("disabled", true);
		}
	},
	componentWillReceiveProps: function(propsit) {
		if (propsit.tayte) {
			this.setState({taytenimi: propsit.tayte.nimi, taytesaatavilla: propsit.tayte.saatavilla}, function() { this.haePizzat() });
			this.scrollaaKohdalle();
		}
	},
	vaihdasaatavuus: function(e) {
		var value = e.target.value;
		if (value == 0) {
			this.setState({taytesaatavilla: false});
		}
		else {
			this.setState({taytesaatavilla: true});
		}
	},
	lahetaPaivitys: function() {
		var saatavuus = 0;
		if (this.state.taytesaatavilla === true) {
			saatavuus = 1;
		}
		var submitdata = [{name: "action", value: "paivitatayte"}, {name: "tayteid", value: this.props.tayte.id}, {name: "taytenimi", value: this.state.taytenimi}, {name: "taytesaatavilla", value: saatavuus}, {name: "json", value: "true"}];
		this.props.lahetaPaivitys(submitdata);
	},
	avaaModal: function() {
		$("#taytepoistomodal").openModal();
	},
	poistaTayte: function() {
		var submitdata = [{name: "action", value:"poistatayte"}, {name: "id", value: this.props.tayte.id}, {name: "json", value: "true"}];
		this.props.lahetaPaivitys(submitdata);
		$("#taytepoistomodal").closeModal();
	},
	render: function() {
		return (
			<div className="col s12 m12 l5 push-l7" id="taytem">
			<h2>Muokkaa täytettä<a onClick={this.avaaModal } href="#!" className="taytteenpoisto"><i className="material-icons">delete</i></a></h2>
			<h3>{this.props.tayte.nimi }</h3>
			<div className="row">
			<form id="tayteformi">
			<div className="row">
			<div className="col s12 input-field">
			<input type="text" name="taytenimi" id="taytenimi"
			className="fieldi" value={this.state.taytenimi } onChange={this.paivitanimi }/> <label
			htmlFor="taytenimi" className="active">Täytteen nimi</label>
			</div>
			</div>
			<div className="row">
			<div className="col s6">
			<input name="taytesaatavilla" type="radio" id="saatavilla"
			value="1" checked={this.state.taytesaatavilla === true} onChange={this.vaihdasaatavuus}/>
			<label htmlFor="saatavilla">Saatavilla</label>
			</div>
			<div className="col s6">
			<input name="taytesaatavilla" type="radio" id="eisaatavilla"
			value="0" checked={this.state.taytesaatavilla === false} onChange={this.vaihdasaatavuus}/>
			<label htmlFor="eisaatavilla">Ei
			saatavilla</label>
			</div>
			</div>
			<div className="row">
			<div className="col s12">
			<button className="btn waves-effect waves-light btn-large left red lighten-2"
			type="button" onClick={this.props.peruuta }>Peruuta</button>
			<button className="btn waves-effect waves-light btn-large right"
			type="button" id="paivitatayte" onClick={this.lahetaPaivitys }><i className="material-icons left">done</i>Päivitä</button>
			</div>
			<TaytettaKayttavatPizzat pizzat={this.state.pizzalista} />
			<div id="taytepoistomodal" className="modal">
			<div className="modal-content center-align">
			<h4>Oletko varma?</h4>
			<p className="center-align">Täyte '{this.props.tayte.nimi}' poistetaan pysyvästi</p>
			<br />
			<a href="#!" className="modal-action modal-close waves-effect waves-light btn red lighten-2">Peruuta</a> <button onClick={this.poistaTayte } type="button" className="modal-action waves-effect waves-light btn"><i className="material-icons left">delete</i> Poista</button>
			</div>
			</div>
			</div>
			</form>
			</div>
			</div>
		);
	}
});

// Palauttaa yksittäisen pizzataulukon rivin
var Pizza = React.createClass({
	pizzanPoisto: function() {
		var id = this.props.id;
		this.props.kasittelePizza({"pizza-poista": id, "json": "true" });
	},
	pizzanPalautus: function() {
		var id = this.props.id;
		this.props.kasittelePizza({"pizza-palauta": id, "json": "true" });
	},
	render: function() {
		var taytteet = "";
		for (var int = 0; int < this.props.taytteet.length; int++) {
			taytteet += this.props.taytteet[int].nimi;
			if ((int+1) != this.props.taytteet.length) {
				taytteet+= ", ";
			}
		}
		var poistomerkitty = {"className": "taulukkorivi"};
		var muokkaanappi = <a className="waves-effect waves-light btn tooltipped" href={"?pizza-edit=" + this.props.id } data-position="left" data-delay="500" data-tooltip="Muokkaa"><i className="material-icons">edit</i></a>;
		var poistonappi = "";
		if (this.props.poistomerkinta != null) {
			poistomerkitty = {"className": "taulukkorivi red lighten-5"};
			poistonappi = <button className="waves-effect waves-light btn red lighten-2 tooltipped" type="button" onClick={this.pizzanPalautus } data-position="right" data-delay="500" data-tooltip="Palauta"> <i className="material-icons large">visibility_off</i></button>;
		}
		else {
			poistonappi = <button className="waves-effect waves-light btn red lighten-2 tooltipped" type="button" onClick={this.pizzanPoisto } data-position="right" data-delay="500" data-tooltip="Poista"> <i className="material-icons large">delete</i></button>;
		}
		return (
			<tr {... poistomerkitty}>
			<td>{this.props.nimi }</td>
			<td className="pienifontti hide-on-small-only">{taytteet }</td>
			<td className="hide-on-small-only">{parseFloat(this.props.hinta).toFixed(2).replace(".",",") } €</td>
			<td className="right-align">{muokkaanappi } {poistonappi }</td>
			</tr>
		);
	}
});

// Juoman muokkaus
var JuomanMuokkaus = React.createClass({
	getInitialState: function() {
	return ({juomanimi: this.props.nimi, juomahinta: this.props.hinta, juomakoko: this.props.koko, juomasaatavilla: this.props.saatavilla, juomakuvaus: this.props.kuvaus});
	},
	paivitanimi: function(e) {
		this.setState({ juomanimi: e.target.value }, function(){ this.paivitanappi() });
	},
	paivitahinta: function(e) {
		this.setState({ juomahinta: e.target.value }, function(){ this.paivitanappi() });
	},
	paivitakoko: function(e) {
		this.setState({ juomakoko: e.target.value }, function(){ this.paivitanappi() });
	},
	paivitasaatavilla: function(e) {
		var value = e.target.value;
		if (value == 0) {
			this.setState({juomasaatavilla: false});
		}
		else {
			this.setState({juomasaatavilla: true});
		}
	},
	paivitakuvaus: function(e) {
		this.setState({ juomakuvaus: e.target.value }, function(){ this.paivitanappi() });
	},
	paivitanappi: function() {
		if (this.state.juomanimi.length > 2 && this.state.juomahinta > 0 && this.state.juomakuvaus.length > 0 && this.state.juomakoko > 0) {
			$("#paivitajuoma").attr("disabled", false);
		}
		else {
			$("#paivitajuoma").attr("disabled", true);
		}
	},
	componentDidMount: function() {
		this.avaaModal();
	},
	componentWillReceiveProps: function(propsit) {
		if (propsit.id && propsit.nimi && propsit.hinta && propsit.koko && propsit.kuvaus) {
			this.setState({juomanimi: propsit.nimi, juomahinta: propsit.hinta, juomakoko: propsit.koko, juomasaatavilla: propsit.saatavilla, juomakuvaus: propsit.kuvaus}, function() {
				this.paivitanappi();
				this.avaaModal();
			});
		}
	},
	lahetaPaivitys: function() {
		console.log("Lähetetään juoman päivitys!");
		var saatavuus = 0;
		if (this.state.juomasaatavilla === true) {
			saatavuus = 1;
		}
		var data = [{name: "action", value: "paivitajuoma"},
		{name: "juomaid", value: this.props.id},
		{name: "juomanimi", value: this.state.juomanimi},
		{name: "juomakoko", value: this.state.juomakoko},
		{name: "juomasaatavilla", value: saatavuus},
		{name: "juomahinta", value: this.state.juomahinta},
		{name: "juomasaatavilla", value: saatavuus},
		{name: "juomakuvaus", value: this.state.juomakuvaus},
		{name: "json", value: "true"}];
		this.props.lahetaPaivitys(data);
		$("#juomamodal").closeModal();
		this.lopetaMuokkaus();
	},
	lopetaMuokkaus: function() {
		this.props.lopetaMuokkaus();
	},
	avaaModal: function() {
		$("#juomamodal").openModal({complete: this.lopetaMuokkaus});
	},
	render: function() {
		return (
			<div id="juomamodal" className="modal">
			<div className="modal-content center-align">
			<div className="row">
			<h4>Muokkaa juomaa</h4>
			<h5 className="modalsubtitle">{this.props.nimi}</h5>
			<form id="juomanmuokkausformi">
			<div className="col s12 m12 l10 offset-l1">
			<div className="row">
			<div className="input-field col s12 m6 l6">
			<input type="text" name="juomanimi" id="juomanimi" value={this.state.juomanimi } onChange={this.paivitanimi }/>
			<label htmlFor="juomanimi" className="active">Juoman nimi</label>
			</div>
			<div className="input-field col s12 m3 l3">
			<input type="number" className="validate" min="0" step="0.01" name="juomakoko" id="juomakoko" value={this.state.juomakoko } onChange={this.paivitakoko }/>
			<label htmlFor="juomakoko" data-error="Virhe" className="active">Juoman koko</label>
			</div>
			<div className="input-field col s12 m3 l3">
			<input type="number" className="validate" min="0" step="0.05" name="juomahinta" id="juomahinta" value={this.state.juomahinta } onChange={this.paivitahinta }/>
			<label htmlFor="juomahinta" data-error="Virhe" className="active">Juoman hinta</label>
			</div>
			<div className="input-field col s12 m9 l9">
			<textarea className="materialize-textarea" name="pizzakuvaus" id="juomakuvaus" length="255" value={this.state.juomakuvaus } onChange={this.paivitakuvaus }></textarea>
			<label htmlFor="juomakuvaus" className="active">Juoman kuvaus</label>
			</div>
			<div className="input-field col s12 m3 l3 left-align">
			<label>Saatavuus</label><br />
			<input name="juomasaatavilla" type="radio" id="juomasaatavilla" value="1" checked={this.state.juomasaatavilla === true} onChange={this.paivitasaatavilla} />
			<label htmlFor="juomasaatavilla">Saatavilla</label>
			<br />
			<input name="juomasaatavilla" type="radio" id="juomaeisaatavilla" value="0" checked={this.state.juomasaatavilla === false} onChange={this.paivitasaatavilla} />
			<label htmlFor="juomaeisaatavilla">Ei saatavilla</label>
			</div>
			</div>
			</div>
			</form>
			</div>
			<a href="#!" className="modal-action modal-close waves-effect waves-light btn red lighten-2">Peruuta</a> <button onClick={this.lahetaPaivitys } id="paivitajuoma" type="button" className="modal-action waves-effect waves-light btn">Tallenna</button>
			</div>
			</div>
		);
	}
});

// Palauttaa yksittäisen juomataulukon rivin
var Juoma = React.createClass({
	juomanPoisto: function() {
		var id = this.props.id;
		this.props.kasittelePizza({"juoma-poista": id, "json": "true" });
	},
	juomanPalautus: function() {
		var id = this.props.id;
		this.props.kasittelePizza({"juoma-palauta": id, "json": "true" });
	},
	muokkaaJuomaa: function() {
		this.props.muokkaaJuomaa(this.props.id);
	},
	lahetaPaivitys: function(data) {
		this.props.kasittelePizza(data);
	},
	render: function() {
		var poistomerkitty = {"className": "taulukkorivi"};
		var muokkaanappi = <button className="waves-effect waves-light btn tooltipped" data-position="left" data-delay="500" data-tooltip="Muokkaa" onClick={this.muokkaaJuomaa}><i className="material-icons">edit</i></button>;
		var poistonappi = "";
		var saatavilla = <span className="errori">Ei</span>;
		if (this.props.saatavilla == true) {
			saatavilla = <span>Kyllä</span>;
		}
		if (this.props.poistomerkinta != null) {
			poistomerkitty = {"className": "taulukkorivi red lighten-5"};
			poistonappi = <button className="waves-effect waves-light btn red lighten-2 tooltipped" type="button" onClick={this.juomanPalautus } data-position="right" data-delay="500" data-tooltip="Palauta"> <i className="material-icons large">visibility_off</i></button>;
		}
		else {
			poistonappi = <button className="waves-effect waves-light btn red lighten-2 tooltipped" type="button" onClick={this.juomanPoisto } data-position="right" data-delay="500" data-tooltip="Poista"> <i className="material-icons large">delete</i></button>;
		}
		return (
			<tr {... poistomerkitty}>
			<td>{this.props.nimi }</td>
			<td className="hide-on-small-only">{parseFloat(this.props.koko).toFixed(2).replace(".",",") }l</td>
			<td className="hide-on-small-only">{saatavilla}</td>
			<td className="hide-on-small-only">{parseFloat(this.props.hinta).toFixed(2).replace(".",",") } €</td>
			<td className="right-align">{muokkaanappi } {poistonappi }</td>
			</tr>
		);
	}
});

// Nimestä huolimatta sisältää listan pizzoista sekä juomista
var Pizzalista = React.createClass({
	getInitialState: function() {
		return({muokattavajuoma: 0});
	},
	componentDidMount: function() {
		$('.tooltipped').tooltip({delay: 500});
	},
	avaaModal: function() {
		$("#poistomodal").openModal();
	},
	poistaValitut: function() {
		this.props.poistaValitut({ "poista-merkityt": "true", "json": "true" });
		$("#poistomodal").closeModal();
	},
	muokkaaJuomaa: function(id) {
		if (isNaN(id) === false) {
			this.setState({muokattavajuoma: id});
		}
	},
	lopetaMuokkaus: function() {
		this.setState({muokattavajuoma: 0});
	},
	render: function() {
		var nappistatus = {"disabled": "disabled"};
		if ((this.props.poistettavatpizzat + this.props.poistettavatjuomat) > 0) {
			nappistatus = {};
		}
		var juomanMuokkaus = "";
		if (this.state.muokattavajuoma > 0) {
		for (var i = 0; i < this.props.juomat.length; i++) {
			if (this.props.juomat[i].id == this.state.muokattavajuoma) {
				var juoma = this.props.juomat[i];
				var juomanMuokkaus = <JuomanMuokkaus id={juoma.id} nimi={juoma.nimi} hinta={juoma.hinta} saatavilla={juoma.saatavilla} koko={juoma.koko} kuvaus={juoma.kuvaus} lopetaMuokkaus={this.lopetaMuokkaus} lahetaPaivitys={this.props.muokkaaJuomaa}/>;
			}
		}
		}
		return (
			<div className="col s12">
			<div className="row">
			<div className="col s12">
			<h2>Pizzat</h2>
			<table className="bordered">
			<thead>
			<tr>
			<th>Nimi</th>
			<th className="hide-on-small-only">Täytteet</th>
			<th className="hide-on-small-only">Hinta</th>
			<th></th>
			</tr>
			</thead>
			<tbody>
			{this.props.pizzat.map((o, i) => <Pizza key={o.id} id={o.id} nimi={o.nimi} hinta={o.hinta } taytteet={o.taytteet } poistomerkinta={o.poistomerkinta} kasittelePizza={this.props.kasittelePizza } />)}
			</tbody>
			</table>
			<br />
			</div>
			<div className="col s12">
			<h2>Juomat</h2>
			<table className="bordered">
			<thead>
			<tr>
			<th>Nimi</th>
			<th className="hide-on-small-only">Koko</th>
			<th className="hide-on-small-only">Saatavilla</th>
			<th className="hide-on-small-only">Hinta</th>
			<th></th>
			</tr>
			</thead>
			<tbody>
			{this.props.juomat.map((o, i) => <Juoma key={o.id} id={o.id} nimi={o.nimi} hinta={o.hinta } poistomerkinta={o.poistomerkinta} kasittelePizza={this.props.kasittelePizza } koko={o.koko} saatavilla={o.saatavilla} kuvaus={o.kuvaus} muokkaaJuomaa={this.muokkaaJuomaa}/>)}
			</tbody>
			</table>
			<br />
			<div className="col s12 m6 l6 push-m6 push-l6 small-centteri right-align">
			<button className="waves-effect waves-light btn modal-trigger red lighten-2 tooltipped" {...nappistatus} type="button" onClick={this.avaaModal } data-position="bottom" data-delay="500" data-tooltip="Poista merkityt tuotteet pysyvästi"><i className="material-icons left">delete</i> Poista merkityt</button>
			<div id="poistomodal" className="modal">
			<div className="modal-content center-align">
			<h4>Oletko varma?</h4>
			<p>Poistettavaksi merkityt pizzat ({this.props.poistettavatpizzat }kpl) ja juomat ({this.props.poistettavatjuomat}kpl) poistetaan tietokannasta pysyvästi.</p>
			<a href="#!" className="modal-action modal-close waves-effect waves-light btn red lighten-2">Peruuta</a> <button onClick={this.poistaValitut } type="button" className="modal-action waves-effect waves-light btn"><i className="material-icons left">delete</i> Poista</button>
			</div>
			</div>
			{juomanMuokkaus}
			</div>
			</div>
			</div>
			</div>
		);
	}
});

// Palauttaa yksittäisen täytteen checkbox rivin pizzan lisäystä varten
var TayteCheckbox = React.createClass({
	render: function() {
		var tyyli = {};
		if (this.props.saatavilla != true) {
			tyyli = {"className": "errori-light"};
		}
		return (
			<div className="col s6 m4 l3 taytediv">
			<input type="checkbox" id={this.props.id } value={this.props.id} name="pizzatayte" onChange={this.props.laskeValitut }/>
			<label {... tyyli} htmlFor={this.props.id }>{this.props.nimi }</label>
			</div>
		);
	}
});

// Pizzan lisäysformi
var PizzanLisays = React.createClass({
	getInitialState: function() {
		return ({valittuja: 0, pizzanimi: "", pizzahinta: "", pizzakuvaus: ""});
	},
	componentDidMount: function() {
		$('textarea#pizzakuvaus').characterCounter();
		$("#submitpizza").attr("disabled", true);
	},
	laskeValitut: function() {
		var valittuja = $("#pizza-taytteet input[name='pizzatayte']:checked").length;
		this.setState({valittuja: valittuja }, function(){ this.paivitanappi() });
		if (valittuja > 4) {
			$("#pizza-taytteet input:checkbox:not(:checked)")
			.each(function() {
				$(this).attr("disabled", true);
			});
		} else {
			$("#pizza-taytteet input:checkbox").each(
				function() {
					$(this).attr("disabled", false);
				});
			}
		},
		paivitanimi: function(e) {
			this.setState({ pizzanimi: e.target.value }, function(){ this.paivitanappi() });
		},
		paivitahinta: function(e) {
			this.setState({ pizzahinta: e.target.value }, function(){ this.paivitanappi() });
		},
		paivitakuvaus: function(e) {
			this.setState({ pizzakuvaus: e.target.value }, function(){ this.paivitanappi() });
		},
		paivitanappi: function() {
			if (this.state.pizzanimi.length > 2 && this.state.pizzahinta > 0 && this.state.pizzakuvaus.length > 0 && this.state.valittuja > 0) {
				$("#submitpizza").attr("disabled", false);
			}
			else {
				$("#submitpizza").attr("disabled", true);
			}
		},
		componentWillReceiveProps: function(propsit) {
			if (propsit.pizzaLisaysStatus) {
				if (propsit.pizzaLisaysStatus == "success") {
					this.setState({pizzanimi: "", pizzahinta: "", pizzakuvaus: ""}, function() { this.paivitanappi() });
					$("#pizza-taytteet input:checkbox").each(
						function() {
							$(this).attr("checked", false);
						});
						this.laskeValitut();
					}
				}
			},
			render: function() {
				return (
					<div className="col s12">
					<div className="row">
					<h2>Lisää pizza</h2>
					<form id="lisaysformi">
					<div className="col s12 m12 l10 offset-l1">
					<div className="row">
					<div className="input-field col s12 m9 l9">
					<input type="text" name="pizzanimi" id="pizzanimi" value={this.state.pizzanimi } onChange={this.paivitanimi }/>
					<label htmlFor="pizzanimi">Pizzan nimi</label>
					</div>
					<div className="input-field col s12 m3 l3">
					<input type="number" className="validate" min="0" step="0.05" name="pizzahinta" id="pizzahinta" value={this.state.pizzahinta } onChange={this.paivitahinta }/>
					<label htmlFor="pizzahinta" data-error="Virhe">Pizzan hinta</label>
					</div>
					<div className="input-field col s12">
					<textarea className="materialize-textarea" name="pizzakuvaus" id="pizzakuvaus" length="255" value={this.state.pizzakuvaus } onChange={this.paivitakuvaus }></textarea>
					<label htmlFor="pizzakuvaus">Pizzan kuvaus</label>
					</div>
					</div>
					<div className="row" id="pizza-taytteet">
					<label id="taytteet-label">Täytteet {this.state.valittuja } / 5</label><br/><br/>
					<div className="row">
					{this.props.taytteet.map((o, i) => <TayteCheckbox key={o.id} id={o.id} nimi={o.nimi} saatavilla={o.saatavilla } laskeValitut={this.laskeValitut } />)}
					</div>
					</div>
					<button className="btn waves-effect waves-light btn-large" id="submitpizza" type="button" onClick={this.props.lisaaPizza }>Lisää pizza</button>
					</div>
					</form>
					</div>
					</div>
				);
			}
		});

		// Juoman lisäysformi
		var JuomanLisays = React.createClass({
			getInitialState: function() {
				return ({juomanimi: "", juomahinta: "", juomakoko: "", juomasaatavilla: true, juomakuvaus: ""});
			},
			componentDidMount: function() {
				$("#submitjuoma").attr("disabled", true);
			},
				paivitanimi: function(e) {
					this.setState({ juomanimi: e.target.value }, function(){ this.paivitanappi() });
				},
				paivitahinta: function(e) {
					this.setState({ juomahinta: e.target.value }, function(){ this.paivitanappi() });
				},
				paivitakoko: function(e) {
					this.setState({ juomakoko: e.target.value }, function(){ this.paivitanappi() });
				},
				paivitasaatavilla: function(e) {
					var value = e.target.value;
					if (value == 0) {
						this.setState({juomasaatavilla: false});
					}
					else {
						this.setState({juomasaatavilla: true});
					}
				},
				paivitakuvaus: function(e) {
					this.setState({ juomakuvaus: e.target.value }, function(){ this.paivitanappi() });
				},
				paivitanappi: function() {
					if (this.state.juomanimi.length > 2 && this.state.juomahinta > 0 && this.state.juomakuvaus.length > 0 && this.state.juomakoko > 0) {
						$("#submitjuoma").attr("disabled", false);
					}
					else {
						$("#submitjuoma").attr("disabled", true);
					}
				},
				componentWillReceiveProps: function(propsit) {
					if (propsit.juomaLisaysStatus) {
						if (propsit.juomaLisaysStatus == "success") {
							this.setState({juomanimi: "", juomahinta: "", juomakoko: "", juomasaatavilla: true, juomakuvaus: ""}, function() { this.paivitanappi() });
							}
						}
					},
					lisaaJuoma: function() {
						var saatavuus = 0;
						if (this.state.juomasaatavilla === true) {
							saatavuus = 1;
						}
						var submitdata = [{name: "action", value: "lisaajuoma"},
						{name: "juomanimi", value: this.state.juomanimi},
						{name: "juomakoko", value: this.state.juomakoko},
						{name: "juomasaatavilla", value: saatavuus},
						{name: "juomahinta", value: this.state.juomahinta},
						{name: "juomasaatavilla", value: saatavuus},
						{name: "juomakuvaus", value: this.state.juomakuvaus},
						{name: "json", value: "true"}];
						this.props.lisaaJuoma(submitdata);
					},
					render: function() {
						return (
							<div className="col s12">
							<div className="row">
							<h2>Lisää juoma</h2>
							<form id="juomanlisaysformi">
							<div className="col s12 m12 l10 offset-l1">
							<div className="row">
							<div className="input-field col s12 m6 l6">
							<input type="text" name="juomanimi" id="juomanimi" value={this.state.juomanimi } onChange={this.paivitanimi }/>
							<label htmlFor="juomanimi">Juoman nimi</label>
							</div>
							<div className="input-field col s12 m3 l3">
							<input type="number" className="validate" min="0" step="0.01" name="juomakoko" id="juomakoko" value={this.state.juomakoko } onChange={this.paivitakoko }/>
							<label htmlFor="juomakoko" data-error="Virhe">Juoman koko</label>
							</div>
							<div className="input-field col s12 m3 l3">
							<input type="number" className="validate" min="0" step="0.05" name="juomahinta" id="juomahinta" value={this.state.juomahinta } onChange={this.paivitahinta }/>
							<label htmlFor="juomahinta" data-error="Virhe">Juoman hinta</label>
							</div>
							<div className="input-field col s12 m9 l9">
							<textarea className="materialize-textarea" name="pizzakuvaus" id="juomakuvaus" length="255" value={this.state.juomakuvaus } onChange={this.paivitakuvaus }></textarea>
							<label htmlFor="juomakuvaus">Juoman kuvaus</label>
							</div>
							<div className="input-field col s12 m3 l3">
							<label>Saatavuus</label><br />
							<input name="juomasaatavilla" type="radio" id="juomasaatavilla" value="1" checked={this.state.juomasaatavilla === true} onChange={this.paivitasaatavilla} />
							<label htmlFor="juomasaatavilla">Saatavilla</label>
							<br />
							<input name="juomasaatavilla" type="radio" id="juomaeisaatavilla" value="0" checked={this.state.juomasaatavilla === false} onChange={this.paivitasaatavilla} />
							<label htmlFor="juomaeisaatavilla">Ei saatavilla</label>
							</div>
							<div className="col s12 m12 l12">
							<br />
							<button className="btn waves-effect waves-light btn-large" id="submitjuoma" type="button" onClick={this.lisaaJuoma }>Lisää juoma</button>
							</div>
							</div>
							</div>
							</form>
							</div>
							</div>
						);
					}
				});

		// Hallintasivun navigaatio
		var Navigaatio = React.createClass({
			render: function() {
				return (
					<div>
					<div className="row hide-on-small-only">
					<div className="col s12 m12 l10 offset-l1">
					<ul className="tabs">
					<li className="tab col s12"><a href="#pizza-h" className="active">Tuotteiden
					hallinta</a></li>
					<li className="tab col s12"><a href="#pizza-l">Tuotteiden lisäys</a></li>
					<li className="tab col s12"><a href="#tayte-h">Täytteiden
					hallinta</a></li>
					</ul>
					</div>
					</div>
					<div className="row hide-on-med-and-up">
					<div className="col s12">
					<ul className="tabs">
					<li className="tab col s12"><a href="#pizza-h" className="active"><img
					src="img/pizza_gear.png" alt="P" /> </a></li>
					<li className="tab col s12"><a href="#pizza-l"><img
					src="img/pizza_add.png" alt="L" /></a></li>
					<li className="tab col s12"><a href="#tayte-h"><img
					src="img/pizza_zoom.png" alt="T" /> </a></li>
					</ul>
					</div>
					</div>
					</div>
				);
			}
		});

		// Hallintasivun renderointi ja funktiot
		var Hallintasivu = React.createClass({
			getInitialState: function() {
				return { pizzat: [], taytteet: [], juomat: [], poistettavatpizzat: 0, poistettavatjuomat: 0, tayteLisaysStatus: null, pizzaLisaysStatus: null, juomaLisaysStatus: null, muokattavaTayte: {} };
			},
			componentDidMount: function() {
				this.haeData();
				$('ul.tabs').tabs();
			},
			haeData: function() {
				return $.post("hallinta", {action: "haekaikki"}).done(
					function(json) {
						console.log("Datat haettu - Pizzat: " + json.pizzat.length + "kpl, Täytteet: " + json.taytteet.length + "kpl, Juomat: " + json.juomat.length + "kpl");
						var poistettavatpizzat = 0;
						var poistettavatjuomat = 0;
						for (var i = 0; i < json.pizzat.length; i++) {
							if (json.pizzat[i].poistomerkinta != null) {
								poistettavatpizzat++;
							}
						}
						for (var i = 0; i < json.juomat.length; i++) {
							if (json.juomat[i].poistomerkinta != null) {
								poistettavatjuomat++;
							}
						}
						this.setState({ pizzat: json.pizzat, taytteet: json.taytteet, juomat: json.juomat, poistettavatpizzat: poistettavatpizzat, poistettavatjuomat: poistettavatjuomat })
					}.bind(this)).fail(
						function(jqxhr, textStatus, error) {
							var errori = textStatus + ", " + error;
							console.log("Error dataa hakiessa: " + errori);
						});
					},
					kasittelePizza: function(toiminto) {
						$.get("hallinta", toiminto).done(
							function(json) {
								var vastaus = json[0];
								if (vastaus.virhe != null) {
									console.log(vastaus.virhe);
									naytaVirhe(vastaus.virhe);
								}
								else if (vastaus.success != null) {
									console.log(vastaus.success);
									naytaSuccess(vastaus.success);
									this.haeData();
								}
								else {
									console.log(JSON.stringify(json));
									naytaVirhe("Virhe JSON vastauksessa!")
								}
							}.bind(this)).fail(
								function(jqxhr, textStatus, error) {
									var errori = textStatus + ", " + error;
									console.log("Faili: " + errori);
									console.log(JSON.stringify(json));
									naytaVirhe("Virhe javascriptissa!")
								});
							},
									lisaaTayte: function() {
										var submitdata = $("#tayteformi" ).serializeArray();
										submitdata.push({name: "action", value: "lisaatayte"});
										submitdata.push({name: "json", value: "true"});
										$.post("hallinta", submitdata).done(
											function(json) {
												var vastaus = json[0];
												if (vastaus.virhe != null) {
													naytaVirhe(vastaus.virhe);
												}
												else if (vastaus.success != null) {
													naytaSuccess(vastaus.success);
													this.setState({ tayteLisaysStatus: "success" });
													this.haeData();
												}
												else {
													console.log(JSON.stringify(json));
													naytaVirhe("Virhe JSON vastauksessa!")
												}
											}.bind(this)).fail(
												function(jqxhr, textStatus, error) {
													var errori = textStatus + ", " + error;
													console.log("Faili: " + errori);
													console.log(JSON.stringify(json));
													naytaVirhe("Virhe javascriptissa!")
												});
											},
											lisaaPizza: function() {
												var submitdata = $("#lisaysformi" ).serializeArray();
												submitdata.push({name: "action", value: "lisaapizza"});
												submitdata.push({name: "json", value: "true"});
												$.post("hallinta", submitdata).done(
													function(json) {
														var vastaus = json[0];
														if (vastaus.virhe != null) {
															naytaVirhe(vastaus.virhe);
														}
														else if (vastaus.success != null) {
															naytaSuccess(vastaus.success);
															this.setState({ pizzaLisaysStatus: "success" });
															this.haeData();
														}
														else {
															console.log(JSON.stringify(json));
															naytaVirhe("Virhe JSON vastauksessa!")
														}
													}.bind(this)).fail(
														function(jqxhr, textStatus, error) {
															var errori = textStatus + ", " + error;
															console.log("Faili: " + errori);
															console.log(JSON.stringify(json));
															naytaVirhe("Virhe javascriptissa!")
														});
													},
													lisaaJuoma: function(data) {
														$.post("hallinta", data).done(
															function(json) {
																var vastaus = json[0];
																if (vastaus.virhe != null) {
																	naytaVirhe(vastaus.virhe);
																}
																else if (vastaus.success != null) {
																	naytaSuccess(vastaus.success);
																	this.setState({ juomaLisaysStatus: "success" });
																	this.haeData();
																}
																else {
																	console.log(JSON.stringify(json));
																	naytaVirhe("Virhe JSON vastauksessa!")
																}
															}.bind(this)).fail(
																function(jqxhr, textStatus, error) {
																	var errori = textStatus + ", " + error;
																	console.log("Faili: " + errori);
																	console.log(JSON.stringify(json));
																	naytaVirhe("Virhe javascriptissa!")
																});
															},
													muokkaaTaytetta: function(tayte) {
														this.setState({muokattavaTayte: tayte});
													},
													peruutaTaytemuokkaus: function() {
														this.setState({muokattavaTayte: {}});
													},
													lahetaTaytePaivitys: function(tayte) {
														$.post("hallinta", tayte).done(
															function(json) {
																var vastaus = json[0];
																if (vastaus.virhe != null) {
																	naytaVirhe(vastaus.virhe);
																}
																else if (vastaus.success != null) {
																	naytaSuccess(vastaus.success);
																	this.haeData();
																	this.setState({muokattavaTayte: {}});
																}
																else {
																	console.log(JSON.stringify(json));
																	naytaVirhe("Virhe JSON vastauksessa!")
																}
															}.bind(this)).fail(
																function(jqxhr, textStatus, error) {
																	var errori = textStatus + ", " + error;
																	console.log("Faili: " + errori);
																	console.log(JSON.stringify(json));
																	naytaVirhe("Virhe javascriptissa!")
																});
													},
													render: function() {
														var taytetoiminto = "";
														if (Object.keys(this.state.muokattavaTayte).length === 0) {
															taytetoiminto = <TaytteenLisays lahetaLisays={this.lisaaTayte } tayteLisaysStatus={this.state.tayteLisaysStatus } />;
													 	}
														else {
															taytetoiminto = <TaytteenMuokkaus tayte={this.state.muokattavaTayte } peruuta={this.peruutaTaytemuokkaus } lahetaPaivitys={this.lahetaTaytePaivitys }/>;
														}
														var headerteksti = <p className="flow-text">Tietokannassa on yhteensä {this.state.pizzat.length } pizzaa, {this.state.taytteet.length } täytettä ja {this.state.juomat.length} juomaa!</p>;
														var pizzalista = 	<Pizzalista pizzat={this.state.pizzat } juomat={this.state.juomat} kasittelePizza={this.kasittelePizza } poistaValitut={this.kasittelePizza } poistettavatpizzat={this.state.poistettavatpizzat } poistettavatjuomat={this.state.poistettavatjuomat } muokkaaJuomaa={this.lisaaJuoma}/>;
														var taytelista = <Taytelista taytteet={this.state.taytteet } muokkaaTaytetta={this.muokkaaTaytetta }/>;
														var pizzanlisays = <PizzanLisays taytteet={this.state.taytteet } lisaaPizza={this.lisaaPizza } pizzaLisaysStatus={this.state.pizzaLisaysStatus }/>;
														if (this.state.pizzat.length < 1 && this.state.taytteet.length < 1) {
															headerteksti = <p className="flow-text">Listalla ei ole yhtään pizzaa eikä täytettä, tai niitä ei saatu noudettua tietokannasta</p>;
														}
														if (this.state.pizzat.length < 1) {
															pizzalista = <div className="center-align errori">Listalla ei ole yhtään pizzaa, tai niitä ei saatu noudettua tietokannasta.</div>;
														}
														if (this.state.taytteet.length < 1) {
															taytelista = <div className="center-align errori col s12 m12 l6 pull-l5">Listalla ei ole yhtään täytettä, tai niitä ei saatu noudettua tietokannasta.</div>;
															pizzanlisays = <div className="center-align errori col s12">Täytteitä ei ole, tai niitä ei saatu noudettua tietokannasta. Yritä lisätä täytteet ensin.</div>;
														}
														return(
															<div>
															<div className="row headertext">
															<h1>Hallinta</h1>
															{headerteksti}
															</div>
															<div id="main-content">
															<Navigaatio />
															<div className="row" id="pizza-h">
															{pizzalista}
															</div>
															<div className="row" id="pizza-l">
															{pizzanlisays}
															<JuomanLisays lisaaJuoma={this.lisaaJuoma} juomaLisaysStatus={this.state.juomaLisaysStatus}/>
															</div>
															<div className="row" id="tayte-h">
															{taytetoiminto}
															{taytelista}
															</div>
															</div>
															</div>
														);
													}
												});

												ReactDOM.render(
													<Hallintasivu />,
													document.getElementById("hallintasisalto")
												)
