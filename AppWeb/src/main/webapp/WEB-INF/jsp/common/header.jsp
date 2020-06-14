<%-- 
    Document   : header
    Created on : 9 mars 2020, 09:04:04
    Author     : nicolasdotnet
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<!-- Le doctype permet de savoir quelle version de HTML est utilisée sur la page -->
<!DOCTYPE html>
<html lang="fr">
    <head>
        <!-- Permet de spécifier l'encodage de caractères de la page -->
        <meta charset="UTF-8">
        <title>Bibliothéque de big city</title>
        <!-- Chargement de FontAwesome-->
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
        <!-- chargement d'une GoogleFont -->
        <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- chargement de bootstrap css -->
        <link rel='stylesheet' type='text/css' href='/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css' />
        <!-- chargement de la feuille de style personnalisée-->
        <link rel='stylesheet' type='text/css' href='/styles.css' />
    </head>


    <!-- Corps de la page (ce qui s'affiche dans la fenêtre du navigateur)-->
    <body>
        <header>
            <!-- menu de navigation -->

            <nav class="navbar navbar-default" role="navigation">
                <div class="container">
                    <div class="navbar-header">
                        <a class="navbar-brand" style="font-family: Montserrat" href="/">Biblio</br>de Big City</a>
                        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                            <span class="sr-only">Toggle navigation</span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                    </div>

                    <!-- Collect the nav links, forms, and other content for toggling -->
                    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                        <ul class="nav navbar-nav">
                            <li class="nav-item"><a href="/book/all">Livres</a></li>
                            
                                <li class="nav-item"><a href="/user/bookings">Mes prêts</a></li>                            

                        </ul>
                        <ul class="nav navbar-nav navbar-right">

                                <li class="nav-item"><a href="/logout">Logout</a></li>
                                <li class="nav-item username"><a href="/user/account">
                                <li class="nav-item"><a href="/login">Se connecter</a></li>
                        </ul>
                    </div>
            </nav>
        </header>
        <main>
            <div class="container">
                
