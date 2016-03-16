$(document).ready(function() {
    $('.current').on('click', function() {
        $('.current').each(function(){
            $(this).removeClass('selected');
        });

        $(this).addClass('selected');
    });

});


function quotes(nickname) {
    var quotes = $('#quotes_content');
    showOneMenu(quotes);
}

function subscribe(following) {
    $.ajax({
        url: 'subscribe',
        type: 'POST',
        data: ({following: following}),
        success: function(data) {
            if (!data.error) {
                var elementFollowers = $('#followers');
                if (data.data == 'Increment') {
                    var followers = elementFollowers.text();
                    var followersInt = incrementString(followers);
                    elementFollowers.text(followersInt);
                    $('.sub').text('Отписаться');
                    $('.sub_wrap').removeClass('subscribe');
                    $('.sub_wrap').addClass('unsubscribe');
                } else if (data.data == 'Decrement') {
                    var followers = elementFollowers.text();
                    var followersInt = decrementString(followers);
                    elementFollowers.text(followersInt);
                    $('.sub').text('Подписаться');
                    $('.sub_wrap').removeClass('unsubscribe');
                    $('.sub_wrap').addClass('subscribe');
                }
            }
        }
    });
}

function showOneMenu(menu) {
    $('.flag').each(function(){
        $(this).hide();
    });
    menu.show();
}


var followersBean = {};
function followers(followers) {
    if (!followersBean.length) {
        $.ajax({
            url: 'followers',
            type: 'POST',
            data: ({followers: followers}),
            success: function (data) {
                if (!data.error) {
                    followersBean = data.data;
                    drawFollowers(data.data);
                }
            }
        });
    } else {
        drawFollowers(followersBean);
    }

}

var followingBean = {};
function following(following) {
    if (!followingBean.length) {
        $.ajax({
            url: '/followings',
            type: 'POST',
            data: ({following: following}),
            success: function(data) {
                followingBean = data.data;
                drawFollowers(data.data);
            }
        })
    } else {
        drawFollowers(followingBean);
    }
}

function drawFollowers(followers) {
    var profile_content = $('#profile_content');
    profile_content.empty();
    showOneMenu(profile_content);

    var keys = Object.keys(followers);
    for (var i = 0; i < keys.length; i++) {
        var user = followers[i];
        profile_content.append(
            '<section class="col-lg-3 col-md-5 follower border ">' +
            '<div class="border-bottom text-center" style="padding: 10px;">' +
            '<img src="/resources/img/nerd_2.jpg" class="userava"/>' +
            '<a href="'+user.nickname+'">'+user.nickname+'</a>' +
            '</div>' +

            '<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 col-centered" style="overflow: hidden;">' +
            '<div class="profile_menu col-lg-4 col-md-4 col-sm-4 col-xs-4" style="border-left: none; padding: 0; margin-left: -10px;">' +
            '<span style="font-weight: 700; ">'+user.countReadBooks+'</span>' +
            '<div>Книг</div>' +
            '</div>' +
            '<div class="profile_menu col-lg-4 col-md-4 col-sm-4 col-xs-4" >' +
            '<span style="font-weight: 700;">'+user.countQuotes+'</span>' +
            '<div>Цитат</div>' +
            '</div>' +
            '<div class="profile_menu col-lg-4 col-md-4 col-sm-4 col-xs-4" >' +
            '<span style="font-weight: 700;">'+user.followers.length+'</span>' +
            '<div>Подписчиков</div>' +
            '</div>' +
            '</div>' +
            '<hr class="hr" style="margin-top: 0;" />' +
            '<div class="text-center margin-top">Сейчас читает:</div>' +
            '<div id="status">' +
            '<div class="status" style="text-decoration: none; cursor: default">' +
            '<span>'+user.authorName+'</span>' +
            '</div>' +
            '<div class="status" style="text-decoration: none; cursor: default">' +
            '<span>'+user.bookName+'</span>' +
            '</div>' +
            '</div><br />' +
            '</section>'
        )
    }
}

var bookBean = {};
function books(user_id) {
    if (!bookBean.length) {
        $.ajax({
            url: 'books',
            type: 'POST',
            data: ({userId: user_id}),
            success: function(data) {
                bookBean = data.data;
                drawBooks(data.data);
            }
        })
    } else {
        drawBooks(bookBean);
    }
}

function moreQuotes() {
    $.ajax({
        url: 'moreQuotes/'+page,
        type: 'GET',
        success: function(data) {
            if (!data.error) {
                var quotes = data.datas[0];
                var currentUser = data.datas[1];
                var keys = Object.keys(quotes);

                for (var i = 0; i < keys.length; i++) {
                    var quote = quotes[i];
                    $('#quu').append('<section id="quotes" class="col-lg-5 col-md-8 col-sm-12 col-xs-8 col-centered">' +
                        '<div id="'+quote.id+'" class="cart border_shadow">' +
                        '<div class="username">'+
                        '<img src="/resources/img/nerd_2.jpg" width="100" class="userava"/>'+
                        '<a href="'+quote.userNickname+'">'+quote.userNickname+'</a>'+
                        '<b><span class="pull-right" style="color:grey;opacity: 0.6;font-size:12pt;">'+quote.date+'</span></b>'+
                        '</div>'+
                        '<div style="margin-top: 20px;" class="field col-centered">'+quote.author+'</div>'+
                        '<hr class="line margin-bottom" />'+

                        '<div class="text-center" id="quote_text_'+quote.id+'" style="font-family:Copperplate; padding: 10px 25px;">'+quote.text+'</div>'+

                        '<hr class="line margin-top" />'+
                        '<div style="margin-bottom:30px; cursor: pointer;" class="field col-centered" onclick="addBook('+quote.userId+', \''+quote.book+'\', \''+currentUser.id+'\')">'+quote.book+'</div>'+

                        '<div style="height: 40px;">'+
                        '<div class="pull-left like" onclick="like($(this).parent().parent());">' +
                        '<img id="like_unlike" src="'+containsUser(quote.users, currentUser)+'" width="30" height="30" />' +
                        '</div>'+
                        '<div class="pull-left likes_count" id="likes">'+quote.users.length+'</div>'+
                        '<div id="'+quote.id+'" class="pull-right" style="display: inline-block; cursor:pointer; width: 8%; height: 100%;" onclick="editorOfQuote(this.id);">'+
                        '<img src="/resources/img/edit.png" width="30" />'+
                        '</div>'+
                        '</div>'+
                        '</div>' +
                        '</section>')
                }
                page += 1;
            } else if (data.error == 'S200') {
                $('#moreQuotes').remove();
            }
        }
    })
}

function drawBooks(books) {
    var profile_content = $('#profile_content');
    showOneMenu(profile_content);
    profile_content.empty();

    var keys = Object.keys(books);
    for (var i = 0; i < keys.length; i++) {
        var book = books[i];
        profile_content.append('<div class="books" id="'+book.id+'">' +
            '<div class="bookk col-lg-1">' +
            '<span>'+book.author+'</span>' +
            '<img src="/resources/img/background.jpg" height="250" width="200" />' +
            '<span>'+book.name+'</span>' +
            '</div>' +
            '</div>')
    }
}