$(document).ready(function () {

    var leftMenu = document.getElementById("left-menu");
    var body = document.getElementById("body");
    modal = document.getElementById('myModal');

    window.onclick = function (event) {
        if (event.target == leftMenu || event.target == body) {
            clearAll();
        }
        if (event.target == modal) {
            closeModal();
        }
    }
});

var stChange = false;
var bkComplete = true;

function openModal(id) {
    modal.style.display = "block";
    modal.id = id;
}


function closeModal() {
    modal.style.display = "none";
}

function clearAll() {
    clear();
    $('#quote').css('border', 'solid 1px #ccc');
}


function addQuote() {
    var book = getBook();
    var author = getAuthor();
    if (book == 'Название книги') {
        book = null;
    }
    if (author == 'Автор') {
        author = null;
    }

    if (!(book && author)) {
        alert("Введите имя автора и название книги, которую сейчас читаете в левом меню.");
        return;
    }

    var quote = $('#quote').val();
    quote = quote.trim();
    if (quote) {
        $.ajax({
            url: "quote/add",
            type: "POST",
            data: ({quote: quote}),
            success: function (data) {
                console.log(data);
                if (!data.error) {
                    $('#quu').prepend('<section id="quotes" class="col-lg-4 col-md-5 col-sm-7 col-xs-8 col-centered">' +
                        '<div id="' + data.data.id + '" class="cart border_shadow">' +
                        '<div class="username">' +
                        '<img src="'+data.data.userAvatar+'" width="100" class="userava"/>' +
                        '<a href="' + data.data.userNickname + '">' + data.data.userNickname + '</a>' +
                        '<b><span class="pull-right" style="color:grey;opacity: 0.6;font-size:12pt;">' + data.data.date + '</span></b>' +
                        '</div>' +
                        '<div style="margin-top: 20px;" class="field col-centered">' + data.data.author + '</div>' +
                        '<hr class="line margin-bottom" />' +

                        '<div class="text-center" id="quote_text_' + data.data.id + '" style="font-family:Copperplate; padding: 10px 25px;">' + data.data.text + '</div>' +

                        '<hr class="line margin-top" />' +
                        '<div style="margin-bottom:30px;" class="field col-centered">' + data.data.book + '</div>' +

                        '<div style="height: 40px;">' +
                        '<div class="pull-left like" onclick="like($(this).parent().parent());">' +
                        '<img id="like_unlike" src="/resources/img/unlike.png"  width="30" height="30" />' +
                        '</div>' +
                        '<div class="pull-left likes_count" id="likes" ">0</div>' +

                            /*'<div style="display:inline-block;min-height:inherit;width: 80%" class="pull-left">'+
                             '<input class="form-control" style="min-height: 100%" width="100%" placeholder="Введите комментарий"/>'+
                             '</div>'+*/
                        '<div id="' + data.data.id + '" class="pull-right" style="display: inline-block; cursor:pointer; width: 8%; height: 100%;" onclick="editorOfQuote(this.id);">' +
                        '<img src="/resources/img/edit.png" width="30" />' +
                        '</div>' +
                        '</div>' +
                        '</div>' +
                        '</section>');
                    /*$('#quotes').prepend('<div id="'+data.data.id+'" class="cart border_shadow">' +
                     '<div class="username">'+
                     '<img src="/resources/img/nerd_2.jpg" width="100" class="userava"/>'+
                     '<a href="'+data.data.userNickname+'"> marat</a>'+
                     '<b><span class="pull-right" style="color:grey;opacity: 0.6;font-size:12pt;">'+data.data.date+'</span></b>'+
                     '</div>'+
                     '<div style="margin-top: 20px;" class="field col-centered">'+data.data.author+'</div>'+
                     '<hr class="line margin-bottom" />'+

                     '<div class="text-center" id="quote_text_'+data.data.id+'" style="font-family:Copperplate; padding: 10px 25px;">'+data.data.text+'</div>'+

                     '<hr class="line margin-top" />'+
                     '<div style="margin-bottom:30px;" class="field col-centered">'+data.data.book+'</div>'+

                     '<div style="height: 40px;">'+
                     '<div class="pull-left like" onclick="like($(this).parent().parent());">' +
                     '<img id="like_unlike" src="/resources/img/unlike.png"  width="30" height="30" />' +
                     '</div>'+
                     '<div class="pull-left likes_count" id="likes" ">0</div>'+

                     /!*'<div style="display:inline-block;min-height:inherit;width: 80%" class="pull-left">'+
                     '<input class="form-control" style="min-height: 100%" width="100%" placeholder="Введите комментарий"/>'+
                     '</div>'+*!/
                     '<div id="'+data.data.id+'" class="pull-right" style="display: inline-block; cursor:pointer; width: 8%; height: 100%;" onclick="editorOfQuote(this.id);">'+
                     '<img src="/resources/img/edit.png" width="30" />'+
                     '</div>'+
                     '</div>'+
                     '</div>');*/
                    quantityQuotesIncrement();
                    $('#quote').val('');
                } else {
                    if (data.error === 'S210') {
                        alert('Превышено максимальное количество символов для цитаты - 2000.');
                    }
                }
            }
        });
    } else {
        $('#quote').css('border', 'solid 1px red');
    }
}

function like(element) {
    var quote_id = element.attr('id');

    $.ajax({
        url: "like/" + quote_id,
        type: "POST",
        success: function (data) {
            if (!data.error) {
                var likes = $('#' + quote_id).find('#likes').text();
                var intLikes;
                if (data.data === 'increment') {
                    intLikes = incrementString(likes);
                    element.find('#like_unlike').attr('src', '/resources/img/like.png');
                } else if (data.data === 'decrement') {
                    intLikes = decrementString(likes);
                    element.find('#like_unlike').attr('src', '/resources/img/unlike.png');
                }
                $('#' + quote_id).find('#likes').text(intLikes);
            }
        }
    })
}

function editorOfQuote(id) {
    openModal(id);
    $('#quote_edit').val($('#quote_text_' + id).text());
}


function editQuote(id) {
    var text = $('#quote_edit').val();
    $.ajax({
        url: "quote/edit/" + id + '?text=' + text,
        type: "PUT",
        success: function (data) {
            if (!data.error) {
                $('#quote_text_' + id).text(text);
                closeModal();
            }
        }

    })
}


function deleteQuote(id) {
    $.ajax({
        url: "quote/delete/" + id,
        type: "DELETE",
        success: function (data) {
            if (!data.error) {
                $('#' + id).remove();
                quantityQuotesDecrement();
                closeModal();
            }
        }
    })
}

function quantityQuotesIncrement() {
    var count_quotes = $('#count_quotes').text();
    var count = parseInt(count_quotes);
    $('#count_quotes').text(count + 1);
}

function quantityQuotesDecrement() {
    var count_quotes = $('#count_quotes').text();
    var count = parseInt(count_quotes);
    $('#count_quotes').text(count - 1);
}

function getBook() {
    return $('#book').text();
}

function getAuthor() {
    return $('#author').text();
}

function editStatus(name) {
    clear();
    var status = $('.' + name).text();
    $('.' + name).hide();
    $('#' + name + '_edit').show().val(status);
    stChange = true;
    bkComplete = true;
}

function clear() {
    var authorNameEdit = $('#authorName_edit');
    if (authorNameEdit.is(':visible')) {
        var authorName = authorNameEdit.val();
        authorNameEdit.hide();
        $('.authorName').show();
        if (authorName) {
            $('.authorName').text(authorName).css('color', 'black');
        } else {
            $('.authorName').text("Автор").css('color', 'darkgrey');
        }
    }

    var bookNameEdit = $('#bookName_edit');
    if (bookNameEdit.is(':visible')) {
        var bookName = bookNameEdit.val();
        bookNameEdit.hide();
        $('.bookName').show();
        if (bookName) {
            $('.bookName').text(bookName).css('color', 'black');
        } else {
            $('.bookName').text('Название книги').css('color', 'darkgrey');
        }
    }
}

function saveStatus() {
    clear();
    var bookName = $('.bookName').text();
    var authorName = $('.authorName').text();

    if (stChange) {
        $.ajax({
            url: "status/save",
            type: "POST",
            data: ({bookName: bookName, authorName: authorName}),
            success: function (data) {
                if (!data.error) {
                    $('#status_message').text('Сохранено!').show(300);
                    setTimeout(function () {
                        $('#status_message').hide(300);
                    }, 2000);
                    bkComplete = true;
                }
            }
        });
    }
}


function bookComplete() {
    if (bkComplete) {
        $.ajax({
            url: "book/complete",
            type: "GET",
            success: function (data) {
                if (!data.error) {
                    var count_books = $('#count_books').text();
                    var count = incrementString(count_books);
                    $('#count_books').text(count);
                    $('#status_message').text('Молодец, продолжай читать!').css('font-size', '14px').show(300);
                    setTimeout(function () {
                        $('#status_message').hide(300);
                    }, 3000);
                    clearStatus();
                    bkComplete = false;
                } else if (data.error == 'S100') {
                    $('#status_message').text('Книга уже добавленна в прочитанные.').css('font-size', '13px').show(300);
                    setTimeout(function () {
                        $('#status_message').hide(300);
                    }, 3000);
                    bkComplete = false;
                } else if (data.error == 'S130') {
                    $('#status_message').text('Автор не установлен. Введите автора и нажмите "Сохранить"').css('font-size', '13px').show(300);
                    setTimeout(function () {
                        $('#status_message').hide(300);
                    }, 3000);
                    bkComplete = true;

                } else if (data.error == 'S131'){
                    $("#status_message").text("Название книги не установлено").css('font-size', '13px').show(300);
                    setTimeout(function () {
                        $('#status_message').hide(300);
                    }, 3000);
                    bkComplete = true;

                } else if (data.error = 'S000') {
                    console.log('Error in Book controller.');
                    alert('Неизвестная ошибка.')
                }
            }
        })
    }
}

function clearStatus() {
    $('.authorName').text("Автор").css('color', 'darkgrey');
    $('.bookName').text('Название книги').css('color', 'darkgrey');
}

var page = 1;
function moreQuotes() {
    $.ajax({
        url: 'quotes/more/' + page,
        type: 'GET',
        success: function (data) {
            if (!data.error) {
                var quotes = data.datas[0];
                var currentUser = data.datas[1];
                var keys = Object.keys(quotes);

                for (var i = 0; i < keys.length; i++) {
                    var quote = quotes[i];
                    $('#quu').append('<section id="quotes" class="col-lg-4 col-md-5 col-sm-7 col-xs-8 col-centered">' +
                        '<div id="' + quote.id + '" class="cart border_shadow">' +
                        '<div class="username">' +
                        '<img src="'+quote.userAvatar+'" width="100" class="userava"/>' +
                        '<a href="' + quote.userNickname + '">' + quote.userNickname + '</a>' +
                        '<b><span class="pull-right" style="color:grey;opacity: 0.6;font-size:12pt;">' + quote.date + '</span></b>' +
                        '</div>' +
                        '<div style="margin-top: 20px;" class="field col-centered">' + quote.author + '</div>' +
                        '<hr class="line margin-bottom" />' +

                        '<div class="text-center" id="quote_text_' + quote.id + '" style="font-family:Copperplate; padding: 10px 25px;">' + quote.text + '</div>' +

                        '<hr class="line margin-top" />' +
                        '<div style="margin-bottom:30px; cursor: pointer;" class="field col-centered" onclick="addBook(' + quote.userId + ', \'' + quote.book + '\', \'' + currentUser.id + '\')">' + quote.book + '</div>' +

                        '<div style="height: 40px;">' +
                        '<div class="pull-left like" onclick="like($(this).parent().parent());">' +
                        '<img id="like_unlike" src="' + containsUser(quote.users, currentUser) + '" width="30" height="30" />' +
                        '</div>' +
                        '<div class="pull-left likes_count" id="likes">' + quote.users.length + '</div>' +
                        '<div id="' + quote.id + '" class="pull-right" style="display: inline-block; cursor:pointer; width: 8%; height: 100%;" onclick="editorOfQuote(this.id);">' +
                        '<img src="/resources/img/edit.png" width="30" />' +
                        '</div>' +
                        '</div>' +
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

function containsUser(list, value) {
    var keys = Object.keys(list);
    var contains = false;
    for (var i = 0; i < keys.length; i++) {
        var user = list[i];
        if (user.id == value.id) {
            contains = true;
        }
    }
    if (contains) {
        return "/resources/img/like.png"
    } else {
        return "/resources/img/unlike.png";
    }
}

function addBook(user_id, book, currentUserId) {
    var sure = confirm("Вы хотите добавить эту книгу в свой плановый список?");
    if (sure) {
        var currentId = parseInt(currentUserId);
        var userId = parseInt(user_id);
        if (userId && book && currentId) {
            $.ajax({
                url: 'bookplan/add/' + book + '/' + userId + '/' + currentId,
                type: 'GET',
                success: function (data) {
                    if (!data.error) {
                        alert('Книга успешно добавлена в Ваш плановый список.');
                    } else if (data.error == 'S110') {
                        alert('Эта книга уже есть в Вашем плановом списке.')
                    }
                }
            })
        }
    }
}


function listenEnter(input, id) {
    alert('listening');
    input.addEventListener("keypress", function(e) {
       if (e.keyCode === 13) {
           var message = $(input).val();
           addComment(message, id);
       }
    });
}

function send(span, userId, userNickname) {
    var input = $(span).prev();
    var id = $(input).attr('id');
    var message = $(input).val();

    var validate = validation(message);

    if (validate) {
        addComment(message, id, userId, userNickname, input);
    }
}

function addComment(message, quote_id, userId, userNickname, input) {
    $.ajax({
        url: '/comment/add',
        type: 'POST',
        data: ({comment: message, quote_id: quote_id, user_id: userId, user_nickname: userNickname}),
        success: function(data) {
            if (!data.error) {
                console.log(data);
                var comment = data.data;
                $(input).val('');
                $('#comments').append('<div>'+comment.message+'</div>');
            } else if (data.error == 'S300') {
                alert('Empty comment');
            }
        }
    })
}

function validation(string) {
    var str = string.trim();
    return str.length > 0
}

function deleteComment(id) {
    $.ajax({
        url: '/comment/delete/'+id,
        type: 'DELETE',
        success: function(data) {
            if (!data.error) {
                $('#comment_' + id).remove();
            }
        }
    })
}

function subscribe(following, follower) {
    $.ajax({
        url: 'subscribe',
        type: 'POST',
        data: ({following: following, follower:follower}),
        success: function(data) {
            if (!data.error) {
                $('#'+following).remove();
            }
        }
    });
}