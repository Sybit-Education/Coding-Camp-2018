<%--

--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<html>
    <head>
        <template:header title="Battleships"/>
    </head>
    
        <template:navigation/>

        <div class="container">
        
            <p>Winner ${winner}</p>
 
                <h1 style = "color: #e74c3c; text-align: center;"> <b> Spiel beendet! </b> </h1>
        
                <br>
                <h2 style = " text-align: center;"> Spieler... hat gewonnen </h2>
                <br>
                <h3 style = " text-align: center;">   Spieler... hat verloren </h3>
                <br>
                <h4 style = " text-align: center;"> Spielzeit: </h4>
                <br>
                   

                <table class="table">

                <tr>
                  <th scope="col"></th>
                  <th scope="col">getroffene Schiffe</th>
                  <th scope="col">Schüsse insgesamt</th>
                  <th scope="col">davon Treffer</th>
                </tr>


                <tr>
                  <th scope="row">Spieler 1</th>
                  <td></td>
                  <td></td>
                  <td></td>
                </tr>
                <tr>
                  <th scope="row">Spieler 2</th>
                  <td></td>
                  <td></td>
                  <td></td>
                </tr>


            </table>
                        
               
  
        </div>

        <template:footer/>
        <template:javascript/>
    
</html>
