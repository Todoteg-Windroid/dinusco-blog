<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
            <!DOCTYPE html>
            <html lang="es">

            <head>
                <meta charset="UTF-8" />
                <meta name="viewport" content="width=device-width, initial-scale=1.0" />
                <title>Document</title>
                <link rel="stylesheet" type="text/css" href="/inicio.css" />

                <style>
                    /* Estilos generales */
                    body {
                        font-family: Arial, sans-serif;
                        background-color: #f0f0f0;
                        margin: 0;
                        padding: 0;
                    }

                    header {
                        background-color: #000000;
                        color: #fff;
                        text-align: center;
                        padding: 20px 0;
                    }

                    article {
                        background-color: #fff;
                        max-width: 80%;
                        margin: 20px auto;
                        padding: 20px;
                        min-height: 20vh;
                        border: 1px solid #ccc;
                        border-radius: 5px;
                        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                        position: relative;
                    }

                    footer {
                        text-align: center;
                        padding: 10px 0;
                        background-color: #3f51b5;
                        color: #fff;
                        bottom: 0;
                    }

                    /* Estilos del aviso */
                    .access-notice {
                        background-color: #000000cc;
                        color: #fff;
                        /* Cambiar el color del texto según tus preferencias */
                        text-align: center;
                        padding: 10px;
                        border-radius: 5px;
                        position: absolute;
                        bottom: -25px;
                        /* Ajustar la distancia desde la parte inferior del artículo */
                        left: 50%;
                        transform: translateX(-50%);
                        /* Centrar horizontalmente */
                    }

                    .access-notice p {
                        font-size: 16px;
                        margin-bottom: 5px;
                    }

                    .access-notice a {
                        color: #fff;
                        text-decoration: underline;
                        margin: 0 10px;
                    }

                    /* Estilos adicionales según sea necesario */
                </style>
                <link href="https://cdn.quilljs.com/1.3.7/quill.bubble.css" rel="stylesheet" />
            </head>

            <body>
                <div class="header">
                    <h1>Semillero de Investigación Alan Turing</h1>
                    <p>
                        Con las matemáticas y la robotica, se hace el futuro de las ciencias
                    </p>
                </div>

                <div class="topnav">
                    <a href="/articles/list">Inicio</a>
                    <a href="#">Proyectos</a>
                    <a href="#">Sobre nosotros</a>
                    <a href="#">Contacto</a>
                    <!-- Verificar si el usuario ha iniciado sesión -->
                    <sec:authorize access="isAuthenticated()">
                        <a href="/logout" style="float: right">Log out</a>
                        <a href="/createArticle" style="float: right">Create Article</a>
                    </sec:authorize>
                    <sec:authorize access="!isAuthenticated()">
                        <a href="/login" style="float: right">Registro/Login</a>
                    </sec:authorize>
                </div>

                <header>
                    <h1>${title}</h1>
                    <p>Autor: ${author}</p>
                    <p>Fecha de Publicación: ${createDate}</p>
                </header>

                <article>
                    <div class="article-content" id="article-content"></div>
                    <sec:authorize access="!isAuthenticated()">
                        <div class="access-overlay">
                            <div class="access-notice">
                                <p>
                                    Para acceder al artículo completo, debes registrarte o iniciar
                                    sesión.
                                </p>
                                <a href="/login">Registro/Login</a>
                            </div>
                        </div>
                    </sec:authorize>
                </article>

                <footer>
                    <p>© 2023 Nombre de tu Sitio Web</p>
                </footer>
                <script src="https://cdn.quilljs.com/1.3.7/quill.js"></script>
                <script>


                    const Image = Quill.import('formats/image');
                    const ImageFormatAttributesList = [
                        'alt',
                        'height',
                        'width',
                        'style'
                    ];

                    class StyledImage extends Image {
                        static formats(domNode) {
                            return ImageFormatAttributesList.reduce(function (formats, attribute) {
                                if (domNode.hasAttribute(attribute)) {
                                    formats[attribute] = domNode.getAttribute(attribute);
                                }
                                return formats;
                            }, {});
                        }
                        format(name, value) {
                            if (ImageFormatAttributesList.indexOf(name) > -1) {
                                if (value) {
                                    this.domNode.setAttribute(name, value);
                                } else {
                                    this.domNode.removeAttribute(name);
                                }
                            } else {
                                super.format(name, value);
                            }
                        }
                    }

                    Quill.register(StyledImage, true);
                    var quill = new Quill("#article-content", {
                        theme: "bubble",
                        readOnly: true,
                    });
                    //var isAuthenticated = '<sec:authorize access="isAuthenticated()">true</sec:authorize>';
                    let content = `${content}`;
                    console.log(content);
                    console.log(JSON.parse(content));
                    quill.setContents(JSON.parse(content));
                </script>
            </body>

            </html>