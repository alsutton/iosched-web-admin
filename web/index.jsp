<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<body>
<div class="row">&nbsp;</div>
<c:if test="${not empty sessionScope.error}">
    <div class="row"><div class="alert alert-danger text-center">${sessionScope.error}</div></div>
    <c:set scope="session" var="error" value="" />
</c:if>

<div class="row">
    <div class="col-md-12 text-center"><img src="<c:url value='/logo.png' />"/></div>
</div>

<div class="row">
    <div class="col-md-12 text-center">
        <p>Conference Engineer is currently in development. More information will be available
        at a later date.</p>
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