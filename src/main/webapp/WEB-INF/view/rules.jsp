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
        <main class="container">
         <h1 style = "color:#212529; text-align: center; letter-spacing: 4px; margin-top: 20px"> <b> Spielregeln </b> </h1>

           
           <hr style="border: #b51682 3px solid; border-radius: 10px; margin-top: 50px;">
           <h2 style = " text-align: center; margin-bottom: 20px"> Die Schiffe dürfen </h2>
           
      
           <ul class="list-unstyled" style="margin-left:10%; font-size: 1.5rem; max-width: 100%; -moz-column-count: 2;
                -moz-column-gap: 10px;
                -webkit-column-count: 2;
                -webkit-column-gap: 10px;
                column-count: 2;
                column-gap: 20px;">
               <li>• nicht aneinander stoßen</li>
               <li>• nicht über Eck gebaut sein oder Ausbuchtungen besitzen</li>
               <li>• nicht diagonal aufgestellt werden</li>
               <li>• auch am Rand liegen</li>
            </ul>
            
            <hr style="border: #b51682 3px solid; border-radius: 10px ">
           <h2 style = " text-align: center; margin-bottom: 20px"> Spielverlauf </h2>
           
           <ul class="list-unstyled" style="margin-left:10%; font-size: 1.5rem; max-width: 100%;">
               <li>• Der Schießende gibt eine Koordinate an, auf die er feuert</li>
               <li>• Der Beschossene sieht auf seinem Spielfeld, ob der Schuss ein Treffer war, ein Schiff versenkt wurde oder ins Wasser ging</li>
               <li>• Ein Schiff gilt als versenkt, wenn alle Felder des Schiffes getroffen wurden</li>
               <li>• Jeder Spieler hat ein Schuss pro Runde</li>
           </ul> 
           
            <hr style="border: #b51682 3px solid; border-radius: 10px ">
            <h2 style = " text-align: center; margin-bottom: 20px"> Schiffe </h2>
            
            <ul class="list-unstyled" style="margin-left:10%; font-size: 1.5rem; max-width:100%; color:#111; -moz-column-count: 2;
                -moz-column-gap: 10px;
                -webkit-column-count: 2;
                -webkit-column-gap: 10px;
                column-count: 2;
                column-gap: 20px">
                <li>• ein Schlachtschiff (5 Kästchen)</li>
                <li>• zwei Kreuzer (je 4 Kästchen)</li>
                <li>• drei Zerstörer (je 3 Kästchen)</li>
                <li>• vier U-Boote (je 2 Kästchen)</li>
            </ul>
            <br>

            <hr style="border: #b51682 3px solid; border-radius: 10px ">
            <h2 style = " text-align: center; margin-bottom: 20px"> So wird gespielt </h2>
            
            <ul class="list-unstyled" style="margin-left:10%; font-size: 1.5rem; max-width:100%; color:#111; -moz-column-count: 2;
                -moz-column-gap: 10px;
                -webkit-column-count: 2;
                -webkit-column-gap: 10px;
                column-count: 2;
                column-gap: 20px">
                <li>• auf "Neues Spiel..." klicken</li>
                <li>• Namen eingeben</li>
                <li>• Sie können einen weiteren Spieler einladen indem sie auf Share link klicken und daraufhin den Link and die Person senden mit der Sie spielen möchten</li>
                <li>• Platzieren sie die Schiffe indem sie sie mit der Maus auf die gewünschte Position auf dem Feld ziehen. Sie können sie auch drehen indem sie auf ein Schiff doppelklicken </li>
                <li>• Klicken sie auf weiter um auf das Spielfeld zu kommen, hier müssen sie ggf. auf den anderen Spieler warten</li>
                <li>• Sobald das Spiel beginnt bekommen sie 60 sekunden pro Schuß sobald Sie schießen ended ihre Runde</li>
                <li>• Um zu gewinnen müssen Sie jedes gegnerische Schiff versenken</li>
            </ul>
            <br>
      
        
        </main>
        <template:footer />
        <template:javascript/>

    </body>
</html>
