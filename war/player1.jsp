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
String token = channelService.createChannel("player1");
 
%>
 
<!DOCTYPE html>
<html>
    <head>
        <script src='//code.jquery.com/jquery-1.7.2.min.js'></script>
        <script src="/_ah/channel/jsapi"></script>
        <script src="js/test.js"></script>
    </head>
    <body>
    
    <script>
    var token ="<%=token %>";// This will creaete unique identifier(some id created by google api + ur key)
 
	channel = new goog.appengine.Channel('<%=token%>');    
	    socket = channel.open();    
		
	socket.onopen = function() {$('#messages').append('<p>Connected!</p>'); };
    socket.onmessage = function(message) {
    console.log(message); 
    console.log('message' + message); 
    	$('#messages').append('<p>' + message.data + '</p>');
    };
    socket.onerror = function() { $('#messages').append('<p>Connection Error!</p>'); };
    socket.onclose = function() { $('#messages').append('<p>Connection Closed!</p>'); };        
    
    </script>
    
		LOG Messages
		<p id="messages"></p>
		
		<button onclick=paff(2)>Paff Le 2</button>
		<button onclick=paff(3)>Paff Le 3</button>
		<button onclick=paff(4)>Paff Le 4</button>
    </body>
</html>