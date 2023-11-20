<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Admin</title>
  <link rel="stylesheet" href="/admin.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css">
</head>
<body>
  <%@ include file="admin-header.jspf" %>
  <section class="page-content">
    <jsp:include page="${view}" />
    <footer class="page-footer">
      <span>made by </span>
      <a href="https://georgemartsoukos.com/" target="_blank">
        <img width="24" height="24" src="https://assets.codepen.io/162656/george-martsoukos-small-logo.svg" alt="George Martsoukos logo">
      </a>
    </footer>
  </section>
  <spring:message code="menu.lightMode" var="lightMode" />
  <spring:message code="menu.darkMode" var="darkMode" />
  <script src="/admin.js"></script>
</body>
</html>