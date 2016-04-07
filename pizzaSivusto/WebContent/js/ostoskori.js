var lisaaOstoskoriin = function(id, tyyppi) {
  console.log("Yritetään lisätä tuotetta ostoskoriin, id:" + id + " - tyyppi:" + tyyppi);
  $.post("ostoskori", {action: "lisaa", id: id, tyyppi: tyyppi, json: "true"}).done(
    function(json) {
      var vastaus = json[0];
      if (vastaus.virhe != null) {
        naytaVirhe(vastaus.virhe);
      }
      else if (vastaus.success != null) {
        naytaSuccess(vastaus.success);
        printtaaOstoskori();
      }
    }).fail(
      function(jqxhr, textStatus, error) {
        var errori = textStatus + ", " + error;
        console.log("Error ostoskoriin lisätessä: " + errori);
        naytaVirhe("Pizzan lisääminen ostoskoriin ei onnistunut")
      });
    }

    var tyhjennaOstoskori = function() {
      $.post("ostoskori", {action: "tyhjenna", json: "true"}).done(
        function(json) {
          var vastaus = json[0];
          if (vastaus.virhe != null) {
            naytaVirhe(vastaus.virhe);
          }
          else if (vastaus.success != null) {
            naytaSuccess(vastaus.success);
            $("#ostoskorimodal").closeModal();
            printtaaOstoskori();
          }
        }).fail(
          function(jqxhr, textStatus, error) {
            var errori = textStatus + ", " + error;
            console.log("Error ostoskoriin lisätessä: " + errori);
            naytaVirhe("Ostoskorin tyhjentäminen ei onnistunut");
          });
        }

    function haeOstoskori() {
      return $.get("ostoskori", {"ostoskoriJsonina": "true"}).done(
        function(json) {
        }).fail(
          function(jqxhr, textStatus, error) {
            var errori = textStatus + ", " + error;
            console.log("Faili haeOstoskori-metodissa: " + errori);
            console.log(JSON.stringify(json));
            naytaVirhe("Ostoskorin noutaminen palvelimelta ei onnistunut")
          });
        }

        function formatoiHinta(hinta) {
          return parseFloat(hinta).toFixed(2).replace(".",",") + " €";
        }

        var printtaaOstoskori = function() {
          $.when(haeOstoskori()).done(function(ostoskori) {
            var tuotteita = ostoskori.length;
            var ostoskoririvit = "";
            var yhteishinta = 0;
            if (ostoskori.length > 0) {
              ostoskori.map(function(o, i) {
                ostoskoririvit += "<tr><td>" + o.nimi + "</td><td class=\"center-align\">" + formatoiHinta(o.hinta) + "</td></tr>";
                yhteishinta += o.hinta;
              });
              yhteishinta = formatoiHinta(yhteishinta);
              ostoskoririvit += "<tr class=\"ostoskori-yhteishinta\"><td class=\"right-align\">Yhteishinta</td><td class=\"center-align\">" + yhteishinta + "</td></tr>"
              $("#ostoskori-table, #ostoskori-tilausnappi, #ostoskori-tyhjennysnappi").removeClass("hide");
              $("#ostoskori-sulkunappi").removeClass("center").addClass("left");
              $("#ostoskori-yhteismaara").html("Ostoskorissa yhteensä " + tuotteita + " tuotetta");
              $(".navbar-yhteishinta").html(" " + yhteishinta);
              $("#ostoskori-tbody").html(ostoskoririvit);
            }
            else {
              $("#ostoskori-table, #ostoskori-tilausnappi, #ostoskori-tyhjennysnappi").addClass("hide");
              $("#ostoskori-sulkunappi").removeClass("left").addClass("center");
              $("#ostoskori-yhteismaara").html("Ostoskorisi on tyhjä<br>");
              $(".navbar-yhteishinta").html("");
            }
          });
        }

        $(document).ready(function() {
          printtaaOstoskori();
        });
