<%--

--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<html>
    <head>
        <template:header title="Battleships"/>
    </head>
    <body>
        <template:navigation/>

        <div class="container">
            <h1>Game over</h1>
            <p>${winner.id}</p>
            <div class="row">
                <div class="col">
                    <div class="form-group">
                        <label for="message">The Message</label>
                        <input type="text" id="message" class="form-control">
                    </div>
                    <button id="send" class="btn btn-primary" onclick="Battleship.webSocketHandler.sendMessage()">Send</button>
                </div>
            </div>
        </div>
        <hr>
       
        <template:footer/>

        <template:javascript/>
        <script>
            window.onload = function () {
                Battleship.init();

                Battleship.webSocketHandler.connect().then(function () {
                    Battleship.webSocketHandler.subscribeToMatch();
                    Battleship.saveGamefield();
                })
            };
        </script>
    </body>
</html>
