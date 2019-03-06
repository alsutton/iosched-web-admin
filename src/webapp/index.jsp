<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><style>.form-signin input { margin-top: 10px; margin-bottom: 10px; }</style></head>
<body>
<div class="row">&nbsp;</div>
<c:if test="${not empty sessionScope.error}">
    <div class="row"><div class="alert alert-danger text-center">${sessionScope.error}</div></div>
    <c:set scope="session" var="error" value="" />
</c:if>

<div class="row">
    <div class="col-md-5 text-center">
        <img src="<c:url value='/logo.png' />"/>
    </div>
    <div class="col-md-1">&nbsp;</div>
    <div class="col-md-6">
        <div class="row">
            <div class="col-md-8 col-md-offset-2">
                <form class="form-signin" name="logindetails" action="<c:url value='/Login'/>" method="POST" accept-charset="UTF-8">
                    <legend>Login</legend>
                    <input type="text" id="username" name="username" class="form-control" placeholder="Username"/>
                    <input type="password" id="password" name="password" class="form-control" placeholder="Password"/>
                    <button type="submit" class="btn btn-lg btn-primary btn-block">Login</button>
                </form>
            </div>
        </div>

        <div class="spacer">&nbsp;</div>
        <div class="spacer">&nbsp;</div>
        <div class="spacer">&nbsp;</div>

        <div class="row">
            <div class="col-md-12">
                <p>Conference Engineer is designed to help conference organisers. It allows users to plan conferences
                    by managing their tracks, speakers, sessions, and the download the data in a format suitable
                    for use in a <a href="https://github.com/FunkyAndroid/iosched">modified version of Googles IOsched application</a>.</p>

                <p>Conference Engineer is still in development, so if you have some suggestions please let us know via our
                    <a href="http://support.funkyandroid.com/">support portal</a>.</p>
            </div>
        </div>

        <div class="row">
            <div class="col-md-12 text-center">
                <h4>No login? <a href="<c:url value='/Register' />">Sign-up Here</a>.</h4>
            </div>
        </div>
    </div>
</div>

<div class="row">&nbsp;</div>
<div class="row">&nbsp;</div>
<div class="row">&nbsp;</div>
</body>
</html>