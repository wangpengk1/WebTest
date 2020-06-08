<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/6/6 0006
  Time: 20:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="https://cdn.bootcdn.net/ajax/libs/jquery/3.5.1/jquery.js"></script>
    <script src="https://cdn.bootcdn.net/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script>
        $(document).ready(function () {
            $("button").click(function () {
                $("p").hide(1000,function () {
                   $(this).show(1000);
                });
            });
        });
    </script>
</head>
<body>
    <p>aaaaaaaaaaaaaaaaaaaaaaaaaaaaa</p>
    <button>asdasd</button>
</body>
</html>