// Hallintasivun renderointi ja funktiot
var Tilaukset = React.createClass({
  getInitialState: function() {
    return { tilaukset: [] };
  },
  componentDidMount: function() {
    this.haeData();
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
          <div className="center-align">
          <table>
          <thead>
          <tr>
          <td>#</td>
          <td>Tilausaika</td>
          <td>Status</td>
          <td>Toimitus</td>
          </tr>
          </thead>
          <tbody>
          {this.state.tilaukset.map((o,i) => <Tilausrivi rivi={o} key={i} />)}
          </tbody>
          </table>
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
  render: function() {
    return (
      <tr>
      <td>{this.props.rivi.id}</td>
      <td>{this.formatoiPaiva()}</td>
      <td>{this.props.rivi.status}</td>
      <td>{this.props.rivi.toimitustapa}</td>
      </tr>
    );
  }
});

    ReactDOM.render(
      <Tilaukset />,
      document.getElementById("tilaussisalto")
    )
