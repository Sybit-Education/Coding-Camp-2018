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
    <div class="row" style="margin-left: -7.5%">
        <div class="col-2 center">
            <div class="text-right" id="friendly-current-field" style="font-weight: bold">A1</div>
        </div>
        <div class="col-2 center">
            <h2 class="text-center">Friendly Field</h2>
        </div>         
        <div class="col-4 center">
            <div class="text-center" id="turn-field" style="font-weight: bold">Warte auf Spieler</div>
        </div>
        <div class="col-2 center" style="margin-left: -3%">
            <h2 class="text-center">Enemy Field</h2>
        </div>
        <div class="col-2 center">
            <div class="text-left" id="enemy-current-field" style="font-weight: bold">A1</div>
        </div>
    </div>
    
    <div class="row">
        <div class="col-1 center"></div>
        <div class="col-4 center" id="friendlyPlayerDiv">
            <canvas id="friendlyPlayer" width="480" height="480"></canvas>
        </div>
        <div class="col-2 center">
            <h3 id="countDownSeconds" class="text-center">60</h3>
            <!-- Button trigger modal -->
                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#shareLinkModal">
                    share link ...
                </button>
        </div>
        <div class="col-4 center" id="enemyPlayerDiv">
            <canvas id="enemyPlayer" width="480" height="480"></canvas>
        </div>
        <div class="col-1 center"></div>
    </div>
</div>




<!-- Modal -->
<div class="modal fade" id="shareLinkModal" tabindex="-1" role="dialog" aria-labelledby="schareLink" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Share Link</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <textarea rows="2" cols="55" id="Url" style="width: 100%"></textarea>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="copyUrl()">
                    Copy to clipboard ...
                </button>
            </div>
        </div>
    </div>
</div>

<template:footer/>

<template:javascript/>
<script>
    window.onload = function () {
        let reloadFlag = window.localStorage.getItem("reloadFlag") ? window.localStorage.getItem("reloadFlag") : "false";
        if(reloadFlag === "false"){
            Battleship.init();
            Battleship.webSocketHandler.connect().then(function () {
                Battleship.webSocketHandler.subscribeToMatch(function (message) {
                    Battleship.receiveMessagesFromWebSocket(message);
                });
                Battleship.saveGamefield();
                window.localStorage.setItem("reloadFlag","true");
            })

        }else{
            Battleship.init();
            Battleship.webSocketHandler.connect().then(function () {
                Battleship.webSocketHandler.subscribeToMatch(function (message) {
                    Battleship.receiveMessagesFromWebSocket(message);
                });
                Battleship.requestGamefieldData();
            })
        }
    };
</script>

<script>
    let test = localStorage.getItem("keyMatchLink");
    console.log(test);
    document.getElementById("Url").value = localStorage.getItem("keyMatchLink");
    function copyUrl(){
    let copyUrl = document.getElementById("Url");
    copyUrl.select();
    document.execCommand("copy");
    }
</script>
</body>
</html>
