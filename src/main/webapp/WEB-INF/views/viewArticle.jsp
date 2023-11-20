<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="es">
    <body>
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