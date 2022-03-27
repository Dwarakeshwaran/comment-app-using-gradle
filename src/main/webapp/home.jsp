<%--
  Created by IntelliJ IDEA.
  User: dwaki
  Date: 27-03-2022
  Time: 12:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
    <style>

        .content{
            margin-top: 5rem;
            margin-left: 5rem;
        }
        .read-comment-area{
           margin: auto;
        }

    </style>
    <!-- CSS only -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body class="content">
    <form method="post" action="submit-comment btn btn-outline-secondary">
        <div class="row write-comment-area">
            <div class="col-4">
                <label for="comment-write-area">What would you like to share with world?</label><br><br>
                <textarea id="comment-write-area" rows="5" cols="60"></textarea>
            </div>
            <div class="col-2">
                <input type="submit" value="Submit" class="btn btn-outline-secondary">
            </div>
        </div>
    </form>

    <div class="read-comment-area">

            <div class="row">
                <div class="col-4">
                    <h3>Comments</h3>
                </div>
                <div class="col-2">
                    <input type="submit" value="Filter" class="btn btn-outline-secondary">
                </div>

            </div>
    </div>


</body>
</html>
