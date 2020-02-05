<!doctype html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                <img class="rounded-circle" src="https://api.adorable.io/avatars/285/abott@adorable.png" alt=""
                     style="height: 32px;">
                <a class="p-2 text-dark" href="../user/profile">${requestScope.user.name}</a>
            </nav>
            <a class="btn btn-outline-primary" href="../user/logout">Sign out</a>
        </div>

        <main role="main" class="container">
            <h1>Admin</h1>
            <table class="table">
                <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Link</th>
                        <th scope="col">By</th>
                        <th scope="col">Type</th>
                        <th scope="col">Reporter</th>
                        <th scope="col">Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${requestScope.reports}" var="r">
                        <tr>
                            <th scope="row">${r.id}</th>
                            <td>${r.content}</td>
                            <td>
                                ${r.author}
                            </td>
                            <td>
                                ${r.type}
                            </td>
                            <td>
                                ${r.reporter}
                            </td>
                            <td>
                                <form method="POST" id="assessment" hidden="true">
                                    <input type="hidden" value="${r.id}" name="id">
                                    <input type="hidden" value="" id="approve" name="approve">
                                </form>
                                    <a  onclick="approveFrom(1)">Approve</a>
                                    <a  class="text-danger" onclick="approveFrom(0)">Reject</a>
                            </td>
                        </tr>
                    </c:forEach>


                    
                </tbody>
            </table>
        </main>
        <script>
            function approveFrom(id){
                document.getElementById("approve").value = id;
                document.getElementById("assessment").submit();
            }
        </script>

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