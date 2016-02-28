function mobileShowSignIn() {
    var signIn = $('#signin');
    var signUp = $('#signup');
    if (signUp.is(':visible')) {
        signUp.hide();
        signIn.show();
    } else if (signIn.is(':visible')){
        signIn.hide();
    } else {
        signIn.show();
    }
}

function mobileShowSignUp() {
    var signIn = $('#signin');
    var signUp = $('#signup');

    if (signIn.is(':visible')) {
        signIn.hide();
        signUp.show();
    } else if (signUp.is(':visible')) {
        signUp.hide();
    } else {
        signUp.show();
    }
}