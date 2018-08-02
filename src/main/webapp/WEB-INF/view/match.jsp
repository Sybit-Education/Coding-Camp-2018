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
                share link ...
            </button>
            <button id="checkShips" class="btn btn-default" onclick="Battleship.allShipsOnStage();">Weiter</button>
        </div>
        <div class="col">
            <div id="current-field" style="margin-top: 2%; font-weight: bold"></div>
        </div>
    </div>
    <div class="row">
        <div>
            <canvas id="canvas" width="800" height="480"></canvas>
        </div>
    </div>
</main>

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
                <textarea rows="2" cols="55" id="Url"></textarea>
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

        <button id="toastShipPlacement" type="button" class="btn btn-secondary" data-toggle="snackbar" data-style="toast" data-timeout="3000" data-content="Schiff kann nicht auf diese Position gesetzt werden">
            Toast
        </button>

        <button id="toastShipNonPlacement" type="button" class="btn btn-secondary" data-toggle="snackbar" data-style="toast" data-timeout="3000" data-content="Alle Schiffe auf das Spielfeld setzen">
            Toast
        </button>

        <button id="toastShipRotation" type="button" class="btn btn-secondary" data-toggle="snackbar" data-style="toast" data-timeout="3000" data-content="Schiff kann nicht gedreht werden">
            Toast
        </button>

        <template:footer />
        <template:javascript/>

<script>
    let url;
    url = window.location.href;
    document.getElementById("Url").value = url;
</script>
     
<script>
    function copyUrl(){
    let copyUrl = document.getElementById("Url");
    copyUrl.select();
    document.execCommand("copy");
    }
</script>

<script>
    window.onload = function () {
        window.localStorage.clear();
        Battleship.init();
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
