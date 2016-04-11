var pathi = "/" + window.location.pathname.split("/")[1] + "/ostoskori";

var lisaaOstoskoriin = function(id, tyyppi) {
  $(".ostoskorinappi").attr("disabled", "disabled");
  setTimeout(function(){
    $(".ostoskorinappi").removeAttr("disabled");
  }, 900);
  $.post(pathi, {action: "lisaa", id: id, tyyppi: tyyppi, json: "true"}).done(
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

    var poistaOstoskorista = function(index) {
      $.post(pathi, {action: "poista", index: index, json: "true"}).done(
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
            console.log("Error ostoskorista poistaessa: " + errori);
            naytaVirhe("Pizzan poistaminen ostoskorista ei onnistunut")
          });
        }
    
    var escape = function(stringi) {
    	return $("<div />").text(stringi).html();
    }

        var tyhjennaOstoskori = function() {
          $.post(pathi, {action: "tyhjenna", json: "true"}).done(
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
              return $.ajax({ url: pathi, type: "GET", async: false, cache: false, data: {"ostoskoriJsonina": "true"}}).done(
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
                    if (tuotteita > 0) {
                      ostoskori.map(function(o, i) {
                        ostoskoririvit += "<tr class=\"ostoskori-rivi\"><td>" + escape(o.nimi) + "</td><td class=\"right-align\"><a href=\"#!\" class=\"ostoskori-poistonappi\" onClick=\"poistaOstoskorista(" + escape(o.indeksi) + ")\"><i class=\"material-icons tiny\">clear</i></a></td><td class=\"center-align\">" + escape(formatoiHinta(o.hinta)) + "</td></tr>";
                        yhteishinta += o.hinta;
                      });
                      yhteishinta = formatoiHinta(yhteishinta);
                      var tuotesana = "tuotetta";
                      if (tuotteita == 1) {
                        tuotesana = "tuote"
                      }
                      ostoskoririvit += "<tr class=\"ostoskori-yhteishinta\"><td></td><td class=\"right-align\">Yhteensä</td><td class=\"center-align\">" + yhteishinta + "</td></tr>"
                      $(".ostoskori-table, .ostoskori-tilausnappi, .ostoskori-tyhjennysnappi").removeClass("hide");
                      $("#ostoskori-sulkunappi").removeClass("center").addClass("left");
                      $(".ostoskori-yhteismaara").html("Ostoskorissa on yhteensä " + tuotteita + " " + tuotesana + ".");
                      $(".navbar-yhteishinta").html(" " + yhteishinta);
                      $(".ostoskori-tbody").html(ostoskoririvit);
                    }
                    else {
                      $(".ostoskori-table, .ostoskori-tilausnappi, .ostoskori-tyhjennysnappi").addClass("hide");
                      $("#ostoskori-sulkunappi").removeClass("left").addClass("center");
                      $(".ostoskori-yhteismaara").html("Ostoskori on tyhjä<br>");
                      $(".navbar-yhteishinta").html("");
                    }
                  });
                }

                $(document).ready(function() {
                  printtaaOstoskori();
                });
