<%@ tag body-content="empty" trimDirectiveWhitespaces="true"  pageEncoding="utf-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="navbar navbar-expand navbar-dark" style="background-color:#b51682">
    <div class="container">
        <a class="navbar-brand" href="<c:url value="/" />">Battleship</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbar" aria-controls="navbar" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbar">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" style="color:white" href="<c:url value="/rules" />">Spielregeln</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" style="color:white" href="<c:url value="/credits" />">Credits</a>
                </li>
               
            </ul>
        </div>
    </div>
</nav>
<br />