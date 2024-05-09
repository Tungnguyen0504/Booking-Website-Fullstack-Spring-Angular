import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

declare var $: any;

@Component({
  selector: 'app-file-input',
  templateUrl: './file-input.component.html',
  styleUrls: ['./file-input.component.css'],
})
export class FileInputComponent implements OnInit {
  @Input() multiple: boolean = false;
  @Output() eventEmitter: EventEmitter<FileList> = new EventEmitter<FileList>();

  ngOnInit(): void {
    $('.file-input').fileinput({
      allowedFileTypes: ['image'],
      initialPreviewAsData: true,
      showUpload: false,
      showCancel: false,
    });
  }

  onFileChange(event: any) {
    this.eventEmitter.emit(event.target.files);
  }
}
