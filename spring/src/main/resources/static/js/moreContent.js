$(document).ready(function(){
  var coontAll = 0;
  $("#btnMoreEvents").on("click", function() {
	  $('#spinner').html("<img src='/images/spinner.gif>");
	  coontAll++;
	  $.get("/moreContent", {
		  page: coontAll
      })
      .done(function(data) {
    	$('#spinner').empty();
        if (!$.trim(data)) {
          $("#btnMoreEvents").html("No hay más resultados");
        } else {
          $("#listEvents").append(data);
        }
      });
  });
});
