$('.toggle').on('click', function() {
  $('.login-container').stop().addClass('active');
});

$('.close').on('click', function() {
  $('.login-container').stop().removeClass('active');
});