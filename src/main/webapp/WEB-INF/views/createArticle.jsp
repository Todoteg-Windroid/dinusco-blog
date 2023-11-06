<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

    <!DOCTYPE html>
    <html>

    <head>
        <title>Bienvenido a mi aplicación</title>
        <style>
            body {
                font-family: Arial, sans-serif;
            }

            .editor {
                width: 80%;
                max-width: 800px;
                margin: 20px auto;
                background-color: #fff;
                border: 1px solid #ccc;
                border-radius: 5px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                padding: 20px;
            }

            .toolbar {
                border-bottom: 1px solid #ccc;
                padding-bottom: 10px;
                margin-bottom: 10px;
            }

            .toolbar button {
                background-color: #fff;
                border: 1px solid #ccc;
                border-radius: 4px;
                padding: 4px 8px;
                margin-right: 5px;
                cursor: pointer;
            }

            .editor-content {
                border: 1px solid #ccc;
                border-radius: 4px;
                min-height: 300px;
                padding: 10px;
                font-size: 16px;
            }

            .form-group {
                margin-bottom: 20px;
            }

            label {
                font-weight: bold;
            }

            input[type="text"],
            textarea {
                box-sizing: border-box;
                -moz-box-sizing: border-box;
                color: inherit;
                font-family: inherit;
                padding: 0.8em 0 10px 0.8em;
                border: 1px solid #cfcfcf;
                outline: 0;
                display: inline-block;
                margin: 0 0 0.8em 0;
                padding-right: 2em;
                width: 100%;
                border-radius: 7px;
            }

            textarea {
                height: 400px;
                resize: vertical;
            }

            .btn-default {
                width: 100%;
                padding: 0.8em 0 10px 0.8em;
                background-color: #24243e;
                border: none;
                color: #fff;
                cursor: pointer;
                text-transform: uppercase;
                border-radius: 7px;
            }

            .btn-default:hover {
                background-color: #313151;
            }
        </style>
    </head>

    <body>
        <div class="editor">
            <form:form modelAttribute="article" role="form" autocomplete="off" accept-charset="UTF-8" id="editorForm">
                <!-- 
                <div class="form-group">
                    <form:label path="slug">
                        URL name
                    </form:label>
                    <form:input path="slug" class="form-control" placeholder="Slug" />
                </div>-->
                <div class="form-group">
                    <form:label path="title">
                        Post title
                    </form:label>
                    <form:input path="title" class="form-control" autocomplete="off" placeholder="title" />
                </div>
                <div class="form-group">
                    <form:input path="contentFull" id="contentFull" type="hidden" class="form-control"
                        autocomplete="off" />

                    <div class="toolbar">
                        <button type="button" id="boldButton"><b>B</b></button>
                        <button type="button" id="italicButton"><i>I</i></button>
                        <button type="button" id="underlineButton"><u>U</u></button>
                        <button type="button" id="listButton">List</button>
                        <button type="button" id="linkButton">Link</button>
                        <button type="button" id="imageButton">Imagen</button>
                        <button type="button" id="fontSizeButton">Tamaño de Fuente</button>
                        <button type="button" id="fontFamilyButton">Fuente</button>
                        <button type="button" id="indentButton">Tabular</button>
                        <select id="listTypeSelect">
                            <option value="unordered">Lista Desordenada</option>
                            <option value="ordered">Lista Ordenada</option>
                        </select>
                    </div>
                    <div id="editor" class="editor-content" contenteditable="true"></div>
                </div>

                <button type="submit" class="btn btn-default">
                    create
                </button>
            </form:form>
        </div>

        <script>
            function toggleFormat(command, value) {
                document.execCommand(command, false, value);
            }

            document.getElementById("boldButton").addEventListener("click", () => toggleFormat("bold"));
            document.getElementById("italicButton").addEventListener("click", () => toggleFormat("italic"));
            document.getElementById("underlineButton").addEventListener("click", () => toggleFormat("underline"));
            document.getElementById("listButton").addEventListener("click", () => {
                const listType = document.getElementById("listTypeSelect").value;
                toggleFormat(listType === "unordered" ? "insertUnorderedList" : "insertOrderedList");
            });
            document.getElementById("linkButton").addEventListener("click", () => {
                const url = prompt("Insertar URL:");
                if (url) {
                    toggleFormat("createLink", url);
                }
            });
            document.getElementById("imageButton").addEventListener("click", () => {
                const url = prompt("Insertar URL de la imagen:");
                if (url) {
                    toggleFormat("insertImage", url);
                }
            });

            document.getElementById("fontSizeButton").addEventListener("click", () => {
                const fontSize = prompt("Tamaño de Fuente (ejemplo: 16px):");
                if (fontSize) {
                    toggleFormat("fontSize", fontSize);
                }
            });

            document.getElementById("fontFamilyButton").addEventListener("click", () => {
                const fontFamily = prompt("Nombre de Fuente (ejemplo: Arial):");
                if (fontFamily) {
                    toggleFormat("fontName", fontFamily);
                }
            });

            document.getElementById("indentButton").addEventListener("click", () => {
                toggleFormat("indent");
            });


            const inputContent = document.getElementById("contentFull");
            const editor = document.getElementById("editor");
            const form = document.getElementById("editorForm");

            form.addEventListener("submit", (event) => {

                inputContent.value = editor.innerHTML;
            });



        </script>
    </body>

    </html>