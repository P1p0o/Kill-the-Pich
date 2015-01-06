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
String token = channelService.createChannel("mimo");
 
%>
 
<!DOCTYPE html>
<html>
    <head>
        <script src='//code.jquery.com/jquery-1.7.2.min.js'></script>
        <script src="/_ah/channel/jsapi"></script>
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
    </body>
</html>