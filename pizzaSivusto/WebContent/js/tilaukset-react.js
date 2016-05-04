// Hallintasivun renderointi ja funktiot
var Tilaukset = React.createClass({
  getInitialState: function() {
    return { tilaukset: [] };
  },
  componentDidMount: function() {
    //this.haeData();
  },
  haeData: function() {
    return $.post("hallinta", {action: "haeTilaukset"}).done(
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
          Tähän käyttäjien listaus
          </div>
        );
      }
    });

    ReactDOM.render(
      <Tilaukset />,
      document.getElementById("tilaussisalto")
    )
