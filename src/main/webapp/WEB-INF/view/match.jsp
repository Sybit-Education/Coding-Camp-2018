<!DOCTYPE html>

<%--
  Created by IntelliJ IDEA.
  User: fzr
  Date: 22.05.2018
  Time: 12:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>

<html>
    <head>
        <template:header title="Battleships" />
    </head>

    <body>

<template:navigation/>

<main class="container">
    <h1>Match</h1>
    <div class="row">
        <div class="col">
            <!-- Button trigger modal -->
            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#shareLinkModal">
                Share Match Link
            </button>
            <button id="checkShips" class="btn btn-default" onclick="Battleship.allShipsOnStage();">Weiter</button>
        </div>
        <div class="col">
            <div id="current-field" style="margin-top: 2%; font-weight: bold"></div>
        </div>
    </div>
    <div class="row">
        <div class="col">
            <canvas id="canvas" width="800" height="480"></canvas>
        </div>
    </div>
</main>

<!-- Modal -->
<!-- TODO Modal implementieren -->

        <button id="toastShipPlacement" type="button" class="btn btn-secondary" data-toggle="snackbar" data-style="toast" data-timeout="2000" data-content="Schiff kann nicht auf diese Position gesetzt werden">
            Toast
        </button>

        <button id="toastShipNonPlacement" type="button" class="btn btn-secondary" data-toggle="snackbar" data-style="toast" data-timeout="2000" data-content="Alle Schiffe auf das Spielfeld setzen">
            Toast
        </button>

        <button id="toastShipRotation" type="button" class="btn btn-secondary" data-toggle="snackbar" data-style="toast" data-timeout="2000" data-content="Schiff kann nicht gedreht werden">
            Toast
        </button>

        <template:footer />
        <template:javascript/>

<script>
//TODO Link kopieren
</script>

<script>
    window.onload = function () {
        Battleship.init();

                // Battleship.webSocketHandler.connect().then(function () {
                //     Battleship.webSocketHandler.subscribeToMatch();
                // })
            };

            let showSnackbarShipPlacementNotPossible = function(){
                $('#toastShipPlacement').snackbar("show");
            };
            
            let showSnackbarNotAllShipsArePlaced = function (){
                $('#toastShipNonPlacement').snackbar("show");
            };

            let showSnackbarShipRotationNotPossible = function () {
                $('#toastShipRotation').snackbar("show");
            };
        </script>
    </body>
</html>
