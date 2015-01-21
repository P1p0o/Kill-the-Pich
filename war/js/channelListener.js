function listenToChannel(message, play){	
	if(message.data.indexOf("paf") > -1){
		if(message.data.indexOf("paf"+play) > -1){
			$("#notifications").text("Vous vous êtes fait pafer!!");
		}
		else{
			var player = message.data.split("paf")[1];
			var name = $("#player"+player).text();
			console.log(name);
			console.log(name.split("Points"));
			name = name.split("Points")[0];
			$("#notifications").text(name+" s'est fait pafer!!");
		}
	}
	
	if(message.data.indexOf("missed") > -1){
		if(message.data.indexOf("missed"+play) > -1){
			$("#notifications").append("<p>Vous ne perdez pas de point de vie!!</p>");
		}
		else{
			var player = message.data.split("missed")[1];
			var name = $("#player"+player).text();
			console.log(name);
			console.log(name.split("Points"));
			name = name.split("Points")[0];
			$("#notifications").append("<p>"+name+" esquive le paf!!</p>");
		}
	}

	if(message.data.indexOf("startGame") > -1){
		$("#notifications").text("4 joueurs. La partie commence");
		refreshHand(play);
		if(message.data.indexOf("Turn"+play) > -1){
			$("#notifications").append("<p>Vous êtes le chef. Vous commencez à jouer.</p>");

			$(document).ajaxStop(function () {
			     enablePlayer();
			  });
		}
		else{
			var player = message.data.split("Turn")[1];
			var name = $("#player"+player).text();
			console.log(name);
			console.log(name.split("Points"));
			name = name.split("Points")[0];
			$("#notifications").append("<p>"+name+" est le chef. Il commence à jouer.</p>");
			
			$(document).ajaxStop(function () {
			      disablePlayer();
			  });
		}
	}	
	
	if(message.data.indexOf("turn") > -1){

		refreshHand(play);
		if(message.data.indexOf("turn"+play) > -1){
			$("#notifications").text("A vous de jouer...");

			$(document).ajaxStop(function () {
			     enablePlayer();
			});
		}
		else{
			var player = message.data.split("turn")[1];
			var name = $("#player"+player).text();
			console.log(name);
			console.log(name.split("Points"));
			name = name.split("Points")[0];
			$("#notifications").text(name+" est en train de jouer...");
			
			$(document).ajaxStop(function () {
			      disablePlayer();
			});
		}
	}	
	
	if(message.data.indexOf("refreshHand") > -1){
		refreshHand(play);
	}	
	if(message.data.indexOf("loseLife") > -1){
		if(message.data.indexOf("loseLife"+play) > -1){
			$("#notifications").append("<p>Tu perds 1 point de vie !!</p>");
		}
		else{
			var player = message.data.split("loseLife")[1];
			var name = $("#player"+player).text();
			console.log(name);
			console.log(name.split("Points"));
			name = name.split("Points")[0];
			$("#notifications").append("<p>"+name+" perd 1 point de vie!!</p>");
		}
		refreshHand(play);
		reEnablePlayer();
	}	
	if(message.data.indexOf("waitAnswer") > -1){
		if(message.data.indexOf("waitAnswer"+play) > -1){
			$("#notifications").text("A toi d'esquiver!!");
	        $("#notifications").append("<p>Si tu ne veux pas ou peux pas esquiver click ici</p>");
	        $("#notifications").append("<button onclick='skipDodge()'>Skip</button>");
			$(document).ajaxStop(function () {
			     enableMissed();
			});

		}
		else{
			var player = message.data.split("waitAnswer")[1];
			var name = $("#player"+player).text();
			console.log(name);
			console.log(name.split("Points"));
			name = name.split("Points")[0];
			$("#notifications").append("<p>"+name+" peut esquiver!!</p>");
		}
	}
	if(message.data.indexOf("win") > -1){
		alert("Le vainqueur est : "+ message.data.split("win")[1]);
		window.location.href = "index.html";
	}
	if(message.data.indexOf("poulemouth") > -1){
		if(message.data.indexOf("poulemouth"+play) > -1){
			$("#notifications").text("Vous dévorez un poulemouth!!");
			$("#notifications").append("<p>Vous gagnez un point de vie</p>");
		}
		else{
			var player = message.data.split("poulemouth")[1];
			var name = $("#player"+player).text();
			console.log(name);
			console.log(name.split("Points"));
			name = name.split("Points")[0];
			$("#notifications").text(name+" dévore un poulemouth");
			$("#notifications").append("<p>Il gagne un point de vie</p>");
		}
	}
	
	if(message.data.indexOf("chasse") > -1){
		if(message.data.indexOf("chasse"+play) > -1){
			$("#notifications").text("Vous partez à la chasse!!");
		}
		else{
			var player = message.data.split("chasse")[1];
			var name = $("#player"+player).text();
			console.log(name);
			console.log(name.split("Points"));
			name = name.split("Points")[0];
			$("#notifications").text(name+" part à la chasse");
		}
	}
	
	if(message.data.indexOf("hippopotamouth") > -1){
		if(message.data.indexOf("hippopotamouth"+play) > -1){
			$("#notifications").text("Vous attrapez un hippopotamouth!!");
		}
		else{
			var player = message.data.split("hippopotamouth")[1];
			var name = $("#player"+player).text();
			console.log(name);
			console.log(name.split("Points"));
			name = name.split("Points")[0];
			$("#notifications").text(name+" attrape un hippopotamouth");
		}
		$("#notifications").append("<p>Tous les joueurs gagnent un point de vie</p>");
	}
	
	
	
}
