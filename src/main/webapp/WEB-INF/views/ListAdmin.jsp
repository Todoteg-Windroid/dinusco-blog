<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <style>
                .actions {
                    display: grid;
                    grid-template-columns: repeat(3, 1fr);
                    grid-gap: 20px;
                    align-items: end;
                }
            </style>
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
        </head>

        <body>
            <section class="search-and-user">
                <form>
                    <input type="search" placeholder="Search Pages...">
                    <button type="submit" aria-label="submit form">
                        <svg aria-hidden="true">
                            <use xlink:href="#search"></use>
                        </svg>
                    </button>
                </form>
                <div class="admin-profile">
                    <span class="greeting">Add</span>
                    <div class="notifications">
                        <span class="badge">1</span>
                        <a href="dashboard/create">
                            <i class="fa-regular fa-square-plus fa-2xl"></i>
                        </a>
                    </div>
                </div>
            </section>
            <section id="articlesContainer" class="grid"></section>
            <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
            <script th:inline="javascript">
                fetch("http://localhost/articles")
                    .then(response => response.json())
                    .then(data => {

                        const articlesContainer = document.getElementById('articlesContainer');

                        data.forEach(json => {
                            const card = document.createElement('article');
                            card.className = 'card';
                            card.innerHTML = `
                    <div style="width: 100%">
                    <h2>`+ json.title + `</h2>
                    <div>`+ json.summary + `</div>
                    </div>
                    <div>
                    <h5>Autor: ` + json.authorUsername + `</h5>
                    <h5>Fecha de Publicación: ` + json.createDate + `</h5>
                    </div>
                    <div class="actions">
                        <a href="/articles/`+ json.slug + `" style="float:right" target="_blank"><i class="fa-solid fa-eye"></i></a>
                        <a href="/dashboard/delete/`+ json.id + `" style="float:right" ><i class="fa-solid fa-trash-can"></i></a>
                        <a href="/dashboard/edit/`+ json.id + `" style="float:right" ><i class="fa-solid fa-pen-to-square"></i></a>
                    </div>
                `;

                            articlesContainer.appendChild(card);
                        });
                    })
                    .catch(error => console.error('Error:', error));

                window.onload = function () {

                    var msg = "${message}";
                    console.log(msg);
                    if (msg == "Save Success") {
                        Command: toastr["success"]("Article added successfully!!")
                    } else if (msg == "Delete Success") {
                        Command: toastr["success"]("Article deleted successfully!!")
                    } else if (msg == "Delete Failure") {
                        Command: toastr["error"]("Some error occurred, couldn't delete Article")
                    } else if (msg == "Edit Success") {
                        Command: toastr["success"]("Article updated successfully!!")
                    }

                    toastr.options = {
                        "closeButton": true,
                        "debug": false,
                        "newestOnTop": false,
                        "progressBar": true,
                        "positionClass": "toast-top-right",
                        "preventDuplicates": false,
                        "showDuration": "300",
                        "hideDuration": "1000",
                        "timeOut": "5000",
                        "extendedTimeOut": "1000",
                        "showEasing": "swing",
                        "hideEasing": "linear",
                        "showMethod": "fadeIn",
                        "hideMethod": "fadeOut"
                    }
                }
            </script>
        </body>

        </html>