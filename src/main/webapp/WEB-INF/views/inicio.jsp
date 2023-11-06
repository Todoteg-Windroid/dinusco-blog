<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" type="text/css" href="/inicio.css">
</head>
    <body>
  
    <div class="header">
      <h1>Semillero de Investigación Alan Turing</h1>
      <p>Con las matemáticas y la robotica, se hace el futuro de las ciencias</p>
    </div>
    
    <div class="topnav">
      <a href="index.html">Inicio</a>
      <a href="proyecto.html">Proyectos</a>
      <a href="#">Sobre nosotros</a>
      <a href="#">Contacto</a>
      <!-- Verificar si el usuario ha iniciado sesión -->
    <sec:authorize access="isAuthenticated()">
      <a href="/logout" style="float:right">Log out</a>
      <a href="/createArticle" style="float:right">Create Article</a>
    </sec:authorize>
    <sec:authorize access="!isAuthenticated()">
      <a href="/login" style="float:right">Registro/Login</a>
    </sec:authorize>
    </div>
    
    <div class="row">
      <div class="leftcolumn">
        <c:forEach items="${articles}" var="article">
          <div class="card">
            <h2>${article.title}</h2>
            <h5>Autor: ${article.authorUsername}</h5>
            <h5>Fecha de Publicación: ${article.createDate}</h5>
            <div class="fakeimg" style="height:200px;">Image</div>
            <div>
              ${article.summary}
            </div>

            <a href="articles/${article.slug}" style="float:right">read more</a>
          </div>
        </c:forEach>
      </div>
        </div>
      </div>
    </div>
    
    <div class="footer">
      <h2>Footer</h2>
    </div>
    
    </body>
</html>