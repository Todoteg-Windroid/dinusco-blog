<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
    <body>
      <div id="articlesContainer" class="leftcolumn"></div>

    </body>
      <script>
        fetch("http://localhost/articles")
            .then(response => response.json())
            .then(data => {
                const articlesContainer = document.getElementById('articlesContainer');
                
                data.forEach(json => {
                    const urlImage = json.urlFirstImage?'background-image:url('+json.urlFirstImage+');background-size: cover;background-position: center;':'';
                    const card = document.createElement('div');
                    card.className = 'card';
                    card.innerHTML = `
                        <h2>`+json.title+`</h2>
                        <h5>Autor: ` + json.authorUsername + `</h5>
                        <h5>Fecha de Publicación: ` + json.createDate+`</h5>
                        <div class="fakeimg" style="height:200px;`+urlImage+`"></div>
                        <div>`+json.summary+`</div>
                        <a href="`+json.slug+`" style="float:right">read more</a>
                    `;
                    
                    articlesContainer.appendChild(card);
                });
            })
            .catch(error => console.error('Error:', error));
      </script>
</html>