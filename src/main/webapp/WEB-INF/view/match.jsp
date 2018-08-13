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
    <div class="row ">
        <div class="col">
            <!-- Button trigger modal -->
            <button type="button" class="btn btn-primary" style="margin-top: 50px; color:#b51682" data-toggle="modal" data-target="#shareLinkModal">share link ...</button>
            <button id="checkShips" style="margin-top: 50px" class="btn btn-success" onclick="Battleship.allShipsOnStage();">Weiter</button>
        </div>
        <div class="col">
            <div id="current-field" style="margin-top: 60px; font-weight: bold; margin-left: -50px">A1</div>
        </div>
    </div>
    <div class="row">
        <div>
            <canvas id="canvas" width="800" height="480"  style="margin-top: 30px"></canvas>
        </div>
    </div>
</main>

<!-- ShareLinksModal -->
<div class="modal fade" id="shareLinkModal" tabindex="-1" role="dialog" aria-labelledby="schareLink" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel" style="color:#51682">Share Link</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <textarea rows="2" cols="55" id="Url" spellcheck="false" autocorrect="off" style="resize: none; width: 100%;"></textarea>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal" style="color:#b51682" onclick="copyUrl()">
                    Copy to clipboard ...
                </button>
            </div>
        </div>
    </div>
</div>

<!-- NameModal -->
<div class="modal fade" id="NameModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLongTitle">Name Eingeben</h5>
      </div>
      <form onSubmit="return getValue();" class="needs-validation">
        <div class="modal-body">
            <input type="text" rows="1" cols="53" id="NameEingebenTextArea" spellcheck="false" autocorrect="off" class="form-control" style="resize: none;" required/>
            <div class="invalid-feedback">
                Bitte einen Namen eingeben!
            </div>
        </div>
        <div class="modal-footer">
          <button type="submit" class="btn btn-primary" style="color:#b51682">Name Bestätigen</button>
        </div>
      </form>
    </div>
  </div>
</div>

        <button id="toastShipPlacement" type="button" class="btn btn-secondary" data-toggle="snackbar" data-style="toast" data-timeout="3000" data-content="Schiff kann nicht auf diese Position gesetzt werden" style="visibility: hidden;">
            Toast
        </button>

        <button id="toastShipNonPlacement" type="button" class="btn btn-secondary" data-toggle="snackbar" data-style="toast" data-timeout="3000" data-content="Alle Schiffe auf das Spielfeld setzen" style="visibility: hidden;">
            Toast
        </button>

        <button id="toastShipRotation" type="button" class="btn btn-secondary" data-toggle="snackbar" data-style="toast" data-timeout="3000" data-content="Schiff kann nicht gedreht werden" style="visibility: hidden;">
            Toast
        </button>

        <template:footer/>
        <template:javascript/>

<script>
    let url;
    url = window.location.href;
    document.getElementById("Url").value = url;
</script>
<script>
  function getValue(){
   Battleship.utilHandler.setCookie("userName", document.getElementById("NameEingebenTextArea").value);
   $('#NameModal').modal('hide');
   // no reload
   return false;
  }
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
        Battleship.utilHandler.removeCookie("giveUp");
        Battleship.utilHandler.removeCookie("showShips");
        window.localStorage.clear();
        Battleship.utilHandler.removeCookie("userId");
        Battleship.utilHandler.removeCookie("userName");
        setUrlInModal();
        Battleship.init();
        $("#NameModal").modal({backdrop: 'static', keyboard: false});
    };
    
    function setUrlInModal(){
        let url;
        url = window.location.href;
        document.getElementById("Url").value = url;
        localStorage.setItem("keyMatchLink",url);
    }
    
    function copyUrl(){
        let copyUrl = document.getElementById("Url");
        copyUrl.select();
        document.execCommand("copy");
    }

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
