<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@ page import="com.fasterxml.jackson.databind.JsonNode" %>
        <!DOCTYPE html>
        <html>

        <head>
            <style>
                body {
                    font-family: Arial, sans-serif;
                }

                .container {
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
            <link href="https://cdn.quilljs.com/1.3.7/quill.snow.css" rel="stylesheet">
        </head>

        <body>
            <div class="container">
                <form:form modelAttribute="article" role="form" autocomplete="off" accept-charset="UTF-8"
                    id="editorForm">
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
                        <form:input path="title" class="form-control" autocomplete="off" placeholder="title"
                            value="${title}" />
                    </div>
                    <div class="form-group">
                        <form:input path="contentFull" id="contentFull" type="hidden" class="form-control"
                            autocomplete="off" />
                        <div id="editor" class="editor-content" style="height: 62vh;"></div>
                    </div>
                    <button type="submit" class="btn btn-default">
                        create
                    </button>
                </form:form>

            </div>
            <script src="https://cdn.quilljs.com/1.3.7/quill.js"></script>
            <script src="https://cdn.jsdelivr.net/npm/quill-image-resize-module@3.0.0/image-resize.min.js"></script>
            <script>
                const editor = document.getElementById("editor");
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

                // Define el formato personalizado "more"
                var Inline = Quill.import('blots/inline');
                class MoreBlot extends Inline {
                    static formats(domNode) {
                        return true;
                    }

                }
                MoreBlot.blotName = 'more';
                MoreBlot.tagName = 'span';
                Quill.register(MoreBlot);

                var quill = new Quill(editor, {
                    theme: 'snow',
                    modules: {
                        toolbar: [
                            ['bold', 'italic', 'underline', 'strike'],        // toggled buttons
                            ['blockquote', 'code-block'],

                            [{ 'header': 1 }, { 'header': 2 }],               // custom button values
                            [{ 'list': 'ordered' }, { 'list': 'bullet' }],
                            [{ 'script': 'sub' }, { 'script': 'super' }],      // superscript/subscript
                            [{ 'indent': '-1' }, { 'indent': '+1' }],          // outdent/indent
                            [{ 'direction': 'rtl' }],                         // text direction

                            [{ 'size': ['small', false, 'large', 'huge'] }],  // custom dropdown
                            [{ 'header': [1, 2, 3, 4, 5, 6, false] }],

                            [{ 'color': [] }, { 'background': [] }],          // dropdown with defaults from theme
                            [{ 'font': [] }],
                            [{ 'align': [] }],
                            ['link', 'image'],
                            ['clean'],                                         // remove formatting button
                            ['more'],

                        ],
                        imageResize: {
                            displaySize: true,
                        }
                    }
                });

                let toolbar = quill.getModule('toolbar');
                let btnMore = toolbar.controls[24][1];
                btnMore.innerHTML = "<i class='fas fa-icono'>More</i>";
                var insertedMore = false; // Variable para controlar si '[MORE]' se ha insertado

                // Agrega un controlador para el botón personalizado "more"
                toolbar.quill.getModule('toolbar').addHandler('more', function () {
                    var delta = quill.getContents();
                    var cursorPosition = quill.getSelection().index;

                    var index = delta.ops.findIndex(op => op.insert === "[MORE]");

                    console.log(index)

                    if (index >= 0) {
                        quill.setContents(delta.ops.filter(op => op.insert !== "[MORE]"))
                        quill.setSelection(quill.getLength());
                        //quill.setSelection(cursorPosition - "[MORE]".length)
                        // quill.updateContents({ ops: [ { retain: cursorPosition, },{ delete: "[MORE]".length }] })
                        //console.log(quill.updateContents({ ops: [{ retain: index, delete: "[MORE]".length }] }))
                        //quill.updateContents({ ops: [{ retain: ++index, delete: "[MORE]".length }] });
                        insertedMore = false; // Marca que '[MORE]' se ha eliminado
                    } else {
                        // Si '[MORE]' no está presente, insértalo como un marcador

                        //quill.setContents(delta.ops.concat({ insert: '[MORE]', attributes: { more: true } }));
                        //quill.updateContents(new Delta().retain(cursorPosition).insert('[MORE]')).concat(delta)
                        quill.updateContents({
                            ops: [{ retain: cursorPosition + 1 },
                            { insert: '[MORE]', attributes: { more: true } }
                            ]
                        })
                        // Insertar un salto de línea después de [MORE]
                        quill.insertText(quill.getLength(), '\n', 'user');

                        //quill.setSelection(++cursorPosition + "[MORE]".length)
                        quill.setSelection(quill.getLength());
                        insertedMore = true; // Marca que '[MORE]' se ha insertado
                    }


                    console.log(quill.getContents())

                });
                let imageList = [];
                quill.getModule('toolbar').addHandler('image', function () {
                    var input = document.createElement('input');
                    input.setAttribute('type', 'file');
                    input.setAttribute('accept', 'image/*');
                    input.click();

                    input.onchange = function () {
                        var file = input.files[0];
                        var formData = new FormData();
                        formData.append('image', file);

                        fetch('/upload', {
                            method: 'POST',
                            body: formData,
                        })
                            .then(response => response.json())
                            .then(data => {
                                var cursorPosition = quill.getSelection().index;
                                quill.insertEmbed(cursorPosition, 'image', data.imageUrl);
                                imageList.push(data.imageUrl);
                            })
                            .catch(error => {
                                console.error('Error al cargar la imagen:', error);
                            });
                    };
                });
                const form = document.getElementById("editorForm");
                const inputContent = document.getElementById("contentFull");

                // Asigna el contenido inicial a Quill si existe
                var content = "${content}"; // Obtén el contenido del modelo
                if (content) {
                    try {
                        quill.setContents(JSON.parse(content));
                    } catch (error) {
                        console.error("Error parsing JSON:", error);
                    }
                }

                form.addEventListener("submit", (event) => {
                    // Evita que el formulario se envíe inmediatamente (puedes ajustar según sea necesario)
                    event.preventDefault();

                    // Obtén el contenido de Quill como un objeto Delta
                    var contentQuill = quill.getContents();

                    // Convierte el objeto Delta a una cadena JSON
                    var contentJSON = JSON.stringify(contentQuill);
                    // Asigna la cadena JSON al campo oculto
                    inputContent.value = contentJSON;

                    // Envía el formulario manualmente si es necesario
                    form.submit();
                });

            </script>
        </body>

        </html>