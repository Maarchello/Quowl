function signup() {
    deleteErrors();
    var username = $('#j_name').val().trim();
    var email = $('#j_email').val().trim();
    var password = $('#j_password').val().trim();

    if (fieldsNotEmpty(username, email, password)) {
        $.ajax({
            url: "signup",
            type: "POST",
            data: ({username: username, password: password, email: email}),
            success: function(JsonResultBean) {
                if (JsonResultBean.error) {
                    $('#result_signup_error').show().text(JsonResultBean.error);
                }
            }
        });
    }

}

function signin() {
    deleteErrors();
    var username = $('#name').val().trim();
    var password = $('#password').val().trim();

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