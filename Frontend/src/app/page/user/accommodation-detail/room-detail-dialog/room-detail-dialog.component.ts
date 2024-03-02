import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Room } from 'src/app/model/Room.model';
import { FileService } from 'src/app/service/file.service';

interface DialogData {
  room: Room;
}

@Component({
  selector: 'app-room-detail-dialog',
  templateUrl: './room-detail-dialog.component.html',
  styleUrls: ['./room-detail-dialog.component.css'],
})
export class RoomDetailDialogComponent implements OnInit {

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
    private dialogRef: MatDialogRef<RoomDetailDialogComponent>,
    private $fileService: FileService
  ) {
    console.log(data);
  }

  ngOnInit(): void {}
}
