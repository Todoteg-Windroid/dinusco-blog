<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <title>
    Webleb
  </title>
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <link rel="stylesheet" type="text/css" href="login.css" />
</head>
<body style="display: flex; justify-content:center ; align-items: center;height: 100vh;overflow: hidden;">
  <div class="form-wrap">
		<div class="tabs">
			<h3 class="signup-tab"><a href="#signup-tab-content" style="border-top-left-radius: 7px;" data-url="/signup"><spring:message code="submitSignUp"/></a></h3>
			<h3 class="login-tab"><a href="#login-tab-content" style="border-top-right-radius: 7px;" data-url="/login"><spring:message code="submitLogIn"/></a></h3>
		</div>
		<div class="tabs-content">
			<div id="signup-tab-content">
				<form:form modelAttribute="user">
					<spring:message code="emailAddressPlaceholder" var="emailAddressPlaceholder" />
					<form:input path="email" type="email" Class="input" placeholder="${emailAddressPlaceholder}" required="true"/>
					<spring:message code="usernamePlaceholder" var="usernamePlaceholder" />
					<form:input path="username" type="fullname" Class="input" placeholder="${usernamePlaceholder}" required="true" autocomplete="off" />
					<spring:message code="passwordPlaceholder" var="passwordPlaceholder" />
					<form:password path="password" Class="input" placeholder="${passwordPlaceholder}" required="true"/>

					<button type="submit" class="button"><spring:message code="submitSignUp" /></button>
				</form:form>
				<div class="help">
					<p><spring:message code="warningTerms"/></p>
					<p><a href="https://www.web-leb.com/"><spring:message code="termsOfServiceLink"/></a></p>
				</div>
			</div>
			<div id="login-tab-content" >
				<c:if test="${param.error != null}">
					<div>
						<spring:message code="logInError" />
					</div>
				</c:if>
				<c:if test="${param.logout != null}">
					<div>
						<spring:message code="logoutSuccess" />
					</div>
				</c:if>
				<form class="login-form" action="/login" method="post">
					<sec:csrfInput />
					<spring:message code="submitLogIn" var="submitLogIn" />
					<input type="text" name="username"class="input" id="user_login" autocomplete="off" placeholder="${usernamePlaceholder}">
					<input type="password" name="password" class="input" id="user_pass" autocomplete="off" placeholder="${passwordPlaceholder}">
					<input type="submit" class="button" value="${submitLogIn}">
				</form>
				<div class="help">
					<p><a href="https://www.web-leb.com/"><spring:message code="forgetPassword"/></a></p>
				</div>
			</div>
		</div>
	</div>
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  <script src="login.js"></script>
</body>
</html>
