$(document).ready(function() {
    $('.current').on('click', function() {
        $('.current').each(function(){
            $(this).removeClass('selected');
        });

        $(this).addClass('selected');
    });


    $(document).ajaxStart(function () {
        $("#loading").css({'display' : 'block', 'margin': 'auto'});
    }).ajaxStop(function () {
        $("#loading").hide();
    });
});

function quotes(nickname) {
    var quotes = $('#quotes_content');
    quotes.show();
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
    showOneMenu(profile_content);
    profile_content.empty();

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

function drawBooks(books) {
    console.log(books);
    var profile_content = $('#profile_content');
    showOneMenu(profile_content);
    profile_content.empty();

    var keys = Object.keys(books);
    for (var i = 0; i < keys.length; i++) {
        var book = books[i];
        profile_content.append('<div class="books">' +
            '<div class="bookk col-lg-1">' +
            '<span>'+book.author+'</span>' +
            '<img src="/resources/img/background.jpg" height="250" width="200" />' +
            '<span>'+book.name+'</span>' +
            '</div>' +
            '</div>')
    }
}