var paivitaValinta = function() {
	var valittu = $("#kotiinkuljetus").prop("checked");
	if (valittu == true && $("#osoitediv").is(":hidden")) {
		//$("#osoitediv").removeClass("hide");
		$("#osoitediv").slideToggle();
	}
	else if (valittu == false && $("#osoitediv").is(":visible")){
		//$("#osoitediv").addClass("hide");
		$("#osoitediv").slideToggle();
	}
}

$("#toimitustapadiv input:radio").on("change",function() {
	paivitaValinta();
});

$(document).ready(function() {
	var valittu = $("#kotiinkuljetus").prop("checked");
	if (valittu == false) {
		$("#osoitediv").toggle();
	}
});