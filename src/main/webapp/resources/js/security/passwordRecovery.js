function setNewPassword() {
    var password = $('#newPassword').val().trim();
    var recoveryLink = $('#recoveryLink').val();
    $.ajax({
        url: "/precovery",
        type: "POST",
        data: ({password: password, recoveryLink: recoveryLink}),
        success: function (JsonResultBean) {
            console.log(JsonResultBean);
            $('#answer').text(JsonResultBean.data);
            setTimeout(function(){
                document.location.href='http://localhost:8080';
            }, 5000)
        }
    })
}