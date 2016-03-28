function signup() {
    deleteErrors();
    var username = $('#registerUsername').val().trim();
    var email = $('#registerEmail').val().trim();
    var password = $('#registerPassword').val().trim();

    if (fieldsNotEmpty(username, email, password)) {
        $.ajax({
            url: "signup",
            type: "POST",
            data: ({username: username, password: password, email: email}),
            success: function(JsonResultBean) {
                if (!JsonResultBean.error) {
                    var message = $('#result_signup_message');
                    message.show().css('color', 'yellowgreen').text('Регистрация прошла успешно!');

                    setTimeout(function(){
                        message.hide();
                        $('#signup').hide(200);
                    }, 3000);

                } else if (JsonResultBean.error) {
                    $('#result_signup_message').show().text(JsonResultBean.error);
                }
            }
        });
    }

}

function signin() {
    deleteErrors();
    var username = $('#loginUsername').val().trim();
    var password = $('#loginPassword').val().trim();

    if (fieldNotEmptySignin(username, password)) {
        $.ajax({
            url: "login",
            type: "POST",
            data: ({name: username, password: password}),
            success: function(JsonResultBean) {
                if (JsonResultBean.error) {
                    $('#result_signin_error').show().text(JsonResultBean.error);
                } else {
                    location.reload();
                }
            }
        })
    }
}

function fieldNotEmptySignin(name, password) {
    var isSuccess = true;
    if (!name) {
        $('#name_error').show().text('Введите имя.');
        isSuccess = false;
    }

    if (!password) {
        $('#password_error').show().text('Введите пароль.');
        isSuccess = false;
    }

    return isSuccess;
}

function deleteErrors() {
    $('.error_span').hide();
}

function fieldsNotEmpty(name, email, password) {
    var isSuccess = true;
    if (!name) {
        $('#j_name_error').show().text("Введите ваше имя.");
        isSuccess = false;
    }

    if (!email) {
        $('#j_email_error').show().text("Введите ваш email адресс.");
        isSuccess = false;
    }

    if (!password) {
        $('#j_password_error').show().text("Придумайте пароль.");
        isSuccess = false;
    }

    return isSuccess;
}

function showSignUp() {
    if ($('#signup').is(":visible")){
        $('#signup').hide();
    } else $('#signup').show();

}