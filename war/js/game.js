<<<<<<< HEAD
<<<<<<< HEAD

=======
>>>>>>> parent of f0c9b63... web socket
$(function() {
	
    $( ".image_in_slot" ).draggable();
    $( "#dropper" ).droppable({
      drop: function( event, ui ) {  
    	  $(this).alert("bonjour");
      }
    });
  });

<<<<<<< HEAD

/*$(window).load(function(){
	console.log("coucou");
	$('.image_in_slot').draggable();
	$('#defausse').droppable({
	      drop: function( event, ui ) {
	          $("#image1").appendTo($("#defausse"));
	      }
    });	
})*/


function dragIt(theEvent) {
	theEvent.dataTransfer.setData("Text", theEvent.target.id);
	console.log("drag");
}

function dropIt(theEvent) {

	var theData = theEvent.dataTransfer.getData("Text");
	
	var theDraggedElement = document.getElementById(theData);
	
	theEvent.target.appendChild(theDraggedElement);

	theEvent.preventDefault();
	
}
=======
$(window).load(function(){
	console.log("coucou");
	$('.image_in_slot').draggable();
	$('#defausse').draggable();
})
>>>>>>> parent of 5f4c0c2... drag an drop
=======
>>>>>>> parent of f0c9b63... web socket
