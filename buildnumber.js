if (window.XMLHttpRequest) {
    xmlhttp=new XMLHttpRequest();
} else {
    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
}
xmlhttp.onreadystatechange=function() {
    if (xmlhttp.readyState==4 && xmlhttp.status==200) {
        var currentTag = JSON.parse(xmlhttp.responseText)[0].name;
        console.log(currentTag);
        phantom.exit();
    }
}

xmlhttp.open("GET", "https://api.github.com/repos/minigamecore/minigamecore/tags", true );
xmlhttp.send(); 
