<%--
  Created by IntelliJ IDEA.
  User: dwaki
  Date: 27-03-2022
  Time: 14:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>SignUp</title>
</head>
<body>
<div align="center">

    <h1>Sign Up</h1><br><br>
    <p>Already have an account?<a href="login">Sign Up</a> </p><br>

    <form method="post" action="register-user">
        <label for="emailId">Email</label>
        <input type="text" id="emailId" name="email"> <br><br>

        <label for="pass">Password</label>
        <input type="password" id="pass" name="password"> <br><br>

        <label for="secret">Secret</label>
        <input type="text" id="secret" name="secretCode"> <br><br>

        <c:if test="${existsAlready == true}">
            <h2>User Already exists</h2> <br><br>
        </c:if>

        <input type="submit" value="Sign Up">

    </form>

</body>
</html>
