$(document).ready(function(){
	
	$.post("rank",
  		  {
  		  },
  		  function(data,status){
  		   
  		    $(data.user).each(function(i, elt){
  		    	$('#tableRank').append("<tr><td>#"+(i+1)+"</td><td>"+elt+"</td></tr>");
  		    });
  		    
  		  });
	
	
});