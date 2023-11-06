<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Please Log In</title>
    </head>
    <body>
        <h1>Please Log In</h1>
        
        <form action="login" method="post">
            <csrf:token />
            <div>
                <input type="text" name="username" placeholder="Username" />
            </div>
            <div>
                <input type="password" name="password" placeholder="Password" />
            </div>
            <input type="submit" value="Log in" />
        </form>
    </body>
</html>
