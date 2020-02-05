<%@page import="dal.ControlDAO"%>
<%@page import="model.User"%>
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
        <script src="../js/myscript.js" type="text/javascript"></script>
        
        <title>Cyber Links</title>
    </head>

    <body>
        <div class="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-white border-bottom shadow-sm">

            <h5 class="my-0 mr-md-auto font-weight-normal"><a href="dashboard">Cyber Links</a></h5>
            <nav class="my-2 my-md-0 mr-md-3">
                <img class="rounded-circle" src="https://api.adorable.io/avatars/285/abott@adorable.png" alt=""
                     style="height: 32px;">
                <a class="p-2 text-dark" href="profile">${sessionScope.user}</a>
            </nav>
            <a class="btn btn-outline-primary" href="logout">Sign out</a>
        </div>

        <main role="main" class="container">
            <div class="container">
                <div class="row">
                    <div class="col-4">
                        <div class="d-flex flex-column my-3 p-3 bg-white rounded shadow-sm text-center">
                            <img class="rounded-circle mx-auto" src="https://api.adorable.io/avatars/285/abott@adorable.png" alt=""
                                 width="64" height="64">
                            <strong class="p-2 text-muted" href="#">@${requestScope.user.username}</strong>
                            <a class="p-2 text-dark" href="profile">${requestScope.user.name}</a>
                            <%
                                User user = (User) request.getAttribute("user");
                                String suser = (String) request.getSession().getAttribute("user");
                                ControlDAO db = new ControlDAO();
                                if (!user.getUsername().equals(suser)) {%>
                            <div id="follow">
                                <%if (db.isFollow(user.getUsername(), suser)) {%>
                                <button class="btn btn-outline-primary" onclick="follow('<%=user.getUsername()%>','<%=suser%>',1,${sessionScope.csrf})">Waiting</button>
                                <%} else if (db.isFollowed(user.getUsername(), suser)) {%>

                                <button class="btn btn-outline-primary" onclick="follow('<%=user.getUsername()%>','<%=suser%>',1,${sessionScope.csrf})">Followed</button>
                                <%} else {%>
                                <button class="btn btn-outline-primary" onclick="follow('<%=user.getUsername()%>','<%=suser%>',0,${sessionScope.csrf})">Follow</button>
                                <%}%>
                            </div>
                            <%}%>

                        </div>
                        <div class="d-flex flex-column my-10 px-2 pb-4 bg-white rounded shadow-sm text-center" >
                            <h5 class="mb-5">Follow Requests</h5>

                            <%if (user.getUsername().equals(suser)) {%>
                            <div id="follow">
                                <c:forEach items="${requestScope.followers}" var="u">
                                    <div class="d-flex flex-row mb-2 justify-content-between">
                                        <div class="text-left col-6">${u.name} @${u.username}</div>
                                        <div class="col-6 d-flex flex-row justify-content-end">
                                            <button class="btn-sm mr-2 btn-primary" onclick="follow('<%=suser%>','${u.username}', 2)">Approve</button>
                                            <button class="btn-sm btn-danger" onclick="follow('<%=suser%>','${u.username}', 3)">Reject</button>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                            <%}%>



                        </div>
                    </div>

                    <div class="col-8">
                        <div class="my-3 p-3 bg-white rounded shadow-sm">
                            <h6 class="border-bottom border-gray pb-2 mb-0">Recent links</h6>
                            <c:if test="${requestScope.tweets.size() > 0}">
                                <c:forEach items="${requestScope.tweets}" var="p">
                                    <div class="media text-muted pt-3">
                                        <img class="rounded-circle mr-2" src="https://api.adorable.io/avatars/285/deptrai@adorable.png" alt=""
                                             style="height: 32px;">
                                        <p class="media-body pb-3 mb-0 small lh-125 border-bottom border-gray">
                                            <span class="d-block text-gray-dark"><strong>
                                                    ${p.author.name}</strong><span>&nbsp;<a href="profile?username=${p.author.username}">@${p.author.username}</a></span></span>
                                                    ${p.content}
                                        </p>
                                        <small class="ml-2">
                                            <div id="tweet${p.id}">
                                                <button onclick="like(${p.id})" style="color:${p.likes.contains(sessionScope.user)? 'blue' : 'gray'}" >${p.likes.size()} likes</button>
                                            </div>
                                            <div id="report${p.id}">
                                                <c:choose>

                                                    <c:when test="${p.containr(sessionScope.user)}">
                                                        <button onclick="report(${p.id})" style="color:red" >Reported</button>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <button onclick="report(${p.id})" style="color:gray" >Report</button>
                                                    </c:otherwise>

                                                </c:choose>
                                            </div>
                                        </small>
                                    </div>
                                </c:forEach>
                            </c:if>


                            <small class="d-block text-center mt-3">
                                <strong>The End</strong>
                            </small>
                            <div id="page"></div>
                        </div>
                    </div>
                </div>
            </div>
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