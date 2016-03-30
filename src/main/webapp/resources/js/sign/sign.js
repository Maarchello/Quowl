function signup() {
    deleteErrors();

    var username = $('#registerUsername').val().trim();
    var email = $('#registerEmail').val().trim();
    var password = $('#registerPassword').val().trim();
    var repeatedPassword = $('#registerRepeatPassword').val().trim();

    if (isRegistrationValid(username, email, password, repeatedPassword)) {
        $.ajax({
            url: "signup",
            type: "POST",
            data: ({username: username, password: password, email: email}),
            success: function (JsonResultBean) {
                console.log(JsonResultBean);

                if (!JsonResultBean.error) {
                    $('#regServerResult').show();

                } else if (JsonResultBean.error) {
                    $('#regServerResult').show().text(JsonResultBean.error);
                }
            }
        });
    }

}

function signin() {
    deleteErrors();
    var username = $('#loginUsername').val().trim();
    var password = $('#loginPassword').val().trim();

    if (loginFieldsNotEmpty(username, password)) {
        $.ajax({
            url: "login",
            type: "POST",
            data: ({name: username, password: password}),
            success: function (JsonResultBean) {
                if (JsonResultBean.error) {
                    $('#logServerResult').show().text(JsonResultBean.error).css('color', 'black');
                } else {
                    location.reload();
                }
            }
        })
    }
}

function loginFieldsNotEmpty(name, password) {
    var isSuccess = true;
    if (!name) {
        $('#logNameError').show();
        isSuccess = false;
    } else if (!password) {
        $('#logPassError').show();
        isSuccess = false;
    }

    return isSuccess;
}

function deleteErrors() {
    $('.resultMessage').hide();
}

function registerFieldsNotEmpty(name, email, password, repeatPassword) {
    var isSuccess = true;

    if (!email) {
        $('#regEmailError').show();
        isSuccess = false;
    } else if (!name) {
        $('#regNameError').show();
        isSuccess = false;
    } else if (!password) {
        $('#regPassError').show();
        isSuccess = false;
    } else if (!repeatPassword) {
        $('#regRepeatError').show();
        isSuccess = false;
    }

    return isSuccess;
}

function checkRepeatPassword(password, repeatedPassword) {
    if (password === repeatedPassword) {
        return true;
    } else {
        $('#regPassNotMatched').show();
        return false;
    }

}

function isRegistrationValid(username, email, password, repeatedPassword) {

    var isValid = true;

    if (!registerFieldsNotEmpty(username, email, password, repeatedPassword)) {
        isValid = false;
    } else if (!checkRepeatPassword(password, repeatedPassword)) {
        isValid = false;
    }

    return isValid;
}
