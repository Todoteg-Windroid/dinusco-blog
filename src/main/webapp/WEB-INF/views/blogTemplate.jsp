<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Semillero de Investigación Alan Turing</title>
    <link rel="stylesheet" type="text/css" href="/inicio.css">
    <c:if test="${view == 'viewArticle.jsp'}">
        <link rel="stylesheet" type="text/css" href="/viewArticle.css">
        <link href="https://cdn.quilljs.com/1.3.7/quill.bubble.css" rel="stylesheet" />
    </c:if>
</head>
    <body>
  
    <div class="header">
      <h1><spring:message code="blogTitle" /></h1>
      <p><spring:message code="blogDescription" /></p>
    </div>
    
    <div class="topnav">
      <a href="list"><spring:message code="navOptionOne"/></a>
      <a href="#"><spring:message code="navOptionTwo"/></a>
      <a href="#"><spring:message code="navOptionThree"/></a>
      <a href="#"><spring:message code="navOptionFour"/></a>
      <!-- Verificar si el usuario ha iniciado sesión -->
    <sec:authorize access="isAuthenticated()">
      <a href="/logout" style="float:right"><spring:message code="logout"/></a>
    </sec:authorize>
    <sec:authorize access="hasRole('ROLE_ADMIN') || hasRole('EDITOR')">
      <a href="/dashboard/create" style="float:right"><spring:message code="dashboard.create"/></a>
    </sec:authorize>
    <sec:authorize access="!isAuthenticated()">
      <a href="/login" style="float:right"><spring:message code="signupOrLoginOption"/></a>
    </sec:authorize>
    </div>
    
    <div class="row">
        <jsp:include page="${view}" />
    </div>
    
    <div class="footer">
      <h2>Footer</h2>
    </div>
    </body>
</html>