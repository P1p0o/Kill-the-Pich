<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

 
<%@ page import="com.google.appengine.api.channel.ChannelService" %>
<%@ page import="com.google.appengine.api.channel.ChannelServiceFactory" %>
<%@page import="java.util.ArrayList"%>
 
 
<%
 
ChannelService channelService = ChannelServiceFactory.getChannelService();
String token = channelService.createChannel("player3");
 
%>
 
<!DOCTYPE html>
<html>
    <head>
         <meta charset="utf-8">
	    <title>Kill the Pich</title>
	    <link href="css/game.css" rel="stylesheet">
	    <link href="css/player3.css" rel="stylesheet">
	
    </head>
    <body>
    	<div id="plateau">
		<div id="player1">1</div>
		<div id="player4">4</div>
		<div id="centerPlateau">
			<div id="piocheDef">
				<div id="pioche">Pioche</div>
				<div id="defausse" class="dropper">Defausse</div>
			</div>
			<div id="notifications">En attente d'autres joueurs...</div>
		</div>
		<div id="action">
			<div id="action_play" class="action_div" onclick="play()">Play card</div>
			<div id="action_defausse" class="action_div" onclick="defausse()">Defausse card</div>
			<div id="action_cancel" class="action_div" onclick="cancel()">Cancel</div>
			<div id="action_endOfTurn" class="action_div" onclick="endOfTurn()">End of turn</div>
		</div>
		<div id="player2">2</div>
		<div id="player3">
			<div id="information">
				<div id="life">
					life
				</div>
				<div id="role">
					role
				</div>
			</div>
			<div id="cards">
			</div>
		</div>
	</div>
    
   	    <script src='//code.jquery.com/jquery-1.7.2.min.js'></script>
	    <script src="/_ah/channel/jsapi"></script>
	    <script src="js/jquery.js"></script>
		<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.4/jquery-ui.min.js"></script>
		<script src="js/jquery-ui.js"></script>
		<script src="js/jquery-2.1.1.js"></script>
		<script src="js/game.js"></script>
		<script src="js/channelListener.js"></script>
    	<script>
	    var token ="<%=token %>";// This will creaete unique identifier(some id created by google api + ur key)
		channel = new goog.appengine.Channel('<%=token%>');    
		socket = channel.open(); 
		
		socket.onopen = function() {
			addPlayer("player3", token);
		}
		
		
		socket.onmessage = function(message) {
			listenToChannel(message, 3);
		}
    	</script>
    </body>
</html> 
