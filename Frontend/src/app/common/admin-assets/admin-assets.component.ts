import { Component, OnInit, Renderer2 } from '@angular/core';

@Component({
  selector: 'app-admin-assets',
  templateUrl: './admin-assets.component.html',
  styleUrls: ['./admin-assets.component.css'],
})
export class AdminAssetsComponent implements OnInit {
  constructor(private renderer: Renderer2) {}

  ngOnInit(): void {
    this.initAssets();
  }

  initAssets() {
    const link = this.renderer.createElement('link');
    this.renderer.setAttribute(link, 'rel', 'stylesheet');
    this.renderer.setAttribute(link, 'type', 'text/css');
    this.renderer.setAttribute(link, 'href', 'assets/admin/css/style.css');
    this.renderer.appendChild(document.head, link);

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
}
