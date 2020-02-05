<%-- 
    Document   : header
    Created on : Jan 3, 2020, 10:14:40 AM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <!-- <meta http-equiv="Content-Security-Policy"
          content="default-src *; style-src 'self' 'unsafe-inline'; script-src 'self' 'unsafe-inline' 'unsafe-eval'; img-src 'self' data:"> -->

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
              integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <script src="../js/myscript.js" type="text/javascript"></script>
        <title>Cyber Links</title>
    </head>
    <body>
        <div class="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-white border-bottom shadow-sm">
            <h5 class="my-0 mr-md-auto font-weight-normal">Cyber Links</h5>
            <nav class="my-2 my-md-0 mr-md-3">
                <img class="rounded-circle" src="https://api.adorable.io/avatars/285/abott@adorable.png" alt=""
                     style="height: 32px;">
                <a class="p-2 text-dark" href="profile">${sessionScope.user}</a>
            </nav>
            <a class="btn btn-outline-primary" href="logout">Sign out</a>
        </div>
    </body>
</html>
