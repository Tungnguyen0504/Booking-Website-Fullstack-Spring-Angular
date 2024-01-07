import { Component, Renderer2 } from '@angular/core';

@Component({
  selector: 'app-generic-assets',
  templateUrl: './generic-assets.component.html',
  styleUrls: ['./generic-assets.component.css'],
})
export class GenericAssetsComponent {
  constructor(private renderer: Renderer2) {}

  ngOnInit(): void {
    this.initAssets();
  }

  initAssets() {
    const link = this.renderer.createElement('link');
    this.renderer.setAttribute(link, 'rel', 'stylesheet');
    this.renderer.setAttribute(link, 'type', 'text/css');

    this.renderer.setAttribute(link, 'href', 'assets/css/style.css');
    this.renderer.appendChild(document.head, link);

    const script = [
      'assets/js/plugins/plugins.js',

      'https://cdnjs.cloudflare.com/ajax/libs/lightgallery/2.5.0-beta.2/lightgallery.umd.min.js',
      'https://cdnjs.cloudflare.com/ajax/libs/lightgallery/2.5.0-beta.2/lightgallery.min.js',
      'https://cdnjs.cloudflare.com/ajax/libs/lightgallery/2.5.0-beta.2/plugins/thumbnail/lg-thumbnail.min.js',

      'assets/js/input-spinner-bootstrap/bootstrap-input-spinner.js ',

      'assets/js/active.js',

      'assets/js/script.js ',
    ];

    script.forEach((script) => {
      const scriptAuth = this.renderer.createElement('script');
      scriptAuth.src = script;
      this.renderer.appendChild(document.body, scriptAuth);
    });
  }
}
