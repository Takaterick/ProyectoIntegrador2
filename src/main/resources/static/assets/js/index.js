$(document).ready(function () {
    
    $('#carousel-review').owlCarousel({
        loop:true,
        margin:10,
        nav:true,
        responsiveClass:true,
        responsive:{
            0:{
                items:2,
                nav:true
            },
            1053:{
                items:3,
                nav:true
            },
            1500:{
                items:5,
                nav:true,
                loop:false
            }
        }
    })

});