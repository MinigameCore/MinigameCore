var system = require('system');
var args = system.args;

var commit = system.args[0];

if (window.XMLHttpRequest) {
    xmlhttp=new XMLHttpRequest();
} else {
    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
}
xmlhttp.onreadystatechange=function() {
    if (xmlhttp.readyState==4 && xmlhttp.status==200) {
        var message = JSON.parse(xmlhttp.responseText).commit.message;
        console.log(message);
        phantom.exit();
    }
}

xmlhttp.open("GET", "https://api.github.com/repos/minigamecore/minigamecore/commits/"+commit, true );
xmlhttp.send(); 
