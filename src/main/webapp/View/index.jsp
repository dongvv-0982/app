<!doctype html>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html lang="en">

    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <!-- <meta http-equiv="Content-Security-Policy"
          content="default-src *; style-src 'self' 'unsafe-inline'; script-src 'self' 'unsafe-inline' 'unsafe-eval'; img-src 'self' data:"> -->

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
              integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

        <title>Cyber Links</title>
    </head>

    <body>
        <div class="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-white border-bottom shadow-sm">
            <h5 class="my-0 mr-md-auto font-weight-normal">Cyber Links</h5>
            <nav class="my-2 my-md-0 mr-md-3">
                <a class="p-2 text-dark" href="../user/login">Sign in </a>
            </nav>
            <a class="btn btn-primary" href="../user/register">Sign up</a>
        </div>

        <main role="main" class="container">
            <c:forEach items="${requestScope.posts}" var="p">
                <div class="media text-muted pt-3">
                    <img class="rounded-circle mr-2" src="https://api.adorable.io/avatars/285/deptrai@adorable.png" alt=""
                         style="height: 32px;">
                    <p class="media-body pb-3 mb-0 small lh-125 border-bottom border-gray">
                        <span class="d-block text-gray-dark"><strong>${p.author.name}</strong><span>&nbsp;<a href="profile?username=${p.author.username}">@${p.author.username}</a></span></span>
                                ${p.content}
                    </p>
                    <small class="ml-2">
                        <a href="like" style="color:${p.likes.contains(sessionScope.user)? blue : gray}">${p.likes.size()}</a>
                        <br>
                        <a href="report" class="text-muted">Report</a>
                    </small>
                </div>
            </c:forEach>
            <c:if test="${requestScope.posts.size() == 0}">
                <img src="no_content.jpg" alt="Sorry, No tweet for you"/>
            </c:if>

        </main>

        <!-- Optional JavaScript -->
        <!-- jQuery first, then Popper.js, then Bootstrap JS -->
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
                integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
                integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
                integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
    </body>

</html>