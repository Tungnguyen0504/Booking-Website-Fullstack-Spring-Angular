import { AfterViewInit, Component } from '@angular/core';
import { User } from './model/User.model';
import { AuthenticationService } from './service/authentication.service';
import { UserService } from './service/user.service';
import { AlertService } from './service/alert.service';
declare var $: any;

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements AfterViewInit {
  title = 'Thepalatin.com';
  user?: User;
  isLogined: boolean = false;

  constructor(
    private $authService: AuthenticationService,
    private $userService: UserService,
    private $alertService: AlertService
  ) {}

  ngAfterViewInit(): void {
    // if ($.fn.owlCarousel) {
    //   var welcomeSlide = $('.hero-slides');
    //   var testimonial = $('.testimonial-slides');

    //   welcomeSlide.owlCarousel({
    //     items: 1,
    //     margin: 0,
    //     loop: true,
    //     nav: false,
    //     dots: true,
    //     autoplay: true,
    //     animateIn: 'fadeIn',
    //     animateOut: 'fadeOut',
    //   });

    //   welcomeSlide.on('translate.owl.carousel', () => {
    //     var slideLayer = $('[data-animation]');
    //     slideLayer.each(() => {
    //       var anim_name = $(this).data('animation');
    //       $(this)
    //         .removeClass('animated ' + anim_name)
    //         .css('opacity', '0');
    //     });
    //   });

    //   welcomeSlide.on('translated.owl.carousel', () => {
    //     var slideLayer = welcomeSlide
    //       .find('.owl-item.active')
    //       .find('[data-animation]');
    //     slideLayer.each(() => {
    //       var anim_name = $(this).data('animation');
    //       $(this)
    //         .addClass('animated ' + anim_name)
    //         .css('opacity', '1');
    //     });
    //   });

    //   $('[data-delay]').each(() => {
    //     var anim_del = $(this).data('delay');
    //     $(this).css('animation-delay', anim_del);
    //   });

    //   $('[data-duration]').each(() => {
    //     var anim_dur = $(this).data('duration');
    //     $(this).css('animation-duration', anim_dur);
    //   });

    //   var dot = $('.hero-slides .owl-dot');
    //   dot.each(() => {
    //     var index = $(this).index() + 1;
    //     if (index < 10) {
    //       $(this).html('0').append(index);
    //     } else {
    //       $(this).html(index);
    //     }
    //   });

    //   testimonial.owlCarousel({
    //     items: 1,
    //     margin: 0,
    //     loop: true,
    //     nav: false,
    //     dots: false,
    //     autoplay: true,
    //     autoplayTimeout: 5000,
    //     smartSpeed: 600,
    //   });
    // }
  }

  ngOnInit(): void {
    this.isLogined = this.$authService.isLoggedIn();
    // this.getCurrentUser();
  }

  getCurrentUser() {
    if (this.isLogined) {
      this.$userService.getCurrentUser().subscribe({
        next: (response) => {
          this.user = response;
        },
        error: (error) => {
          this.isLogined = false;
          this.$alertService.error(error.error.message);
        },
      });
    }
  }
}
