<%-- 
    Document   : rules
    Created on : 30.07.2018, 14:52:31
    Author     : Schulungsnb
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<!DOCTYPE html>
<html>
    <head>
        <template:header title="Spielregeln" />
    </head>


    <body>
        <template:navigation />    
        <ins><h1 style = "color: #48c9b0; text-align: center;"> <b> Spielregeln </b> </h1> </ins>

        <br>
        <b> <h2 style = "color: #e74c3c;  text-align: center;"><b> Spielzeit: 15 bis 25 Minuten</b></h2> </b>
        <br>
        <h1 style = " text-align: center;"> Die Schiffen dürfen: </h1>
        <h3 style = " text-align: center;">   1. nicht aneinander stoßen </h3>
        <h4 style = " text-align: center;"> 2. nicht über Eck gebaut sein oder Ausbuchtungen besitzen </h4>
        <h4 style = " text-align: center;">  3. nicht diagonal aufgestellt werden </h4>
        <h4 style = " text-align: center;">  4. auch am Rand liegen</h4>
        
        <br>
        <h1 style = " text-align: center;"> Spielverlauf: </h1>
        <br>
        <h3 style = " text-align: center;"> - Der Eingeladene beginnt das Spiel.</h3>
         <h3 style = " text-align: center;"> - Der Schießende gibt eine Koordinate an, auf die er feuert, zum Beispiel C3</h3>
        <h3 style = " text-align: center;"> - Der Beschossene sieht auf seinem Spielfeld, ob der Schluss ein Treffer ist, ein Schiff versenkt hat oder ins Wasser ging.</h3>
         <h3 style = " text-align: center;"> - Ein Schiff gilt als versenkt, wenn alle Felder des Schiffes getroffen wurden</h3>
         <h3 style = " text-align: center;">- Jeder Spieler hat ein Schuss pro Runde</h3>
         <br>
         <h3 style = "color: red; text-align: center;"> <b> Wer zuerst alle Schiffe des Gegners versenkt hat, ist der Sieger </b></h3>
        
         <br>
        <template:footer />
        <template:javascript/>

    </body>
</html>
