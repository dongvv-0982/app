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
function  delete_tweet(id, csrf) {
    console.log(id)
    var request = new XMLHttpRequest();
    request.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            var x = this.responseText;
            if (x === "Deleted") {
                document.location.href = '../user/dashboard';
            }
        }
    };
    request.open("GET", "delete?id=" + id + "&csrf=" + csrf, true);
    request.send();
    return;
}
function follow(username, follower, action, csrf) {

    var request = new XMLHttpRequest();
    request.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            document.getElementById("follow" + username).innerHTML = this.responseText;
        }
    };
    request.open("GET", "follow?username=" + username + "&follower=" + follower + "&action=" + action + "&csrf=" + csrf, true);
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
                document.getElementById("page").innerHTML = this.responseText;
            }
        }
    };
    request.open("GET", "report?id=" + id, true);

    request.send();
    return;
}
function getImg(url, id) {
    var request = new XMLHttpRequest();

    request.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            var x = this.responseText;
            console.log(x);
            document.getElementById('img' + id).innerHTML = x;
        }
    };
    request.open("GET", "getImg?url=" + encodeURI(url), true);

    request.send();

}
function getDescription(url, id) {
    var request = new XMLHttpRequest();

    request.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            var x = this.responseText;
            console.log(x);
            document.getElementById('description' + id).innerHTML = x;
        }
    };
    request.open("GET", "description?url=" + encodeURI(url), true);
    request.send();

}
function utf8Encode(unicodeString) {
    if (typeof unicodeString != 'string')
        throw new TypeError('parameter ‘unicodeString’ is not a string');
    const utf8String = unicodeString.replace(
            /[\u0080-\u07ff]/g, // U+0080 - U+07FF => 2 bytes 110yyyyy, 10zzzzzz
            function (c) {
                var cc = c.charCodeAt(0);
                return String.fromCharCode(0xc0 | cc >> 6, 0x80 | cc & 0x3f);
            }
    ).replace(
            /[\u0800-\uffff]/g, // U+0800 - U+FFFF => 3 bytes 1110xxxx, 10yyyyyy, 10zzzzzz
            function (c) {
                var cc = c.charCodeAt(0);
                return String.fromCharCode(0xe0 | cc >> 12, 0x80 | cc >> 6 & 0x3F, 0x80 | cc & 0x3f);
            }
    );
    return utf8String;
}

$(document).ready(function () {

})


