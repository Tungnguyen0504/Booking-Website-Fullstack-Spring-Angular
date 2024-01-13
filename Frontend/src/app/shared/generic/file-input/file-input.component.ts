import { Component, EventEmitter, OnInit, Output } from '@angular/core';

declare var $: any;

@Component({
  selector: 'app-file-input',
  templateUrl: './file-input.component.html',
  styleUrls: ['./file-input.component.css'],
})
export class FileInputComponent implements OnInit {
  @Output() eventEmitter: EventEmitter<FileList> = new EventEmitter<any>();

  ngOnInit(): void {
    setTimeout(() => {
      $('.file-input').fileinput({
        allowedFileTypes: ['image'],
        initialPreviewAsData: true,
        showUpload: false,
        showCancel: false,
      });
    }, 500);
  }

  onFileChange(event: any) {
    this.eventEmitter.emit(event.target.files);
  }
}
