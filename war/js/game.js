var cardNumber;

$(document).ready(function() { 
	
	playerReady = 0;
	$("#action_defausse").css( "visibility", "hidden" );
	$("#action_play").css( "visibility", "hidden" );
	$("#action_cancel").css( "visibility", "hidden" );
	$("#action_endOfTurn").css( "visibility", "hidden" );
	
});

function showAction(id){
	$("#action_defausse").css( "visibility", "visible" );
	$("#action_play").css( "visibility", "visible" );
	$("#action_cancel").css( "visibility", "visible" );
	cardNumber = id;
}

function showDefausse(id){
	$("#action_defausse").css( "visibility", "visible" );
	$("#action_play").css( "visibility", "hidden" );
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
	
	var currentPlayer = document.URL;
	currentPlayer = currentPlayer.split("player")[1];
	currentPlayer = currentPlayer.split(".jsp")[0];

	var json ={};
	var src = $(image).attr('src');
	
	src = src.split("img/")[1];
	src = src.split(".jpg")[0];
	json.player = currentPlayer; 
	json.card = src;
	json.defausse = "true";
	$.ajax({
		type: "POST",
		dataType: "json",
		url: "card",
	    data: json			
	})/*.always(function() {
		getStartingHand(currentPlayer);	
	});*/
	
	
	
}

function play(){
	var card = "card"+cardNumber;
	var image = $("#"+card).html();
	
	var currentPlayer = document.URL;
	currentPlayer = currentPlayer.split("player")[1];
	currentPlayer = currentPlayer.split(".jsp")[0];
	
	image = image.split("img/")[1];
	image = image.split("\"")[0];
	
	if(image == "paf.jpg"){

		$("#action_defausse").css( "visibility", "hidden" );
		$("#action_play").css( "visibility", "hidden" );
		
		if((currentPlayer != 1) && ($("#player1").text() != "Ce joueur est mort")){
			$("#player1").css("border","1px solid red");
			$("#player1").css("cursor", "pointer");
			$("#player1").click(function(){
				paff(1);
			});
		}
		else{
			$("#player1").removeAttr('onclick');
		}
		
		if((currentPlayer != 2) && ($("#player2").text() != "Ce joueur est mort")){
			$("#player2").css("border","1px solid red");
			$("#player2").css("cursor", "pointer");
			$("#player2").click(function(){
				paff(2);
			});
		}
		else{
			$("#player2").removeAttr('onclick');
		}
		
		if((currentPlayer != 3) && ($("#player3").text() != "Ce joueur est mort")){
			$("#player3").css("border","1px solid red");
			$("#player3").css("cursor", "pointer");
			$("#player3").click(function(){
				paff(3);
			});
		}
		else{
			$("#player3").removeAttr('onclick');
		}
		
		if((currentPlayer != 4) && ($("#player4").text() != "Ce joueur est mort")){
			$("#player4").css("border","1px solid red");
			$("#player4").css("cursor", "pointer");
			$("#player4").click(function(){
				paff(4);
			});
		}
		else{
			$("#player4").removeAttr('onclick');
		}
		
	}
	if(image == "missed.jpg"){
		missed(currentPlayer);
	}
	if(image == "chasse.jpg"){
		chasse(currentPlayer);
	}
	if(image == "poulemouth.jpg"){
		poulemouth(currentPlayer);
	}
	if(image == "hippopotamouth.jpg"){
		hippopotamouth(currentPlayer);
	}
}

function chasse(player){
	var json ={};
	json.player = player; 
	json.card = "chasse";
	$.ajax({
		type: "POST",
		dataType: "json",
		url: "card",
	    data: json			
	});
	
	defausse();
}

function hippopotamouth(player){
	var json ={};
	json.player = player; 
	json.card = "hippopotamouth";
	$.ajax({
		type: "POST",
		dataType: "json",
		url: "card",
	    data: json			
	});
	
	defausse();
}

function poulemouth(player){
	var json ={};
	json.player = player; 
	json.card = "poulemouth";
	$.ajax({
		type: "POST",
		dataType: "json",
		url: "card",
	    data: json			
	});
	
	defausse();
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

	var currentPlayer = document.URL;
	currentPlayer = currentPlayer.split("player")[1];
	currentPlayer = currentPlayer.split(".jsp")[0];
	json.pafeur = currentPlayer;
	
	$.ajax({
		type: "POST",
		dataType: "json",
		url: "card",
	    data: json			
	});
	defausse();
	
	$(document).ajaxStop(function () {
		disablePlayer();
	});
	
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
	}).always(function(){
		defausse();
		reEnablePlayer();
	})
}

function loseLife(player){
	var json ={};
	json.card = "loseLife";
	json.player = player;
	$.ajax({
		type: "POST",
		dataType: "json",
		url: "card",
	    data: json			
	});
}


function enableMissed(){
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
			var number = $(cardFound).parent().attr('id').split('card')[1];
			number = "showAction("+number+")";
			$(cardFound).removeAttr( 'class' );
			$(cardFound).addClass('image_in_slot');
			$(cardFound).removeAttr('onclick');
			$(cardFound).attr('onclick', number);
		}
		else{
			var cardFound = $(player).find(elt);
			$(cardFound).removeAttr( 'class' );
			$(cardFound).addClass('disabledCard');
			$(cardFound).removeAttr('onclick');
		}
	});
	
}

function addPlayer(name, token){
	var json = {};
	json.name = name;
	json.token = token;
	
	$.ajax({
		type: "POST",
		dataType: "json",
		url: "game",
	    data: json			
	})
}

var refreshHand = function(nb){
	var r = $.Deferred();
	
	var json = {};
	json.number = nb;
	
	$.ajax({
		type: "POST",
		dataType: "json",
		url: "hand",
	    data: json			
	}).then(function(json){
		if(json.life > 0){
			$("#cards").empty();
			$(json.cards).each(function(i,elt){
				i++;
				$("#cards").append('<div class="slot_horizontal" id="card'+i+'">\
				<img class="image_in_slot" onclick="showAction('+i+')" src="img/'+elt.name+'.jpg"/>\
				</div>');
				
			});

			$("#life").text(json.login + " Points de vie : "+json.life);
			if(json.role == "hll"){
				$("#role").text("Le Meurtrieur");
			}
			if(json.role == "sherif"){
				$("#role").text("Le Chef");
			}
			if(json.role == "renegat"){
				$("#role").text("L'imposteur");
			}
			if(json.role == "adjoint"){
				$("#role").text("Le Blond");
			}
		}
		else{
			$("#cards").empty();
			$("#role").text("Tu es mort!!");
			$("#life").text("");
		}
		
		if(json.defausse){
			$("#defausse").empty();
			$("#defausse").text("Defausse :"+ json.defausseSize+ " cards");
			$("#defausse").append('<img class="defausseCard" src="img/'+json.defausse+'.jpg"/>');
		}
		else{
			$("#defausse").text("Défausse : 0 cartes");
		}
		if(json.canPaf == false){
			console.log("c FAUX");
			var player = document.URL;
			player = player.split("player")[1];
			player = player.split(".jsp")[0];
			player = ("#player")+player;
			var images =  $(player).find('img').map(function() { return this; }).get();
			
			$(images).each(function(i,elt){
				var cardFound = $(player).find(elt);
				var src = elt.src;
				var cardName = src.split("img/")[1];
				if(cardName == "paf.jpg"){
					var number = $(cardFound).parent().attr('id').split('card')[1];
					number = "showDefausse("+number+")";
					console.log(number);
					$(cardFound).removeAttr( 'class' );
					$(cardFound).addClass('image_in_slot');
					$(cardFound).removeAttr('onclick');
					$(cardFound).attr('onclick', number);
				console.log(cardFound);	
				}
			});
		}
		
		$(json.otherPlayers).each(function(i,elt){
			var playerName = "#"+elt.name;
			if(elt.role == "sherif"){
				$(playerName).text("");
				$(playerName).text(elt.login);
				$(playerName).append("<p>Points de vie : "+elt.life);
				$(playerName).append("<p>Nombre de cartes : "+elt.nbCards+"</p>");
				$(playerName).append("<p>Le Chef</p>");
			}
			else{
				$(playerName).text("");
				$(playerName).text(elt.login);
				$(playerName).append("<p>Points de vie : "+elt.life);
				$(playerName).append("<p>Nombre de cartes : "+elt.nbCards+"</p>");
				
			}
			if(elt.life == 0){
				$(playerName).text("Ce joueur est mort");
			}
		});
		
		$("#pioche").empty();
		$("#pioche").text("Pioche: "+json.pioche+" cartes");
		$("#pioche").append('<img class="defausseCard" src="img/back.jpg"/>');
	});
	
	setTimeout(function () {
	    // and call `resolve` on the deferred object, once you're done
	    r.resolve();
	  }, 2500);

	  // return the deferred object
	  return r;
}

function enablePlayer(){

	var player = document.URL;
	player = player.split("player")[1];
	player = player.split(".jsp")[0];
	player = ("#player")+player;
	var images =  $(player).find('img').map(function() { return this; }).get();
	
	$(images).each(function(i,elt){
		var cardFound = $(player).find(elt);
		var src = elt.src;
		var cardName = src.split("img/")[1];
		if(cardName == "missed.jpg"){
			var number = $(cardFound).parent().attr('id').split('card')[1];
			number = "showDefausse("+number+")";
			
			$(cardFound).removeAttr( 'class' );
			$(cardFound).addClass('image_in_slot');
			$(cardFound).removeAttr('onclick');
			$(cardFound).attr('onclick', number);
			
		}
		
		else{
			var number = $(cardFound).parent().attr('id').split('card')[1];
			number = "showAction("+number+")";
			
			$(cardFound).removeAttr( 'class' );
			$(cardFound).addClass('image_in_slot');
			$(cardFound).removeAttr('onclick');
			$(cardFound).attr('onclick', number);
		}
		
	});
	$("#action_endOfTurn").css( "visibility", "visible" );
}

function disablePlayer(){
	var player = document.URL;
	player = player.split("player")[1];
	player = player.split(".jsp")[0];
	player = ("#player")+player;
	
	var images =  $(player).find('img').map(function() { return this; }).get();
	
	$(images).each(function(i,elt){
		var cardFound = $(player).find(elt);
		$(cardFound).removeAttr( 'class' );
		$(cardFound).addClass('disabledCard');
		$(cardFound).removeAttr('onclick');
	});

	$("#action_endOfTurn").css( "visibility", "hidden" );
}

function endOfTurn(){
	var nbOfImages = $("#cards img").length;
	
	$("#action_defausse").css( "visibility", "hidden" );
	$("#action_play").css( "visibility", "hidden" );
	$("#action_cancel").css( "visibility", "hidden" );
	var life = $("#life").text().split(" : ")[1];
	
	if(nbOfImages <= life){
		changePlayer();
	}
	else{
		$("#notifications").text("Tu ne peux pas avoir plus de "+life+" cartes.");
		$("#notifications").append("<p>Défausse toi de cartes avant de passer ton tour.</p>");
	}

}

function changePlayer(){
	var json = {};
	json.turn = "next";
	$.ajax({
		type: "POST",
		dataType: "json",
		url: "turn",
	    data: json		
		
	});
}

function reEnablePlayer(){
	var json = {};
	json.turn = "";
	$.ajax({
		type: "POST",
		dataType: "json",
		url: "turn",
	    data: json		
	})
}

function skipDodge(){
	var player = document.URL;
	player = player.split("player")[1];
	player = player.split(".jsp")[0];
	
	loseLife(player);
	reEnablePlayer();
}
