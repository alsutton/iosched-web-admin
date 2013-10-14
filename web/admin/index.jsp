<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <style>
        .form-signin { padding: 30px; margin: 0 auto; border: 1px solid #888; border-radius: 10px; }
        .form-signin input { margin-top: 10px; margin-bottom: 10px; }
    </style>
</head>
<body>
<div class="row">&nbsp;</div>
<c:if test="${not empty sessionScope.error}">
    <div class="row"><div class="alert alert-danger text-center">${sessionScope.error}</div></div>
    <c:set scope="session" var="error" value="" />
</c:if>

<div class="row">
    <div class="col-md-4 col-md-offset-4">
        <form class="form-signin" name="logindetails" action="<c:url value='/Login'/>" method="POST" accept-charset="UTF-8">
            <legend>Login</legend>
            <input type="text" id="username" name="username" class="form-control" placeholder="Username"/>
            <input type="password" id="password" name="password" class="form-control" placeholder="Password"/>
            <button type="submit" class="btn btn-lg btn-primary btn-block">Login</button>
        </form>
    </div>
</div>

<div class="row">
    <div class="col-md-4 col-md-offset-4 text-center">
        <h4>No login, but still want to play?<br/><a href="<c:url value='/Register' />">Sign-up Here</a>.</h4>
    </div>
</div>

<div class="row">&nbsp;</div>
<div class="row">&nbsp;</div>
<div class="row">&nbsp;</div>
<div class="row">
    <div class="col-md-12 text-center">
        <p>Initially developed to support</p>
        <a href="http://uk.droidcon.com/"><img src="<c:url value='/dcuk2013.png'/>" /></a>
    </div>
</div>
</body>
</html>