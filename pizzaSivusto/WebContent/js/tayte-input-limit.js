// Limitataan lisättävien täytteiden määrä viiteen

var updateCount = function() {
	var checked = $("#pizza-taytteet input[name='pizzatayte']:checked").length;

	if (checked > 4) {
		$("#pizza-taytteet input:checkbox:not(:checked)")
				.each(function() {
					$(this).attr("disabled", true);
				});
	} else {
		$("#pizza-taytteet input:checkbox").each(
				function() {
					$(this).attr("disabled", false);
				});
	}
	
	$("#taytteet-label").text("Täytteet " + checked + " / 5");
}

$("#pizza-taytteet input:checkbox").on("change",function() {
	updateCount();
});

$(document).ready(function() {
	updateCount();
});