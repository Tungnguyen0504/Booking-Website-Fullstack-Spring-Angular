import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Observable } from 'rxjs';

declare var $: any;

@Component({
  selector: 'app-file-input',
  templateUrl: './file-input.component.html',
  styleUrls: ['./file-input.component.css'],
})
export class FileInputComponent implements OnInit {
  @Input() multiple: boolean = false;
  @Input() filePaths: string[] = [];
  @Output() eventEmitter: EventEmitter<File[]> = new EventEmitter<File[]>();

  ngOnInit(): void {
    $('.file-input').fileinput({
      allowedFileTypes: ['image'],
      initialPreviewAsData: true,
      showUpload: false,
      showCancel: false,
      initialPreview: this.filePaths,
    });
  }

  onFileChange($event: any) {
    var fileList = [];
    for (let i = 0; i < $event.target.files.length; i++) {
      fileList.push($event.target.files[i]);
    }
    this.eventEmitter.emit(fileList);
  }
}
