/*
Todo:
- Pizzojen lisätiedot näkyviin (orgeano, valkosipuli...)
--> vaatii myös backendissä muutoksia
- Joka pizza ja juoma omalle riville?
- Taulukon riveille class jolle tyylimääritys vertical-align: top
*/

// Hallintasivun renderointi ja funktiot
var Tilaukset = React.createClass({
  getInitialState: function() {
    return { tilaukset: [], filtterit: ["0", "1", "2"] };
  },
  componentDidMount: function() {
    this.autoRefresh();
  },
  componentDidUpdate: function() {
    $('select').material_select();
  },
  filtteriMuutos: function(value, checked) {
    var filtterit = this.state.filtterit;
    if (checked === true) {
      if (filtterit.indexOf(value) < 0) {
        filtterit.push(value);
        this.setState({ filtterit: filtterit });
      }
    }
    else {
      if (filtterit.indexOf(value) >= 0) {
        filtterit.splice(filtterit.indexOf(value), 1);
        this.setState({ filtterit: filtterit });
      }
    }
  },
  autoRefresh: function() {
    var self = this;
    var paivitysvali = 10000; // Päivitysväli millisekunteina
    setTimeout(function() {
      if (self.isMounted()) {
        self.haeData();
        self._timer = setInterval(self.haeData, paivitysvali);
      }
    }, 1);
  },
  postRequest: function(data) {
    $.post("tilaukset", data).done(
      function(json) {
        var vastaus = json[0];
        if (vastaus.virhe != null) {
          naytaVirhe(vastaus.virhe);
        }
        else if (vastaus.success != null) {
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
  haeData: function() {
    return $.get("tilaukset", {action: "tilauksetJsonina"}).done(
      function(json) {
        console.log("Tilaukset haettu - tilauksia " + json.length + "kpl");
        this.setState({ tilaukset: json });
      }.bind(this)).fail(
        function(jqxhr, textStatus, error) {
          var errori = textStatus + ", " + error;
          console.log("Error dataa hakiessa: " + errori);
        });
      },
      render: function() {

        var naytettavat = [];

        for (var i = 0; i < this.state.tilaukset.length; i++) {
          if (this.state.filtterit.indexOf(this.state.tilaukset[i].status) >= 0) {
            naytettavat.push(this.state.tilaukset[i]);
          }
        }

        return(
          <div>
          <Filtterit filtteriMuutos={this.filtteriMuutos}/>
          <div className="row">
          {naytettavat.map((o,i) => <Tilausrivi rivi={o} key={i} paivitaStatus={this.postRequest}/>)}
          </div>
          </div>
        );
      }
    });

    var Filtterit = React.createClass({
      kasitteleValinnat: function(e) {
        this.props.filtteriMuutos(e.target.value.toString(), e.target.checked);
      },
      render: function() {
        return (
          <div className="center-align">
          <h3>Näytettävät tilaukset</h3>
          <form id="#filtterit">
          <input type="checkbox" name="filtteri" value="0" id="filtteri0" onChange={this.kasitteleValinnat} defaultChecked />
          <label className="tilausfiltteri" htmlFor="filtteri0">Vastaanotetut</label>
          <input type="checkbox" name="filtteri" value="1" id="filtteri1" onChange={this.kasitteleValinnat} defaultChecked />
          <label className="tilausfiltteri" htmlFor="filtteri1">Työn alla</label>
          <input type="checkbox" name="filtteri" value="2" id="filtteri2" onChange={this.kasitteleValinnat} defaultChecked />
          <label className="tilausfiltteri" htmlFor="filtteri2">Valmiit</label>
          <input type="checkbox" name="filtteri" value="3" id="filtteri3" onChange={this.kasitteleValinnat} />
          <label className="tilausfiltteri" htmlFor="filtteri3">Luovutetut</label>
          </form>
          <br />
          <div className="divider" />
          <br />
          </div>
        );
      }
    });

    var Tilausrivi = React.createClass({
      getInitialState: function() {
        return ({ valinta: this.props.rivi.status });
      },
      componentWillReceiveProps: function(propsit) {
        if (propsit.rivi.status && propsit.rivi.status != this.state.valinta) {
          this.setState({ valinta: propsit.rivi.status });
        }
      },
      formatoiPaiva: function() {
        var p = new Date(this.props.rivi.tilaushetki);
        var tunti = p.getHours();
        var minuutti = p.getMinutes();
        if (tunti < 10) {
          tunti = "0" + tunti;
        }
        if (minuutti < 10) {
          minuutti = "0" + minuutti;
        }
        var tilaushetki = p.getDate() + "." + (p.getMonth() + 1) + "." + p.getFullYear() + " " + tunti + ":" + minuutti;
        return tilaushetki;
      },
      tilausAika: function() {
        var p = new Date(this.props.rivi.tilaushetki);
        var tunti = p.getHours();
        var minuutti = p.getMinutes();
        if (tunti < 10) {
          tunti = "0" + tunti;
        }
        if (minuutti < 10) {
          minuutti = "0" + minuutti;
        }
        var tilausaika = tunti + ":" + minuutti;
        return tilausaika;
      },
      paivitaValittu: function(e) {
        if (e.target.value >= 0 && e.target.value <= 3) {
        console.log("Vaihdetaan tilauksen " + this.props.rivi.id + " status --> " + e.target.value);
        this.setState({ valinta: e.target.value });
        // Lähetetään servulle
        var olio = { action: "paivitaStatus", json: "true", id: this.props.rivi.id, status: e.target.value };
        this.props.paivitaStatus(olio);
        }
      },
      render: function() {

        // Loopataan pizzat ja juomat stringeiksi pilkuilla eroteltuina
        var pizzat = "";
        var juomat = "";
        for (var i = 0; i < this.props.rivi.pizzat.length; i++) {
          pizzat += this.props.rivi.pizzat[i].nimi;
          if ((i + 1) != this.props.rivi.pizzat.length) {
            pizzat += ", ";
          }
        }

        for (var i = 0; i < this.props.rivi.juomat.length; i++) {
          juomat += this.props.rivi.juomat[i].nimi;
          if ((i + 1) != this.props.rivi.juomat.length) {
            juomat += ", ";
          }
        }

        // Käännetään taulukon riveiksi
        if (pizzat != "") {
          pizzat = <tr><td className="tilaukset-rivi">Pizzat</td><td>{pizzat}</td></tr>;
        }
        else {
          pizzat = null;
        }
        if (juomat != "") {
          juomat = <tr><td className="tilaukset-rivi">Juomat</td><td>{juomat}</td></tr>;
        }
        else {
          juomat = null;
        }

        // Lisätiedot taulukkoriviksi jos löytyy
        var lisatiedot = null;
        if (this.props.rivi.lisatiedot != null) {
          lisatiedot = <tr><td className="tilaukset-rivi">Lisätiedot</td><td>{this.props.rivi.lisatiedot}</td></tr>;
        }

        var statusLuokka = {};

        if (this.state.valinta == 0) {
          statusLuokka = {"className": "card orange lighten-3"}
        }
        else if (this.state.valinta == 1) {
          statusLuokka = {"className": "card orange lighten-4"}
        }
        else if (this.state.valinta == 2) {
          statusLuokka = {"className": "card orange lighten-5"}
        }
        else if (this.state.valinta == 3) {
          statusLuokka = {"className": "card green lighten-5"}
        }
        else {
          statusLuokka = {"className": "card grey lighten-5"}
        }

        return (
          <div className="col s12 m12 l6">
          <div {... statusLuokka}>
          <div className="card-content">
          <span className="card-title">{this.props.rivi.toimitustapa} <span className="pienifontti"><a href="#!">#{this.props.rivi.id}</a></span>
          <select className="right browser-default tilaus-statusvalinta" value={this.state.valinta} onChange={this.paivitaValittu }>
          <option value="0">Vastaanotettu</option>
          <option value="1">Työn alla</option>
          <option value="2">Valmis</option>
          <option value="3">Luovutettu</option>
          </select>
          </span>
          <table className="tilaukset-table">
          <tbody>
          <tr><td className="tilaukset-rivi">Tilausaika</td><td>{this.formatoiPaiva()}</td></tr>
          {pizzat }
          {juomat }
          {lisatiedot }
          </tbody>
          </table>
          </div>
          </div>
          </div>
        );
      }
    });

    ReactDOM.render(
      <Tilaukset />,
      document.getElementById("tilaussisalto")
    )
