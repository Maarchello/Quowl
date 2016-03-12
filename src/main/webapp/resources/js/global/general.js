$(document).ready(function(){
    var body = document.getElementById('body');
    window.onclick = function(event) {
        if (event.target == body) {
            closeContextMenu();
        }
    }
});


function showContextMenu() {
    var menu = $('#context-menu');
    if (menu.is(':visible')) {
        menu.hide();
    } else {
        menu.show();
    }
}

function closeContextMenu() {
    $('#context-menu').hide();
}

function incrementString(str) {
    var parseStr = parseInt(str);
    return ++parseStr;
}

function decrementString(str) {
    var parseStr = parseInt(str);
    parseStr -= 1;
    if (parseStr <= 0) return 0;
    return parseStr;
}