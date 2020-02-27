<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html lang="en">

    <head>
        <!-- Required meta tags -->

        <meta http-equiv="content-type" content="text/html; charset=UTF-8" />


        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <!-- <meta http-equiv="Content-Security-Policy"
          content="default-src *; style-src 'self' 'unsafe-inline'; script-src 'self' 'unsafe-inline' 'unsafe-eval'; img-src 'self' data:"> -->

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
              integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js" type="text/javascript"></script>
        <script src="../js/myscript.js" type="text/javascript"></script>
        <title>Cyber Links</title>
    </head>

    <body>
        <div class="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-white border-bottom shadow-sm">
            <h5 class="my-0 mr-md-auto font-weight-normal">Cyber Links</h5>
            <nav class="my-2 my-md-0 mr-md-3">
                <img class="rounded-circle" src='${requestScope.user.imageBase64 == null ?"https://api.adorable.io/avatars/285/abott@adorable.png" : requestScope.user.imageBase64}'
                     onerror="this.src='https://api.adorable.io/avatars/285/abott@adorable.png'" alt=""
                     style="height: 32px;">
                <a class="p-2 text-dark" href="profile">${sessionScope.user}</a>
            </nav>
            <a class="btn btn-outline-primary" href="logout">Sign out</a>
        </div>

        <main role="main" class="container">
            <p>${requestScope.error == 1 ? "missing parameter" : requestScope.error == 2 
                 ?"Content length limit of 150": requestScope.error == 3 ? "Invalid tweet id sent":"" }</p>
            <form class="mt-2 mt-md-0" action="post" method="POST">
                <input class="form-control mr-sm-2" type="text" placeholder="Link" name="content" aria-label="Search">
                <button class="btn btn-success my-2" type="submit">Post</button>
                <select id="inputGroupSelect01" name="type_id">
                    <option value="1" >Public</option>
                    <option value="0">Private</option>
                </select>
            </form>


            <div class="my-3 p-3 bg-white rounded shadow-sm">
                <h6 class="border-bottom border-gray pb-2 mb-0">Recent links</h6>
                <!--                <div class="media text-muted pt-3">
                                    <img class="rounded-circle mr-2" src="https://api.adorable.io/avatars/285/abott@adorable.png" alt=""
                                         style="height: 32px;">
                                    <div class="media-body pb-3 mb-0 small lh-125 border-bottom border-gray">
                                        <span class="d-block text-gray-dark"><strong>Nguyen Anh Tien</strong><span>&nbsp;@userA</span></span>
                                        <span>let check out my site <a href="">https://cyber.com</a>!</span>
                
                                        <div class="card mt-2">
                                            <img class="card-img-top mt-4" style="height: 200px; width: auto; max-height:100%; margin: auto;"
                                                 src="https://placeimg.com/640/480/tech" alt="Card image cap">
                                            <div class="card-body">
                                                <a href="https://cyber.com" style="color: inherit;">
                                                    <p class="card-text">Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus
                                                        commodo, tortor mauris
                                                        condimentum nibh, ut fermentum massa justo sit amet risus...</p>
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                    <small class="ml-2">
                                        <a href="#">12 Likes</a>
                                        <br>
                                        <a href="#" class="text-muted">Report</a>
                                    </small>
                                </div>-->
                <c:if test="${requestScope.tweets.size() > 0}">
                    <c:forEach items="${requestScope.tweets}" var="p">
                        <div class="media text-muted pt-3" id="delete${p.id}">
                            <img class="rounded-circle mr-2" src='${p.author.imageBase64 == null ?"https://api.adorable.io/avatars/285/abott@adorable.png" : p.author.imageBase64}'
                                 onerror="this.src='https://api.adorable.io/avatars/285/abott@adorable.png'" alt=""
                                 style="height: 32px;">
                            <div class="media-body pb-3 mb-0 small lh-125 border-bottom border-gray">
                                <span class="d-block text-gray-dark"><strong>
                                        ${p.author.name}</strong><span>&nbsp;@<a href="profile?username=${p.author.username}">${p.author.username}</a></span></span>
                                        <c:if test="${p.report == null}">

                                    <span>${p.content == null ? "POST DELETED" : p.content}</span>
                                </c:if>
                                <c:if test="${p.report == 'SPAM' || p.report == 'BROKEN LINK' || p.report == 'INAPPROPRIATE CONTENT'}">
                                    <span>${p.content}</span>
                                    <span style="color:red">MARK AS${p.report}</span>
                                </c:if>



                                <c:if test="${p.link != null}">

                                    <br>
                                    <div class="card mt-2" id="preview${p.id}" onload="alert(1)">
                                        <div id="img${p.id}"></div>
                                        <div id="description${p.id}"></div>
                                        <!--getImg('${p.link}',${p.id});getDescription('${p.link}',${p.id});-->
                                    </div>
                                    <script>
                                        $('#preview${p.id}').ready(function () {
                                            getImg('${p.link}',${p.id});
                                            getDescription('${p.link}',${p.id});


                                        });
                                    </script>
                                </c:if>
                            </div>
                            <c:if test="${p.content != null}">
                                <small class="ml-2">
                                    <div id="tweet${p.id}">
                                        <button onclick="like(${p.id})" style="color:${p.likes.contains(sessionScope.user)? 'blue' : 'gray'}" >${p.likes.size()} likes</button>
                                    </div>
                                    <br>
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
                                    <c:if test="${p.author.username == sessionScope.user}">
                                        <div>
                                            <button onclick="delete_tweet(${p.id}, '${sessionScope.csrf}')" style="color:red" >Delete</button>
                                        </div>
                                    </c:if>

                                </small>
                            </c:if>


                        </div>
                    </c:forEach>
                </c:if>

                <small class="d-block text-right mt-3">
                    <a href="?getall=1">All links</a>
                </small>
            </div>
            <div  id="page" style="width: 90%;left: 5%; position: relative;"></div>
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