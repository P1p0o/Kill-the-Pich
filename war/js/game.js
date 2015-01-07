<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes
$(function() {
	
    $( ".image_in_slot" ).draggable();
    $( "#dropper" ).droppable({
      drop: function( event, ui ) {  
    	  $(this).alert("bonjour");
      }
    });
  });


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
<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes
$(window).load(function(){
	console.log("coucou");
	$('.image_in_slot').draggable();
	$('#defausse').draggable();
	
	
})
<<<<<<< Updated upstream
=======

function openMenu(){
	
	
	alert("bonjour !");
	
}

>>>>>>> Stashed changes
