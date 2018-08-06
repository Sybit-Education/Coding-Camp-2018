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
 
                <h1 style = "color: #e74c3c; text-align: center;"> <b> Spiel beendet! </b> </h1>
        
                <br>
                <h2 style = " text-align: center;">${winner} hat gewonnen </h2>
                <br>
                <h3 style = " text-align: center;">${looser} hat verloren </h3>
                <br>
                <h4 style = " text-align: center;"> Schüsse insgesammt: ${shotsCount}</h4>
                <br>
                        
               
  
        </div>

        <template:footer/>
        <template:javascript/>
    
</html>
