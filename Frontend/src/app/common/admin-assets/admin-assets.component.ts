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
      'assets/admin/js/vendor-all.min.js',
      'assets/admin/js/pcoded.min.js',

      'assets/admin/js/plugins/apexcharts.min.js',

      'assets/admin/js/tag-input/tagsinput.js',

      'https://unpkg.com/xlsx@0.15.1/dist/xlsx.full.min.js',
      
      "assets/global/js/star-rating/starrr.js",

      "assets/global/js/multi-select/jquery.multiselect.js"
    ];

    scriptAdmin.forEach((script) => {
      const scriptAd = this.renderer.createElement('script');
      scriptAd.src = script;
      this.renderer.appendChild(document.body, scriptAd);
    });
  }
}
