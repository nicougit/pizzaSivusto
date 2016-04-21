var Login = React.createClass({
  getInitialState: function() {
    return ({email: "", salasana: ""});
  },
  paivitaemail: function(e) {
    this.setState({ email: e.target.value }, function(){ this.paivitanappi() });
  },
  paivitasalasana: function(e) {
    this.setState({ salasana: e.target.value }, function(){ this.paivitanappi() });
  },
  paivitanappi: function() {
    if (this.state.salasana.length > 5) {
      $("#loginnappi").attr("disabled", false);
    }
    else {
      $("#loginnappi").attr("disabled", true);
    }
  },
  componentDidMount: function() {
    this.paivitanappi();
  },
  render: function() {
    return (
      <div className="row" id="kirjaudusisaan">
      <form method="post" action="login">
      <h2>Kirjaudu sisään</h2>
      <div className="row">
      <div className="input-field col s12">
      <input type="email" name="kayttajanimi" id="kayttajanimi" value={this.state.email} onChange={this.paivitaemail}/> <label htmlFor="kayttajanimi">Sähköpostiosoite</label>
      </div>
      </div>
      <div className="row">
      <div className="input-field col s12">
      <input type="password" name="salasana" id="salasana" value={this.state.salasana} onChange={this.paivitasalasana}/> <label
      htmlFor="salasana">Salasana</label>
      </div>
      </div>
      <div className="row">
      <div className="input-field col s12">
      <button className="btn waves-effect waves-light" type="submit"
      name="action" value="login" id="loginnappi">Kirjaudu sisään</button>
      </div>
      </div>
      </form>
      </div>
    );
  }
});

var Rekisterointi = React.createClass({
  getInitialState: function() {
    return ({email: "", pw1: "", pw2: "", etunimi: "", sukunimi: "", puhnro: ""});
  },
  paivitaemail: function(e) {
    if (e.target.value.length <= 256) {
      this.setState({ email: e.target.value }, function(){ this.paivitanappi() });
    }
  },
  paivitapw1: function(e) {
    if (e.target.value.length <= 256) {
      this.setState({ pw1: e.target.value }, function(){ this.paivitanappi() });
    }
  },
  paivitapw2: function(e) {
    if (e.target.value.length <= 256) {
      this.setState({ pw2: e.target.value }, function(){ this.paivitanappi() });
    }
  },
  paivitaetunimi: function(e) {
    if (e.target.value.length <= 50) {
      this.setState({ etunimi: e.target.value }, function(){ this.paivitanappi() });
    }
  },
  paivitasukunimi: function(e) {
    if (e.target.value.length <= 50) {
      this.setState({ sukunimi: e.target.value }, function(){ this.paivitanappi() });
    }
  },
  paivitapuhnro: function(e) {
    var regex = /^\d+$/;
    if (e.target.value.length <= 15 && regex.test(e.target.value) || e.target.value == "") {
      this.setState({ puhnro: e.target.value }, function(){ this.paivitanappi() });
    }
  },
  validoi: function() {
    var emailok = false;
    var passuok = false;
    var etunimiok = false;
    var sukunimiok = false;

    // Emailin validointi (regex rimpsu saattaa olla suoraan netistä)
    var regex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    if (regex.test(this.state.email)) {
      $("#kayttajatunnus").removeClass("invalid");
      $("#kayttajatunnus").addClass("valid");
      emailok = true;
    }
    else if (this.state.email.length > 0){
      $("#kayttajatunnus").removeClass("valid");
      $("#kayttajatunnus").addClass("invalid");
      emailok = false;
    }
    else {
      $("#kayttajatunnus").removeClass("valid,invalid");
      emailok = false;
    }

    // Salasanan validointi
    var pw1 = this.state.pw1;
    var pw2 = this.state.pw2;
    if (pw1 != pw2) {
      $("#salasana-rek,#salasana-rek2").addClass("invalid");
      passuok = false;
    }
    else if (pw1.length > 5 && pw2.length > 5) {
      $("#salasana-rek,#salasana-rek2").removeClass("invalid");
      $("#salasana-rek,#salasana-rek2").addClass("valid");
      passuok = true;
    }
    else {
      $("#salasana-rek,#salasana-rek2").removeClass("invalid,valid");
      passuok = false;
    }

    // Etunimen validointi - Kesken, toistaiseksi vaan pituus
    if (this.state.etunimi.length > 1) {
      etunimiok = true;
      $("#etunimi").addClass("valid");
    }
    else {
      etunimiok = false;
      $("#etunimi").removeClass("valid");
    }

    // Sukunimen validointi - Kesken, toistaiseksi vaan pituus
    if (this.state.sukunimi.length > 0) {
      sukunimiok = true;
      $("#sukunimi").addClass("valid");
    }
    else {
      sukunimiok = false;
      $("#sukunimi").removeClass("valid");
    }

    // Puhelinnumeron validointi
    regex = /^\d+$/;
    if (regex.test(this.state.puhnro) && this.state.puhnro.length > 8) {
      $("#puhelinnro").addClass("valid");
    }
    else {
      $("#puhelinnro").removeClass("valid");
    }

    // Palautus
    if (passuok && emailok && etunimiok && sukunimiok) {
      return true;
    }
    else {
      return false;
    }
  },
  paivitanappi: function() {
    if (this.validoi() === true) {
      $("#regnappi").attr("disabled", false);
    }
    else {
      $("#regnappi").attr("disabled", true);
    }
  },
  componentDidMount: function() {
    this.paivitanappi();
  },
  render: function() {
    return (
      <div className="row" id="rekisteroidy">
      <h2>Rekisteröidy</h2>
      <form action="login" method="post">
      <div className="row">
      <div className="input-field col s12">
      <input type="email" id="kayttajatunnus" name="kayttajatunnus" value={this.state.email} onChange={this.paivitaemail}/>
      <label htmlFor="kayttajatunnus">Sähköpostiosoite</label>
      </div>
      <div className="input-field col s12 m6 l6">
      <input type="password" id="salasana-rek" name="salasana-rek" value={this.state.pw1} onChange={this.paivitapw1}/>
      <label htmlFor="salasana-rek">Salasana</label>
      </div>
      <div className="input-field col s12 m6 l6">
      <input type="password" id="salasana-rek2" name="salasana-rek2" value={this.state.pw2} onChange={this.paivitapw2}/>
      <label htmlFor="salasana-rek2">Salasana uudelleen</label>
      </div>
      <div className="input-field col s12 m6 l6">
      <input type="text" id="etunimi" name="etunimi" value={this.state.etunimi} onChange={this.paivitaetunimi}/>
      <label htmlFor="etunimi">Etunimi</label>
      </div>
      <div className="input-field col s12 m6 l6">
      <input type="text" id="sukunimi" name="sukunimi" value={this.state.sukunimi} onChange={this.paivitasukunimi}/>
      <label htmlFor="sukunimi">Sukunimi</label>
      </div>
      <div className="input-field col s12">
      <input type="tel" id="puhelinnro" name="puhelinnro" value={this.state.puhnro} onChange={this.paivitapuhnro}/>
      <label htmlFor="puhelinnro">Puhelinnumero</label>
      </div>
      </div>
      <div className="row">
      <div className="input-field col s12">
      <button className="btn waves-effect waves-light" type="submit" name="action" value="rekisteroidy" id="regnappi">Rekisteröidy</button>
      </div>
      </div>
      </form>
      </div>
    );
  }
});

var LoginReg = React.createClass({
  componentDidMount: function() {
    $('ul.tabs').tabs();
  },
  render: function() {
    return (
      <div className="row">
      <div className="col s12">
      <div className="row">
      <div className="col s12">
      <br />
      <ul className="tabs">
      <li className="tab col s6"><a href="#kirjaudusisaan"
      className="active">Kirjaudu</a></li>
      <li className="tab col s6"><a href="#rekisteroidy">Rekisteröidy</a></li>
      </ul>
      </div>
      </div>
      <Login />
      <Rekisterointi />
      </div>
      </div>
    );
  }
});

ReactDOM.render(
  <LoginReg />,
  document.getElementById("loginsisalto")
)
