<%-- 
    Document   : formSpot
    Created on : 12 mars 2020, 08:45:35
    Author     : nicolasdotnet
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../common/header.jsp" %>

<ol class="breadcrumb b">
    <li><a href="/">Acceuil</a></li>
    <li class="active">Les livres</li>
</ol>

<c:if test="${!empty msg}"><span class="msg">${msg}</span></c:if>
<c:if test="${!empty error}"><span class="error">${error}</span></c:if>

    <div class="row container">
        <div class="row vcenter">
        <%@ include file="multisearch.jsp" %> 
    </div>
</div>









<table class="table table-striped" style="margin-top: 10px">
    <tr>
        <th>Isbn</th>
        <th>Auteur</th>
        <th>Titre</th>
        <th class="hidden-xs">Catégorie</th>
    </tr>

    <c:forEach items="${books}" var="s">
        
        <spring:url value="/book/${s.bookId}" var="bookUrl" />

        <tr>
            <td><a href="${bookUrl}"><c:out value="${s.isbn}">Valeur par défaut</c:out></a> </td>
            <td><c:out value="${s.author}">Valeur par défaut</c:out> </td>
            <td><c:out value="${s.title}">Valeur par défaut</c:out> </td>
            <td><c:out value="${s.bookCategory.label}">Valeur par défaut</c:out> </td>
            </tr>
    </c:forEach>
</table>

<div>

    <ul class="nav nav-pills">
        <c:forEach items="${pages}" var="pa" varStatus="status">
            <spring:url value="/spots?page=${status.index}&size=${size}" var="pageUrl" />
            <li>
                <a href="${pageUrl}">
                    <c:choose>
                        <c:when test="${status.first && status.last}">                              
                        </c:when>
                        <c:otherwise> 
                            <c:out value="${status.count}"></c:out></a> 
                        </c:otherwise>
                    </c:choose>
            </li>
        </c:forEach>
    </ul>
</div>

<%@ include file="../common/footer.jsp" %>
