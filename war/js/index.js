function joinGame(){
	$.ajax({
		type: "GET",
		dataType: "json",
		url: "game"			
	}).then(function(json){
		
		if(json.numberOfPlayer == 0){
			window.location.href = "player1.jsp";
			
			
		}
		if(json.numberOfPlayer == 1){
			window.location.href = "player2.jsp";
		}
		if(json.numberOfPlayer == 2){
			window.location.href = "player3.jsp";
		}
		if(json.numberOfPlayer == 3){
			window.location.href = "player4.jsp";
		}
		if(json.numberOfPlayer == 4){
			alert("trop de gens dsl trou de balle");
		}
	})}

function goToHall(){
	
	window.open("Hall.html",'_self');
}

function backMainMenu(){
	
	window.open("index.html",'_self'); 
}