var cardNumber;

$(document).ready(function() { 
	playerReady = 0;
	$("#action_defausse").css( "visibility", "hidden" );
	$("#action_play").css( "visibility", "hidden" );
	$("#action_cancel").css( "visibility", "hidden" );
	
})

function showAction(id){
	$("#action_defausse").css( "visibility", "visible" );
	$("#action_play").css( "visibility", "visible" );
	$("#action_cancel").css( "visibility", "visible" );
	cardNumber = id;
}

function cancel(){
	$("#action_defausse").css( "visibility", "hidden" );
	$("#action_play").css( "visibility", "hidden" );
	$("#action_cancel").css( "visibility", "hidden" );
	
	
	$("#player1").css("border","1px solid black");
	$("#player1").css("cursor", "default");
	$("#player2").css("border","1px solid black");
	$("#player2").css("cursor", "default");
	$("#player3").css("border","1px solid black");
	$("#player3").css("cursor", "default");
}

function defausse(){
	var card = "card"+cardNumber;
	$("#defausse").text("");
	var image =  $("#"+card).find('img').map(function() { return this; }).get();
	
	$(image).removeAttr( 'class' );
	$(image).addClass('defausseCard');
	$(image).removeAttr('onclick');
	
	$("#defausse").prepend($("#"+card).html());
	$("#"+card).remove();

	$("#action_defausse").css( "visibility", "hidden" );
	$("#action_play").css( "visibility", "hidden" );
	$("#action_cancel").css( "visibility", "hidden" );
}

function play(){
	var card = "card"+cardNumber;
	var image = $("#"+card).html();
	
	var currentPlayer = document.URL;
	currentPlayer = currentPlayer.split("player")[1];
	currentPlayer = currentPlayer.split(".jsp")[0];
	
	image = image.split("img/")[1];
	image = image.split("\">")[0];

	if(image == "bang.jpg"){

		$("#action_defausse").css( "visibility", "hidden" );
		$("#action_play").css( "visibility", "hidden" );
		
		if(currentPlayer != 1){
			$("#player1").css("border","1px solid red");
			$("#player1").css("cursor", "pointer");
			$("#player1").click(function(){
				paff(1);
			});
		}
		
		if(currentPlayer != 2){
			$("#player2").css("border","1px solid red");
			$("#player2").css("cursor", "pointer");
			$("#player2").click(function(){
				paff(2);
			});
		}
		
		if(currentPlayer != 3){
			$("#player3").css("border","1px solid red");
			$("#player3").css("cursor", "pointer");
			$("#player3").click(function(){
				paff(3);
			});
		}
		
		if(currentPlayer != 4){
			$("#player4").css("border","1px solid red");
			$("#player4").css("cursor", "pointer");
			$("#player4").click(function(){
				paff(4);
			});
		}
		
	}
	else{
		alert("pas bang :(");
	}
	
}
function paff(player){
	$("#player1").css("border","1px solid black");
	$("#player1").css("cursor", "default");

	$("#player2").css("border","1px solid black");
	$("#player2").css("cursor", "default");

	$("#player3").css("border","1px solid black");
	$("#player3").css("cursor", "default");
	
	$("#player4").css("border","1px solid black");
	$("#player4").css("cursor", "default");
	
	var json ={};
	json.player = player; 
	$.ajax({
		type: "GET",
		dataType: "json",
		url: "test",
	    data: json			
	});
	
	defausse();
}

function checkForMissed(){
	var currentPlayer = document.URL;
	currentPlayer = currentPlayer.split("player")[1];
	currentPlayer = currentPlayer.split(".jsp")[0];
	
	var player = $("#player"+currentPlayer);
	var images =  $(player).find('img').map(function() { return this; }).get();
	
	$(images).each(function(i,elt){
		var src = elt.src;
		var cardName = src.split("img/")[1];
		if(cardName == "missed.jpg"){
			var cardFound = $(player).find(elt);
			$(cardFound).css("border", "1px solid red");
		}
	});
}

function addPlayer(token){
	var json = {};
	json.token = token;
	
	$.ajax({
		type: "POST",
		dataType: "json",
		url: "game",
	    data: json			
	})
}

function getStartingHand(nb){
	var json = {};
	json.number = nb;
	
	$.ajax({
		type: "POST",
		dataType: "json",
		url: "hand",
	    data: json			
	}).then(function(json){
		console.log(json);
	})
}


