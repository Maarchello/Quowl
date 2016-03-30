/**
 * Get started button
 */
$(".button-fill").hover(function () {
    $(this).children(".button-inside").addClass('full');
}, function () {
    $(this).children(".button-inside").removeClass('full');
});
/**
 * Login modal
 */
$('.toggle').on('click', function() {
    $('.login-container').stop().addClass('active');
});

$('.close').on('click', function() {
    $('.login-container').stop().removeClass('active');
});