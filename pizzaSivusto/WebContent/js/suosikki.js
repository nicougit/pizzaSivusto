var kayttajapathi = "/" + window.location.pathname.split("/")[1] + "/kayttaja";

var lisaaSuosikki = function(pizzaid) {
  $.post(kayttajapathi, {action: "lisaasuosikki", id: pizzaid, json: "true"}).done(
    function(json) {
      var vastaus = json[0];
      if (vastaus.virhe != null) {
        naytaVirhe(vastaus.virhe);
        $('.tooltipped').tooltip({delay: 500});
      }
      else if (vastaus.success != null) {
        var suosikkiid = json[1].suosikkiid;
        naytaSuccess(vastaus.success);
        paivitaNappi(pizzaid, "lisatty", suosikkiid);
      }
    }).fail(
      function(jqxhr, textStatus, error) {
        var errori = textStatus + ", " + error;
        console.log("Error suosikkia lisätessä: " + errori);
        naytaVirhe("Pizzan lisääminen suosikiksi ei onnistunut")
      });
    }

    var poistaSuosikki = function(pizzaid, suosikkiid) {
      $.post(kayttajapathi, {action: "poistasuosikki", id: suosikkiid, json: "true"}).done(
        function(json) {
          var vastaus = json[0];
          if (vastaus.virhe != null) {
            naytaVirhe(vastaus.virhe);
            $('.tooltipped').tooltip({delay: 500});
          }
          else if (vastaus.success != null) {
            naytaSuccess(vastaus.success);
            paivitaNappi(pizzaid, "poistettu", null);
          }
        }).fail(
          function(jqxhr, textStatus, error) {
            var errori = textStatus + ", " + error;
            console.log("Error suosikkia poistaessa: " + errori);
            naytaVirhe("Suosikkipizzan poistaminen ei onnistunut")
          });
        }

        var paivitaNappi = function(id, toiminto, suosikkiid) {
          var elementti = "";
          if (id != null) {
            elementti = "#suosikkidiv-" + id;
          }

          if (toiminto == "lisatty") {
            var uusnappi = "<a href=\"#!\"><i class=\"material-icons menu-favyes\" onClick=\"poistaSuosikki(" + id + ", " + suosikkiid + ")\">star</i></a>";
            $(elementti).html(uusnappi);
          }
          else if (toiminto == "poistettu") {
            var uusnappi = "<a href=\"#!\"><i class=\"material-icons menu-favno\" onClick=\"lisaaSuosikki(" + id + ")\">star_border</i></a>";
            $(elementti).html(uusnappi);
          }
          else {
            console.log("Tuntematon toiminto suosikkimeiningeissä")
          }
        }
