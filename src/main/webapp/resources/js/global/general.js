$(document).ready(function(){
    var body = document.getElementById('body');
    window.onclick = function(event) {
        if (event.target == body) {
            closeContextMenu();
        }
    }
});

function searchFriends(nickname) {
    $('#resultSearch').empty();
    if (nickname) {
        $.ajax({
            url: 'search/' + nickname,
            type: 'GET',
            success: function (data) {
                var keys = Object.keys(data.data);
                for (var i = 0; i < keys.length; i++) {
                    var user = data.data[i];
                    $('#resultSearch').append('<a href="'+user[1]+'" class="text-center"><div class="border-bottom link" style="background: white; height:40px;" id="'+user[0]+'"><img src="/resources/img/nerd_2.jpg" width="30" />'+user[1]+'</div></a>')
                }

            }
        })
    }
}

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