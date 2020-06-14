<%-- 
    Document   : confirmation
    Created on : 23 avr. 2020, 13:01:27
    Author     : nicolasdotnet
--%>

<%@ include file="../common/header.jsp" %>

<c:choose>
    <c:when test="${!empty create}">
        <spring:url value="/user/${user.userId}" var="userUrl" />
        <p>${create}</p>
        <p>Vous pouvez vous connecter à votre compte. Cliquez <a href="${userUrl}">ici</a></p>
    </c:when>
    <c:when test="${!empty delete}">
        <spring:url value="/user/${user.userId}" var="userUrl" />
        <p>${delete}</p>
        <p>Vous ne pouvez plus vous connecter à votre compte.</p>
        <p>Pour créer un nouveau compte, cliquez <a href="/signup">ici</a></p>
    </c:when>
    <c:when test="${!empty error}">
        <p>${error}</p>
    </c:when>
        <c:when test="${!empty msg}">
        <p>${msg}</p>
    </c:when>
</c:choose>

<%@ include file="../common/footer.jsp" %>