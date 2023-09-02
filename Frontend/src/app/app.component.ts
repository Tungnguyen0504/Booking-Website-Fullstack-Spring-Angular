import { Component, OnInit, Renderer2 } from '@angular/core';
import { AuthenticationService } from './service/authentication.service';
import { User } from './model/User.model';
import * as $ from 'jquery';
import 'owl.carousel';

@Component({
  selector: 'app-user-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {
  title = 'Thepalatin.com';
  user?: User;

  constructor(
    private renderer: Renderer2,
    private authService: AuthenticationService
  ) {}

  ngOnInit(): void {
    this.authService.getCurrentUser().subscribe(
      (response) => {
        this.user = response;
      },
      (error) => {}
    );

    const link = this.renderer.createElement('link');
    this.renderer.setAttribute(link, 'rel', 'stylesheet');
    this.renderer.setAttribute(link, 'type', 'text/css');

    if (typeof this.user === 'undefined' || this.user?.role === 'USER') {
      this.renderer.setAttribute(
        link,
        'href',
        'assets/authentication/css/style.css'
      );

      const scriptAuth = [
        'assets/authentication/js/bootstrap/popper.min.js',
        'https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js',
        'assets/authentication/js/bootstrap/bootstrap.min.js',

        'assets/authentication/js/plugins/plugins.js',

        'https://cdn.jsdelivr.net/npm/flatpickr',
        'https://npmcdn.com/flatpickr/dist/l10n/vn.js',
        'https://unpkg.com/flatpickr@4.6.11/dist/plugins/rangePlugin.js',

        // 'node_modules/owl.carousel/dist/owl.carousel.min.js',

        'https://cdnjs.cloudflare.com/ajax/libs/lightgallery/2.5.0-beta.2/lightgallery.umd.min.js',
        'https://cdnjs.cloudflare.com/ajax/libs/lightgallery/2.5.0-beta.2/lightgallery.min.js',
        'https://cdnjs.cloudflare.com/ajax/libs/lightgallery/2.5.0-beta.2/plugins/thumbnail/lg-thumbnail.min.js',

        'assets/authentication/js/input-spinner-bootstrap/bootstrap-input-spinner.js ',

        'https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js',
        'https://cdn.datatables.net/1.11.5/js/dataTables.bootstrap4.min.js',

        'assets/authentication/js/star-rating/starrr.js',

        'https://cdn.jsdelivr.net/gh/kartik-v/bootstrap-fileinput@5.2.5/js/plugins/piexif.min.js',
        'https://cdn.jsdelivr.net/gh/kartik-v/bootstrap-fileinput@5.2.5/js/plugins/sortable.min.js',
        'https://cdn.jsdelivr.net/gh/kartik-v/bootstrap-fileinput@5.2.5/js/fileinput.min.js',

        'assets/authentication/js/active.js',

        'assets/authentication/js/script.js ',
      ];

      scriptAuth.forEach((script) => {
        const scriptAuth = this.renderer.createElement('script');
        scriptAuth.src = script;
        this.renderer.appendChild(document.body, scriptAuth);
      });
    } else if (this.user?.role === 'ADMIN') {
      this.renderer.setAttribute(link, 'href', 'assets/admin/css/style.css');

      const scriptAdmin = [
        'https://code.jquery.com/jquery-3.5.1.js',
        'assets/admin/js/vendor-all.min.js',
        'assets/admin/js/plugins/bootstrap.min.js',
        'assets/admin/js/pcoded.min.js',

        'assets/admin/js/plugins/apexcharts.min.js',

        'https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js',
        'https://cdn.datatables.net/1.11.5/js/dataTables.bootstrap4.min.js',

        'https://unpkg.com/sweetalert/dist/sweetalert.min.js',

        'https://cdn.jsdelivr.net/npm/flatpickr',
        'https://npmcdn.com/flatpickr/dist/l10n/vn.js',
        'https://unpkg.com/flatpickr@4.6.11/dist/plugins/rangePlugin.js',

        'https://cdn.jsdelivr.net/gh/kartik-v/bootstrap-fileinput@5.2.5/js/plugins/piexif.min.js',
        'https://cdn.jsdelivr.net/gh/kartik-v/bootstrap-fileinput@5.2.5/js/plugins/sortable.min.js',
        'https://cdn.jsdelivr.net/gh/kartik-v/bootstrap-fileinput@5.2.5/js/fileinput.min.js',

        'assets/admin/js/star-rating/starrr.js',

        'assets/admin/js/tag-input/tagsinput.js',

        'assets/admin/js/validation/bs4-form-validation.js',

        'https://unpkg.com/xlsx@0.15.1/dist/xlsx.full.min.js',
      ];

      scriptAdmin.forEach((script) => {
        const scriptAd = this.renderer.createElement('script');
        scriptAd.src = script;
        this.renderer.appendChild(document.body, scriptAd);
      });
    }

    this.renderer.appendChild(document.head, link);
  }
}
