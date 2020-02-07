<%-- 
    Document   : remind
    Created on : Dec 30, 2019, 7:31:03 AM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <script>
            alert("You must login first");
            
            document.location.href="${requestScope.context_path}../user/login";
            
        </script>
        <a href="../user/login">
            <button>Click here if you do not automatic redirect</button>
        </a>
    </body>
</html>
