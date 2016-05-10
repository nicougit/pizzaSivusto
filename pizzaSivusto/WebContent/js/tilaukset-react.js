// Hallintasivun renderointi ja funktiot
var Tilaukset = React.createClass({
  getInitialState: function() {
    return { tilaukset: [] };
  },
  componentDidMount: function() {
    this.haeData();
  },
  componentDidUpdate: function() {
    $('select').material_select();
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
        console.log("Datat haettu - tilauksia " + json.length + "kpl");
        this.setState({ tilaukset: json });
      }.bind(this)).fail(
        function(jqxhr, textStatus, error) {
          var errori = textStatus + ", " + error;
          console.log("Error dataa hakiessa: " + errori);
        });
      },
      render: function() {
        return(
          <div>
          <div className="row">
          {this.state.tilaukset.map((o,i) => <Tilausrivi rivi={o} key={i} paivitaStatus={this.postRequest}/>)}
          </div>
          </div>
        );
      }
    });

    var Tilausrivi = React.createClass({
      getInitialState: function() {
        return ({ valinta: this.props.rivi.status });
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

        if (pizzat != "") {
          pizzat = <span>Pizzat: {pizzat}<br /></span>;
        }
        if (juomat != "") {
          juomat = <span>Juomat: {juomat}<br /></span>;
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
          <p>
          <span>Tilattu kello {this.formatoiPaiva()}</span>
          <br />
          {pizzat }
          {juomat }
          </p>
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
