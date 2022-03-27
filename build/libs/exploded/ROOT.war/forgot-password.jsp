<%--
  Created by IntelliJ IDEA.
  User: dwaki
  Date: 27-03-2022
  Time: 14:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Forgot Password</title>
</head>
<body>
<div align="center">

    <h1>Forgot Password</h1><br><br>

    <form method="post" action="reset-password">
        <label for="emailId">Email</label>
        <input type="text" id="emailId" name="email"> <br><br>

        <label for="secret">Secret</label>
        <input type="text" id="secret" name="secretCode"> <br><br>

        <c:if test="${passwordAvailability == true}">
            <h2>Password: ${password}</h2> <br><br>
        </c:if>

        <c:if test="${passwordAvailability == false}">
            <h2>Invalid Email Id and Secret Code</h2>
        </c:if>

        <input type="submit" value="Get Password"> <br><br>
        <a href="login">Go Back to Login Page</a>

    </form>
</div>
</body>
</html>
