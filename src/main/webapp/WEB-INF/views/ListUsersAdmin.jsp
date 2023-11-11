<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        table {
            width: 100%;
            background: white;
            margin-bottom: 1.25em;
            border: solid 1px #dddddd;
            border-collapse: collapse;
            border-spacing: 0;
        }

        table tr th,
        table tr td {
            padding: 0.5625em 0.625em;
            font-size: 0.875em;
            color: #222222;
            border: 1px solid #dddddd;
        }

        table tr.even,
        table tr.alt,
        table tr:nth-of-type(even) {
            background: #f9f9f9;
        }

        @media only screen and (max-width: 768px) {

            table.resp,
            .resp thead,
            .resp tbody,
            .resp tr,
            .resp th,
            .resp td,
            .resp caption {
                display: block;
            }

            table.resp {
                border: none
            }

            .resp thead tr {
                display: none;
            }

            .resp tbody tr {
                margin: 1em 0;
                border: 1px solid #2ba6cb;
            }

            .resp td {
                border: none;
                border-bottom: 1px solid #dddddd;
                position: relative;
                padding-left: 45%;
                text-align: left;
            }

            .resp tr td:last-child {
                border-bottom: 1px double #dddddd;
            }

            .resp tr:last-child td:last-child {
                border: none;
            }

            .resp td:before {
                position: absolute;
                top: 6px;
                left: 6px;
                width: 45%;
                padding-right: 10px;
                white-space: nowrap;
                text-align: left;
                font-weight: bold;
            }

            td:nth-of-type(1):before {
                content: "Titulo";
            }

            td:nth-of-type(2):before {
                content: "A\00f1o";
            }

            td:nth-of-type(3):before {
                content: "Formato";
            }

            td:nth-of-type(4):before {
                content: "Autor";
            }

            td:nth-of-type(5):before {
                content: "Tama\00f1o";
            }

            td:nth-of-type(6):before {
                content: "Precio";
            }
        }
    </style>
</head>

<body>
    <table class="resp">
        <thead>
            <tr>
                <th scope="col">Id</th>
                <th scope="col">Username</th>
                <th scope="col">Email</th>
                <th scope="col">Password</th>
                <th scope="col">Role</th>
                <th scope="col">Enabled</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td>${user.id}</td>
                    <td>${user.username}</td>
                    <td>${user.email}</td>
                    <td>${user.password}</td>
                    <td>${user.role}</td>
                    <td>${user.enabled}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>

</html>