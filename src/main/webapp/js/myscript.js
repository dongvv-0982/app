/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function like(id) {

    var request = new XMLHttpRequest();
    request.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {

            document.getElementById("tweet" + id).innerHTML = this.responseText;
        }
    };
    request.open("GET", "like?id=" + id, true);

    request.send();
    return;
}
function  delete_tweet(id,csrf) {
    console.log(id)
    var request = new XMLHttpRequest();
    request.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            var x = this.responseText; 
            if(x === "Deleted"){
                document.getElementById("delete"+id).remove();
            }
        }
    };
    request.open("GET", "delete?id=" + id+"&csrf="+csrf, true);
    request.send();
    return;
}
function follow(username, follower, action,csrf) {
    
    var request = new XMLHttpRequest();
    request.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            document.getElementById("follow"+username).innerHTML = this.responseText;
        }
    };
    request.open("GET", "follow?username=" + username + "&follower=" + follower + "&action=" + action+"&csrf="+csrf, true);
    request.send();
    return;
}
function report(id) {

    var request = new XMLHttpRequest();

    request.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            var x = this.responseText;
            console.log("aaa")
            if (!x.includes("<meta")) {
                document.getElementById("report" + id).innerHTML = this.responseText;
                document.getElementById("page").innerHTML = "";
            } else {
                document.getElementById("report" + id).innerHTML = "";
                document.getElementById("page").innerHTML = this.responseText ;
            }
        }
    };
    request.open("GET", "report?id=" + id, true);

    request.send();
    return;
}



