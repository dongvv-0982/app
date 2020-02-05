<%-- 
    Document   : error
    Created on : Jan 3, 2020, 8:15:39 AM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body style='background-image: url("../View/opps.jpg")'>
        <p style="color: red">${requestScope.error}</p>
    </body>
</html>
