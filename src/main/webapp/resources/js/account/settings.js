function uploadFile() {
    var fileSize = $('#image')[0].files[0].size;
    if (fileSize >= 1000000) {
        alert("The size of your image is too large");
        return;
    }
    $.ajax({
        url: 'settings/avatar/change',
        type: 'POST',
        data: new FormData($('#uploadFile')[0]),
        enctype: 'multipart/form-data',
        contentType: false,
        processData: false,
        cache: false,
        success: function(data) {
            alert('Your image saved successfully');
        }
    });
}


$(function(){
    $('#image').change(function(){
        uploadFile();
    })
});