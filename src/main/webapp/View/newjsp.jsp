<%-- 
    Document   : newjsp
    Created on : Feb 5, 2020, 10:42:48 AM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>

        <link href="../View/croppie.css" rel="stylesheet" type="text/css"/>
        <script src="../js/croppie.js" type="text/javascript"></script>
        <script>
            $(document).ready(function () {
                var $uploadCrop = $('#upload-demo');
                $uploadCrop.croppie({
                    viewport: {
                        width: 64,
                        height: 64,
                        type: 'square'
                    },
                    boundary: {
                        width: 300,
                        height: 300
                    }
                });
                $uploadCrop.croppie('bind', '#image');
                $('.vanilla-rotate').on('click', function (ev) {
                    vanilla.rotate(parseInt($(this).data('deg')));
                });
                $('.upload-result').on('click', function (ev) {
                    $uploadCrop.croppie('result', {
                        type: 'canvas',
                        size: 'original'
                    }).then(function (resp) {
                        $('#imagebase64').val(resp);
                        $('#form').submit();
                    });
                });
            });
            
            function updateUrl() {
                var $uploadCrop = $('#upload-demo');
                
                var url = document.getElementById("url").value;
                $uploadCrop.croppie('bind', url);
                $('.vanilla-rotate').on('click', function (ev) {
                    vanilla.rotate(parseInt($(this).data('deg')));
                });
                $('.upload-result').on('click', function (ev) {
                    $uploadCrop.croppie('result', {
                        type: 'canvas',
                        size: 'original'
                    }).then(function (resp) {
                        $('#imagebase64').val(resp);
                        $('#form').submit();
                    });
                });
            }
        </script>
        <style>
            #page {
                background: #FFF;
                padding: 20px;
                margin: 20px;
            }

            #upload-demo {
                width: 300px;
                height: 300px;
            }
        </style>
    </head>
    <body>
        <input type="text" id="url"><button onclick="updateUrl()">Get Image</button>
        <form action="../user/upload" id="form" method="post">
            <div id="page"><div id="upload-demo"></div></div>
            <input type="hidden" id="imagebase64" name="imagebase64">
            <br>    
            <a href="#" class="upload-result">Send</a>
        </form>
    </body>
</html>
