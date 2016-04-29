var TaulukkoRivi = React.createClass({
  formatoiPaiva: function() {
    var p = new Date(this.props.tilaushetki);
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
      <tr className="taulukkorivi">
      <td className="center">
      <a href={'tilaus?tilausnro=' + this.props.tilaus_id }>{this.props.tilaus_id }</a>
      </td>
      <td>{this.formatoiPaiva() }</td>
      <td className="center">
      {this.props.kokonaishinta.toFixed(2).replace(".", ",") } €
      </td>
      </tr>
    );
  }
});

var Taulukko = React.createClass({
  render: function() {
    var rivit = [];
    var offsetti = (this.props.sivu - 1) * 8;
    var vika = offsetti + 8;
    if (vika > this.props.tilaushistoria.length) {
      vika = this.props.tilaushistoria.length;
    }
    for (var i = offsetti; i < vika; i++) {
      rivit.push(<TaulukkoRivi key={this.props.tilaushistoria[i].tilaus_id} tilaus_id={this.props.tilaushistoria[i].tilaus_id} tilaushetki={this.props.tilaushistoria[i].tilaushetki} kokonaishinta={this.props.tilaushistoria[i].kokonaishinta} />);
    }
    return (
      <table className="bordered">
				<thead>
				<tr>
				<th className="center">Tilausnumero</th>
				<th>Tilausajankohta</th>
				<th className="center">Hinta</th>
				</tr>
				</thead>
				<tbody>
        {rivit }
				</tbody>
				</table>
    );
  }
});

var PaginaatioNappi = React.createClass({
  painallus: function() {
    this.props.painallus(this.props.sivu);
  },
  render: function() {
    var tyyli = {};
    if (this.props.sivu == this.props.aktiivinen) {
      tyyli = {"className": "waves-effect active"};
    }
    else {
      tyyli = {"className": "waves-effect"};
    }
    return (
      <li {... tyyli } onClick={this.painallus }>{this.props.sivu }</li>
    );
  }
});

var Paginaatio = React.createClass({
  clickleft: function() {
    var sivuja = Math.ceil(this.props.pituus / 8);
    if (this.props.sivu > 1) {
      this.props.painallus(this.props.sivu - 1);
    }
  },
  clickright: function() {
    var sivuja = Math.ceil(this.props.pituus / 8);
    if (this.props.sivu < sivuja) {
      this.props.painallus(this.props.sivu + 1);
    }
  },
  render: function() {
    var sivuja = Math.ceil(this.props.pituus / 8);
    var rivit = [];
    for (var i = 0; i < sivuja; i++) {
      rivit.push(<PaginaatioNappi key={'nappi' + i} sivu={(i+1)} painallus={this.props.painallus} aktiivinen={this.props.sivu }/>);
    }
    var leftstyle = {"className": "waves-effect"};
    var rightstyle = {"className": "waves-effect"};
    if (this.props.sivu == 1) {
      leftstyle = {"className": "disabled"};
    }
    if (this.props.sivu == sivuja) {
      rightstyle = {"className": "disabled"};
    }
    return (
      <ul className="pagination">
      <li {... leftstyle } onClick={this.clickleft }><a><i className="material-icons">chevron_left</i></a></li>
      {rivit }
      <li {... rightstyle } onClick={this.clickright }><a><i className="material-icons">chevron_right</i></a></li>
      </ul>
    );
  }
});

var Tilaushistoria = React.createClass({
  getInitialState: function() {
    return { tilaukset: [], sivu: 1 };
  },
  componentDidMount: function() {
    this.haeTilaukset();
  },
  vaihdasivu: function(sivu) {
    console.log("Halutaan vaihtaa sivuksi " + sivu);
    this.setState({ sivu: sivu });
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
    var sisalto = "Sinulla ei ole vielä tilauksia";
    if (this.state.tilaukset.length > 0) {
      sisalto = <div><Taulukko tilaushistoria={this.state.tilaukset} sivu={this.state.sivu }/><Paginaatio pituus={this.state.tilaukset.length } painallus={this.vaihdasivu } sivu={this.state.sivu }/></div>;
    }
  return (
    <div>
    <h2>Tilaushistoria</h2>
    {sisalto }
    </div>
  )
  }
});


ReactDOM.render(
  <Tilaushistoria />,
  document.getElementById("tilaushistoriadiv")
)
