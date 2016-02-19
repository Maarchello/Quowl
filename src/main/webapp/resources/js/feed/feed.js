$(document).ready(function(){

    var leftMenu = document.getElementById("left-menu");
    var body = document.getElementById("body");
    modal = document.getElementById('myModal');

    window.onclick = function(event) {
        if (event.target == leftMenu || event.target == body) {
            clearAll();
        }
        if (event.target == modal) {
            closeModal();
        }
    }
});

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

function logout() {
    $.ajax({
        url: "/logout.html",
        type: "GET",
        success: function(data) {
            location.reload();
        },
        error: function(data) {
            console.log(data);
        }
    })
}


function addQuote() {
    var book = getBook();
    var author = getAuthor();
    if (book == 'Введите название книги') {
        book = null;
    }
    if (author == 'Введите автора') {
        author = null;
    }

    if (!(book && author)) {
        alert("Введите имя автора и название книги, которую сейчас читаете.");
        return;
    }

    var quote = $('#quote').val();
    quote = quote.trim();
    if (quote) {
        $.ajax({
            url: "addQuote",
            type: "POST",
            data: ({quote: quote}),
            success: function (data) {
                console.log(data.data);
                if (!data.error) {
                    $('#quotes').prepend('<div id="'+data.data.id+'" class="cart border">' +
                        '<div class="username">'+
                        '<img src="/resources/img/nerd_2.jpg" class="userava"/>'+
                        '<a href="'+data.data.userNickname+'">marat</a>'+
                        '<b><span class="pull-right" style="color:grey;opacity: 0.6;font-size:12pt;">'+data.data.date+'</span></b>'+
                        '</div>'+
                        '<div style="border:none; font-weight:900; font-size:13pt;width:80%;font-family: Copperplate;" class="col-centered">'+data.data.author+'</div>'+
                        '<hr class="line margin-bottom" />'+

                        '<div id="quote_text_'+data.data.id+'" style="font-family:Copperplate;padding: 10px;">'+data.data.text+'</div>'+

                        '<hr class="line margin-top" />'+
                        '<div style="border:none;margin-bottom:30px;font-weight:900;font-size:13pt;width:80%;font-family:Copperplate;" class="col-centered">'+data.data.book+'</div>'+

                        '<div class="border" style="height: 40px;">'+
                        '<div class="pull-left" style="border: solid 1px black;width:10%;min-height:inherit;display:inline-block">Like</div>'+

                        '<div style="display:inline-block;min-height:inherit;width: 80%" class="pull-left">'+
                        '<input class="form-control" style="min-height: 100%" width="100%" placeholder="Введите комментарий"/>'+
                        '</div>'+
                        '<div id="'+data.data.id+'" style="display: inline-block; cursor:pointer; width: 10%; height:100%;" onclick="editorOfQuote(this.id);">'+
                        '<img src="/resources/img/edit.png" />'+
                        '</div>'+
                        '</div>'+
                        '</div>');
                    quantityQuotesIncrement();
                    $('#quote').val('');
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
        url: "like/"+quote_id,
        type: "POST",
        success: function(data) {
            if (!data.error) {
                var likes = $('#'+quote_id).find('#likes').text();
                var intLikes;
                if (data.data === 'increment') {
                    intLikes = incrementString(likes);
                    element.find('#like_unlike').attr('src', '/resources/img/like.png');
                } else if (data.data === 'decrement') {
                    intLikes = decrementString(likes);
                    element.find('#like_unlike').attr('src', '/resources/img/unlike.png');
                }
                $('#'+quote_id).find('#likes').text(intLikes);
            }
        }
    })
}

function editorOfQuote(id) {
    openModal(id);
    $('#quote_edit').val($('#quote_text_'+id).text());
}


function editQuote(id) {
    var text = $('#quote_edit').val();
    $.ajax({
        url: "editQuote/"+id+'?text='+text,
        type: "PUT",
        success: function(data) {
            if (!data.error) {
                $('#quote_text_'+id).text(text);
                closeModal();
            }
        }

    })
}


function deleteQuote(id) {
    $.ajax({
        url: "deleteQuote/"+id,
        type: "DELETE",
        success: function(data) {
            if (!data.error) {
                $('#' + id).remove();
                quantityQuotesDecrement();
                closeModal();
            }
        }
    })
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

function quantityQuotesIncrement() {
    var count_quotes = $('#count_quotes').text();
    var count = parseInt(count_quotes);
    $('#count_quotes').text(count+1);
}

function quantityQuotesDecrement() {
    var count_quotes = $('#count_quotes').text();
    var count = parseInt(count_quotes);
    $('#count_quotes').text(count-1);
}

function getBook() {
    return $('#book').text();
}

function getAuthor() {
    return $('#author').text();
}

var stChange = false;

function editStatus(name) {
    clear();
    var status = $('.'+name).text();
    $('.'+name).hide();
    $('#'+name+'_edit').show().val(status);
    stChange = true;
}

function clear() {
    var authorNameEdit = $('#authorName_edit');
    if (authorNameEdit.is(':visible')) {
        var authorName = authorNameEdit.val();
        authorNameEdit.hide();
        $('.authorName').show();
        if (authorName) {
            $('.authorName').text(authorName);
        } else {
            $('.authorName').text("Введите автора");
        }
    }

    var bookNameEdit = $('#bookName_edit');
    if (bookNameEdit.is(':visible')) {
        var bookName = bookNameEdit.val();
        bookNameEdit.hide();
        $('.bookName').show();
        if (bookName) {
            $('.bookName').text(bookName);
        } else {
            $('.bookName').text('Введите название книги');
        }
    }
}

function saveStatus() {
    clear();
    var bookName = $('.bookName').text();
    var authorName = $('.authorName').text();

    if (stChange) {
        alert('save');
        $.ajax({
            url: "saveStatus",
            type: "POST",
            data: ({bookName: bookName, authorName: authorName}),
            success: function (data) {

            }
        });
    }
}