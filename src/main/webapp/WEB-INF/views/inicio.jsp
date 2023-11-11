<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
      <a href="#">Proyectos</a>
      <a href="#">Sobre nosotros</a>
      <a href="#">Contacto</a>
      <!-- Verificar si el usuario ha iniciado sesión -->
    <sec:authorize access="isAuthenticated()">
      <a href="/logout" style="float:right">Log out</a>
      <a href="/dashboard/create" style="float:right">Create Article</a>
    </sec:authorize>
    <sec:authorize access="!isAuthenticated()">
      <a href="/login" style="float:right">Registro/Login</a>
    </sec:authorize>
    </div>
    
    <div class="row">
      <div id="articlesContainer" class="leftcolumn"></div>
        </div>
      </div>
    </div>
    
    <div class="footer">
      <h2>Footer</h2>
    </div>
    <script>
      fetch("http://localhost/articles")
          .then(response => response.json())
          .then(data => {
              const articlesContainer = document.getElementById('articlesContainer');
              
              data.forEach(json => {
                  const card = document.createElement('div');
                  card.className = 'card';
                  card.innerHTML = `
                      <h2>`+json.title+`</h2>
                      <h5>Autor: ` + json.authorUsername + `</h5>
                      <h5>Fecha de Publicación: ` + json.createDate+`</h5>
                      <div class="fakeimg" style="height:200px;">Image</div>
                      <div>`+json.summary+`</div>
                      <a href="/dashboard/edit/`+json.id+`" style="float:right">Edit</a>
                      <a href="`+json.slug+`" style="float:right">read more</a>
                  `;
                  
                  articlesContainer.appendChild(card);
              });
          })
          .catch(error => console.error('Error:', error));
  </script>
    </body>
</html>