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
        <template:header title="Battleships" />
    </head>

    <body>

        <template:navigation />    

        <main class="container">
            <h1>Battleships</h1>
            <p>Spiele online mit deinen Freunden "Schiffe versenken"! </p>
            <p>Erzeuge einfach ein neues Spiel und teile den Link - schon kann es los gehen!</p>
            <button id="Link" class="btn btn-primary" onclick="Battleship.matchHandler.connectToMatch()">Neues Spiel ...</button>


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
