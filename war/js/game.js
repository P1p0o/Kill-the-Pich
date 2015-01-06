$(function() {
	
    $( ".image_in_slot" ).draggable();
    $( "#dropper" ).droppable({
      drop: function( event, ui ) {  
    	  $(this).alert("bonjour");
      }
    });
  });

