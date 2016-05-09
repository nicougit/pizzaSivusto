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
          {this.state.tilaukset.map((o,i) => <Tilausrivi rivi={o} key={i} />)}
          </div>
          </div>
        );
      }
    });

    var Tilausrivi = React.createClass({
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

        return (
          <div className="col s12">
          <div className="card grey lighten-5">
          <div className="card-content">
          <span className="card-title">{this.props.rivi.toimitustapa} <span className="pienifontti"><a href="#!">#{this.props.rivi.id}</a></span><span className="right">Klo {this.tilausAika()}</span></span>
          <p>
          Status: {this.props.rivi.status }<br />
          Pizzat: {pizzat }<br />
          Juomat: {juomat }<br />
          <select className="statusvalinta">
          <option value="0">Vastaanotettu</option>
          <option value="1">Työn alla</option>
          <option value="2">Valmis</option>
          <option value="3">Luovutettu</option>
          </select>
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
