function paff(player){
	console.log("paff")
	var json ={};
	json.player = player; 
	$.ajax({
		type: "GET",
		dataType: "json",
		url: "test",
	    data: json			
	})
}