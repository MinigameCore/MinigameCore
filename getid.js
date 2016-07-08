if (window.XMLHttpRequest) {
    xmlhttp=new XMLHttpRequest();
} else {
    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
}

xmlhttp.onreadystatechange=function() {
    if (xmlhttp.readyState==4 && xmlhttp.status==200) {
        console.log(JSON.parse(xmlhttp.responseText).id);
        phantom.exit();
    }
}

xmlhttp.open("GET", "https://api.github.com/repos/minigamecore/minigamecore/releases/latest", true );
xmlhttp.send(); 