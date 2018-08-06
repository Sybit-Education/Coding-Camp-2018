<%-- 
    Document   : index
    Created on : 04.05.2018, 14:29:55
    Author     : ssr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>

<!DOCTYPE html>
<html>
    <head>
        <template:header title="Battleship" />
    </head>

    <body>

        <template:navigation />    

        <main class="container">
            <h1 style="float:left; left:50%; position: absolute; margin-top: 50px; margin-left: -100px;">Battleship</h1>
            <p style="float:left; left:50%; position: absolute; margin-top: 550px; margin-left: -300px; color:white; font-size: 1.2rem">Spiele online mit deinen Freunden "Schiffe versenken"! </p>
            <p style="float:left; left:50%; position: absolute; margin-top: 530px; margin-left: -300px; color:white; font-size: 1.2rem">Erzeuge einfach ein neues Spiel und teile den Link - schon kann es los gehen!</p>
            <button id="Link" class="btn btn-primary" onclick="Battleship.matchHandler.connectToMatch()" 
                    style="font-size:1.5rem;float:left; left:50%; position: absolute; margin-top: 100px; margin-left: -102.5px; color:#b51682;">Neues Spiel ...</button>
            
            <img src="resources/images/battleship.png" alt="Kriegsschiff" style="margin-left: -100px; margin-top: 100px">


            <!--<button id="Queue" class="btn btn-primary" onclick="connectToQueue()">Queue</button>-->
        </main>

        <template:footer />
        <template:javascript/>

        <script>
            window.onload = function () {
                window.localStorage.clear();
                Battleship.utilHandler.removeCookie("userId");
                Battleship.utilHandler.removeCookie("userName");
            };
        </script>

    </body>
</html>
