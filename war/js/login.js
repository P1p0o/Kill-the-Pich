$(document).ready(function(){ //au chargement on affiche tantot la login area, tantot le message de bienvenue + score
	$.ajax({
		type: "POST",
		dataType:"json",
		url:"retournesession",
	}).then(function(json){
		console.log(json);
		if(json.response == "true")
		{
			$("#login_area").hide();
			comment="Bienvenue " + json.login ;
			document.getElementById('welcome').innerHTML = comment;
			score = " votre score est de " + json.score;
			document.getElementById('score').innerHTML = score;
			$("#joinGameButton").attr('onclick', "joinGame()");
			return;
		}
		else
		{
			$("#logout_area").hide();
			comment="Vous n'etes pas connecte";
			document.getElementById('errors').innerHTML = comment;
			$("#joinGameButton").attr('onclick', "alertedNotConnected()");
			return;
		}
	});
});

function alertedNotConnected(){
	alert("Veuillez vous connecter pour pouvoir jouer");
}

function logout()
{
	var json = {}; // useless mais ca veut pas marcher sans
	$.ajax({
		type: "POST",
		dataType:"json",
		url:"logout",
		data:json
	}).then(function(json){//window.location.replace("index.html");
		window.location.replace("index.html");
	});
}


function login()
{
	var pass = $('#pass').val();
	var email = $('#email').val();
	var comment = "";
	var score = "";
	document.getElementById('errors').innerHTML = comment;
	if(pass.length<8)
	{
		comment="Mot de passe trop court (8 caracteres minimum)";
		document.getElementById('errors').innerHTML = comment;
		return;
	}
	if(pass.length>20){
		comment="Mot de passe trop long (20 caractres maximum)";
		document.getElementById('errors').innerHTML = comment;
		return;
	}
	if(email.length>35){
		comment="Email trop long (35 caractres maximum)";
		document.getElementById('errors').innerHTML = comment;
		return;
	}
	else 
	{
		if(email.match(/\S+@\S+\.\S+/).length=="1")
		{ 
			var json = {};
			json.pass = pass;
			json.email = email;
			$.ajax({
				type: "POST",
				dataType:"json",
				url:"login",
				data:json
			}).then(function(json){
				if(json.response == "true")
				{
					document.getElementById('errors').innerHTML = comment;
					window.location.replace("index.html");
					return;
				}
				if(json.response == "false1")
				{
					$("#logout_area").hide();
					comment="Vous n'etes pas connecte";
					document.getElementById('errors').innerHTML = comment;
					$("#joinGameButton").attr('onclick', "alertedNotConnected()");
					alert("Caractere interdit dans le champ e-mail");
					return;
				}
				if(json.response == "false2")
				{
					$("#logout_area").hide();
					comment="Vous n'etes pas connecte";
					document.getElementById('errors').innerHTML = comment;
					$("#joinGameButton").attr('onclick', "alertedNotConnected()");
					alert("Caractere interdit dans le champ mot de passe");
					return;
				}
				else
				{
					comment="Identifiant ou mot de passe incorrect.";
					document.getElementById('errors').innerHTML = comment;
					return;
				}
			});
		}
		else
		{
			comment="Adresse mail non conforme.";
			document.getElementById('errors').innerHTML = comment;
			return;
		}
	}
}

