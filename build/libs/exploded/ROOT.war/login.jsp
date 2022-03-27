<%--
  Created by IntelliJ IDEA.
  User: dwaki
  Date: 27-03-2022
  Time: 11:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <div align="center">

        <h1>Sign In</h1><br><br>
        <p>Don't have an account?<a href="signup">Sign Up</a></p><br>

        <form method="post" action="process-login">
            <label for="emailId">Email</label>
            <input type="text" id="emailId" name="email"> <br><br>

            <label for="pass">Password</label>
            <input type="text" id="pass" name="password"> <br><br>

            <a href="forgot-password">Forgot your password?</a><br><br>

            <input type="submit" value="Sign In">

        </form>

        <c:if test="${invalid == true}">
            <h2>Invalid Email Id and Password</h2>
        </c:if>
    </div>


</body>
</html>
