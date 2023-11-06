import { Component, Renderer2 } from '@angular/core';

@Component({
  selector: 'app-generic-assets',
  templateUrl: './generic-assets.component.html',
  styleUrls: ['./generic-assets.component.css']
})
export class GenericAssetsComponent {
  constructor(private renderer: Renderer2) { }

  ngOnInit(): void {
    this.initAssets();
  }

  initAssets() {
    const link = this.renderer.createElement('link');
    this.renderer.setAttribute(link, 'type', 'text/css');

    this.renderer.setAttribute(
      link,
      'href',
      'assets/global/css/style.css'
    );
    this.renderer.appendChild(document.head, link);
  }
}
