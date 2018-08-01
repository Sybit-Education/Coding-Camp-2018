<%--
  Created by IntelliJ IDEA.
  User: ngz
  Date: 29.06.2018
  Time: 14:45
  To change this template use File | Settings | File Templates.
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

<div class="container-fluid">
    <div class="row">
    </div>
</div>
<hr>
<div class="container-fluid">
    <div class="row">
        <div class="col-2">
            <div id="friendly-current-field" style="margin-left: 4%; font-weight: bold">A1</div>
        </div>
        <div class="col-3">
            <h2 class="text-center">Friendly Field</h2>
        </div>         
        <div class="col-2">
            <div id="turn-field" style="font-weight: bold">Warte auf Spieler</div>
        </div>
        <div class="col-2">
            <div id="enemy-current-field" style="margin-left: 4%; font-weight: bold">A1</div>
        </div>
        <div class="col-3">
            <h2 class="text-center">Enemy Field</h2>
        </div>
    </div>
    <div class="row">
        <div class="col"></div>
        <div class="col" id="friendlyPlayerDiv">
            <canvas id="friendlyPlayer" width="480" height="480"></canvas>
        </div>
        <div class="col"></div>
        <div class="col" id="enemyPlayerDiv">
            <canvas id="enemyPlayer" width="480" height="480"></canvas>
        </div>
        <div class="col"></div>
    </div>
</div>
<template:footer/>

<template:javascript/>
<script>
    window.onload = function () {
        Battleship.init();
        Battleship.webSocketHandler.connect().then(function () {
            Battleship.webSocketHandler.subscribeToMatch(function (message) {
                Battleship.receiveMessagesFromWebSocket(message)
            });
            Battleship.saveGamefield();
        })
    };
</script>
</body>
</html>
