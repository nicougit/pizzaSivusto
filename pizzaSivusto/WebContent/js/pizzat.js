var lisaaOstoskoriin = function(id, tyyppi) {
  console.log("Yritetään lisätä tuotetta ostoskoriin, id:" + id + " - tyyppi:" + tyyppi);
  $.post("ostoskori", {action: "lisaa", id: id, tyyppi: tyyppi, json: "true"}).done(
    function(json) {
      console.log("Servu palautti: " + JSON.stringify(json));
      var vastaus = json[0];
      if (vastaus.virhe != null) {
        naytaVirhe(vastaus.virhe);
      }
      else if (vastaus.success != null) {
        naytaSuccess(vastaus.success);
      }
    }.bind(this)).fail(
      function(jqxhr, textStatus, error) {
        var errori = textStatus + ", " + error;
        console.log("Error ostoskoriin lisätessä: " + errori);
      });
    }
