$(document).ready(function(){
  var coontAll = 0;
  $("#btnMoreGroups").on("click", function() {
	  $('#spinner').html("<img src='/images/spinner.gif>");
	  coontAll++;
	  $.get("/moreGroups", {
		  page: coontAll
      })
      .done(function(data) {
    	$('#spinner').empty()
        if (!$.trim(data)) {
          $("#btnMoreGroups").html("No hay más resultados");
        } else {
          $("#listGroups").append(data);
        }
      });
  });
});
