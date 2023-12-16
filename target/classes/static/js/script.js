$(document).ready(function(){
  $('.header-slider').slick({
    arrows: true,
    slidesToShow: 3,
  });

  $('.slider-picture-wrapper').on('click', function(event) {
    event.stopPropagation();
    var popupId = $(this).data('popup');
    $('#' + popupId).fadeIn();
    $('.overlay').fadeIn(); 
  });

  $('.popup .close').on('click', function() {
    $(this).closest('.popup').fadeOut();
    $('.overlay').fadeOut();
  });

  $(document).on('click', function(event) {
    if (!$(event.target).closest('.popup').length) {
      $('.popup').fadeOut();
      $('.overlay').fadeOut(); 
    }
  });
});