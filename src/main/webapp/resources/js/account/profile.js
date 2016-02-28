$(document).ready(function() {
    $('.current').on('click', function() {
        $('.current').each(function(){
            $(this).removeClass('selected');
        });
        $(this).addClass('selected');
    })
});

function quotes(nickname) {
    var quotes = $('#qqq');
     if (quotes.is(':visible')) {
         quotes.hide();
     } else {
         quotes.show();
     }
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