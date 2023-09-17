import { Component, OnInit, Renderer2 } from '@angular/core';

@Component({
  selector: 'app-user-assets',
  templateUrl: './user-assets.component.html',
  styleUrls: ['./user-assets.component.css'],
})
export class UserAssetsComponent implements OnInit {
  constructor(private renderer: Renderer2) {}

  ngOnInit(): void {
    this.initAssets();
  }

  initAssets() {
    const link = this.renderer.createElement('link');
    this.renderer.setAttribute(link, 'rel', 'stylesheet');
    this.renderer.setAttribute(link, 'type', 'text/css');

    this.renderer.setAttribute(
      link,
      'href',
      'assets/authentication/css/style.css'
    );
    this.renderer.appendChild(document.head, link);

    const scriptAuth = [
      'assets/authentication/js/bootstrap/popper.min.js',
      'https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js',
      'assets/authentication/js/bootstrap/bootstrap.min.js',

      'assets/authentication/js/plugins/plugins.js',

      'https://cdn.jsdelivr.net/npm/flatpickr',
      'https://npmcdn.com/flatpickr/dist/l10n/vn.js',
      'https://unpkg.com/flatpickr@4.6.11/dist/plugins/rangePlugin.js',

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
  }
}
