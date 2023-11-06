<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
			<h3 class="signup-tab"><a href="#signup-tab-content" style="border-top-left-radius: 7px;" data-url="/signup">Registro</a></h3>
			<h3 class="login-tab"><a href="#login-tab-content" style="border-top-right-radius: 7px;" data-url="/login">Login</a></h3>
		</div>
		<div class="tabs-content">
			<div id="signup-tab-content">
				<form:form modelAttribute="user">
					<form:input path="email" type="email" Class="input" placeholder="Email" required="true"/>
					<form:input path="username" type="fullname" Class="input" placeholder="Username" required="true" autocomplete="off" />
					<form:password path="password" Class="input" placeholder="Password" required="true"/>

					<button type="submit" class="button">Sign Up</button>
				</form:form>
				<div class="help">
					<p>By signing up, you agree to our</p>
					<p><a href="https://www.web-leb.com/">Terms of service</a></p>
				</div>
			</div>
			<div id="login-tab-content" >
				<c:if test="${param.error != null}">
					<div>
						Invalid username and password.
					</div>
				</c:if>
				<c:if test="${param.logout != null}">
					<div>
						You have been logged out.
					</div>
				</c:if>
				<form class="login-form" action="/login" method="post">
					<sec:csrfInput />
					<input type="text" name="username"class="input" id="user_login" autocomplete="off" placeholder="Email or Username">
					<input type="password" name="password" class="input" id="user_pass" autocomplete="off" placeholder="Password">
					<input type="submit" class="button" value="Login">
				</form>
				<div class="help">
					<p><a href="https://www.web-leb.com/">Forget your password?</a></p>
				</div>
			</div>
		</div>
	</div>
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  <script src="login.js"></script>
</body>
</html>
