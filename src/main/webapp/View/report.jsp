 
<!doctype html>
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
        
        <main role="main" class="container">
            <div class="jumbotron">
                <h3>Do you want to report?</h3>
                <blockquote class="blockquote">
                    <p class="mb-0">Gau Gau Gau Gau Gau Ang Ang Gau Gau.</p>
                    <footer class="blockquote-footer">Vang <cite title="Source Title">@banlaohac</cite></footer>
                </blockquote>
                <h4 class="mb-3">Report</h4>
                
                <div class="d-block my-3">
                    <form method="POST" action="report">
                        <input type="hidden" name="id" value="${requestScope.id}">
                        <div class="custom-control custom-radio">
                            <input id="broken" name="type" type="radio" class="custom-control-input" value="0" checked required>
                            <label class="custom-control-label" for="broken">Broken Link</label>
                        </div>
                        <div class="custom-control custom-radio">
                            <input id="content" name="type" type="radio" class="custom-control-input" value="1" required>
                            <label class="custom-control-label" for="content">Inappropriate Content</label>
                        </div>
                        <div class="custom-control custom-radio">
                            <input id="paypal" name="type" type="radio" class="custom-control-input" value="2" required>
                            <label class="custom-control-label" for="paypal">Spam</label>
                        </div>
                        <input class="btn btn-lg btn-warning"  type="submit">
                    </form>

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