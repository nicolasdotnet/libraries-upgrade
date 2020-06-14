<%-- 
    Document   : show
    Created on : 14 mars 2020, 09:10:46
    Author     : nicolasdotnet
--%>
<%@ include file="../common/header.jsp" %>

<ol class="breadcrumb">
    <li><a href="/">Acceuil</a></li>
    <li><a href="/book/all">Les livres</a></li>
</ol>


<h2>${bookFind.title}</h2>
<c:if test="${!empty msg}"><span class="msg">${msg}</span></c:if>
<c:if test="${!empty error}"><span class="error">${error}</span></c:if>

    <div class="row container">
        <div class="row vcenter">
            <div  class="col-sm-8 hidden-xs"><h3>Informations</h3></div>

            <div class="col-sm-4 hidden-xs">
            </div>
        </div>
    </div>

    <div class="panel panel-default t">
        <div class="panel-body">
            <div>
                <label>Isbn :</label>
                <span><c:out value="${bookFind.isbn}">Valeur par défaut</c:out></span>
            </div>
            <div>
                <label>Auteur :</label>
                <span><c:out value="${bookFind.author}">Valeur par défaut</c:out></span>
            </div>
            <div>
                <label>Titre : </label>
                <span><c:out value="${bookFind.title}">Valeur par défaut</c:out></span>
            </div>
            <div>
                <label>Nombre de copie disponible : </label>
                <span><c:out value="${bookFind.copiesAvailable}">Valeur par défaut</c:out></span>
            </div>
            <div>
                <label>Catégorie : </label>
                <span><c:out value="${bookFind.bookCategory.label}">Valeur par défaut</c:out></span>
            </div>

        </div>
    </div>

<%@ include file="../common/footer.jsp" %>
