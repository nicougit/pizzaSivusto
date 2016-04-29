



var Tilaushistoria = React.createClass({
  getInitialState: function() {
    return { tilaukset: [] };
  },
  componentDidMount: function() {
    this.haeTilaukset();
  },
  haeTilaukset: function() {
    return $.post("kayttaja", {action: "haetilaukset", json: "true"}).done(
      function(json) {
        console.log("Tilaushistoria: " + json.length + " pitkä");
        this.setState({ tilaukset: json });
      }.bind(this)).fail(
        function(jqxhr, textStatus, error) {
          var errori = textStatus + ", " + error;
          console.log("Error tilaushistoriaa hakiessa: " + errori);
        });
      },
  render: function() {
  return (
    <p>lol</p>
  )
  }
});


ReactDOM.render(
  <Tilaushistoria />,
  document.getElementById("tilaushistoriadiv")
)
