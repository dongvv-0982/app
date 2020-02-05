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

        <link href="croppie.css" rel="stylesheet" type="text/css"/>
        <script src="../js/croppie.js" type="text/javascript"></script>
        <script>
            var basic = $('#demo-basic').croppie({
                viewport: {
                    width: 150,
                    height: 200
                }
            });
            basic.croppie('bind', {
                url: 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.rd.com%2Fadvice%2Fpets%2Fcommon-cat-myths%2F&psig=AOvVaw1zAIMTHlbkTvuJSEWPlgpA&ust=1580961996277000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCOj1x8TEuecCFQAAAAAdAAAAABAD',
                points: [77, 469, 280, 739]
            });
//on button click
            basic.croppie('result', 'html').then(function ('#demo-basic') {
                // html is div (overflow hidden)
                // with img positioned inside.
            });
        </script>
        <style>
            img{
                display: block;
                max-width: 100%;
            }
        </style>
    </head>
    <body>
        <div id="demo-basic" style="overflow: hidden">

        </div>
    </body>
</html>
