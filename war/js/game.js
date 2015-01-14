var cardNumber;

$(document).ready(function() { 
	
	playerReady = 0;
	$("#action_defausse").css( "visibility", "hidden" );
	$("#action_play").css( "visibility", "hidden" );
	$("#action_cancel").css( "visibility", "hidden" );
	
});

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
	console.log(image);
	image = image.split("img/")[1];
	image = image.split("\"")[0];

	console.log(image);
	
	if(image == "paf.jpg"){

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
	if(image == "missed.jpg"){
		missed(currentPlayer);
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
	json.card = "paf";
	$.ajax({
		type: "POST",
		dataType: "json",
		url: "card",
	    data: json			
	})
		
	defausse();
	
}

function missed(player){
	var json ={};
	json.card = "missed";
	json.player = player;
	$.ajax({
		type: "POST",
		dataType: "json",
		url: "card",
	    data: json			
	});
	
	defausse();
}

function loseLife(player){
	console.log("loseLife");
	var json ={};
	json.card = "loseLife";
	json.player = player;
	console.log(json);
	$.ajax({
		type: "POST",
		dataType: "json",
		url: "card",
	    data: json			
	});
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
		else{
			var cardFound = $(player).find(elt);
			$(cardFound).removeAttr( 'class' );
			$(cardFound).addClass('defausseCard');
			$(cardFound).removeAttr('onclick');
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
		$(json.cards).each(function(i,elt){
			i++;
			if(elt.name == "paf"){
				$("#cards").append('<div class="slot_horizontal" id="card'+i+'">\
				<img class="image_in_slot" onclick="showAction('+i+')" src="img/paf.jpg"/>\
				</div>');
			}
			if(elt.name == "rate"){
				$("#cards").append('<div class="slot_horizontal" id="card'+i+'">\
				<img class="image_in_slot" onclick="showAction('+i+')" src="img/missed.jpg"/>\
				</div>');
			}
		});
		
		$("#life").text(json.life);
		$("#role").text(json.role);
	});
}


