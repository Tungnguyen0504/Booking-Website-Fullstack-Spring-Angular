import { LiveAnnouncer } from '@angular/cdk/a11y';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import {
  Component,
  ElementRef,
  EventEmitter,
  Input,
  OnInit,
  Output,
  ViewChild,
  inject,
} from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { MatChipInputEvent } from '@angular/material/chips';
import { Observable, map, of, startWith } from 'rxjs';
import { PropertyConfigService } from 'src/app/service/property-config.service';

@Component({
  selector: 'app-custom-field-tag-input',
  templateUrl: './custom-field-tag-input.component.html',
  styleUrls: ['./custom-field-tag-input.component.css'],
})
export class CustomFieldTagInputComponent implements OnInit {
  @Input() requestForm!: FormGroup;
  @Input() property: string = '';
  @Input() txtLabel?: string;
  @Input() txtPlaceHolder?: string;
  @Input() isRequired?: boolean;
  @Output() eventEmitter: EventEmitter<string[]> = new EventEmitter<any>();

  form!: FormGroup;

  responseList: string[] = [];
  separatorKeysCodes: number[] = [ENTER, COMMA];
  filteredResponse?: Observable<string[]>;
  @ViewChild('tagInput')
  tagInput!: ElementRef<HTMLInputElement>;

  announcer = inject(LiveAnnouncer);

  constructor(
    private $formBuilder: FormBuilder,
    private $propertyConfigService: PropertyConfigService
  ) {}

  ngOnInit(): void {
    this.buildFormGroup();
    this.filteredResponse = this.$propertyConfigService.getContents(this.property);
  }

  buildFormGroup() {
    this.form = this.$formBuilder.group({
      tagInputCtrl: new FormControl(''),
      responseList: new FormControl([], this.isRequired ? Validators.required : null),
    });
  }

  add(event: MatChipInputEvent): void {
    const value = (event.value || '').trim().split('|');
    for (let i = 0; i < value.length; i++) {
      const strTemp = value[i].trim();
      if (strTemp && !this.responseList.includes(strTemp)) {
        this.responseList.push(strTemp);
        this.eventEmitter.emit(this.responseList);
      }
      event.chipInput!.clear();
      this.form?.get('tagInputCtrl')!.setValue(null);
    }
  }

  remove(fruit: string): void {
    const index = this.responseList.indexOf(fruit);
    if (index >= 0) {
      this.responseList.splice(index, 1);
      this.announcer.announce(`Removed ${fruit}`);
    }
  }

  selected(event: MatAutocompleteSelectedEvent): void {
    if (!this.responseList.includes(event.option.viewValue)) {
      this.responseList.push(event.option.viewValue);
      this.eventEmitter.emit(this.responseList);
    }
    this.tagInput.nativeElement.value = '';
    this.form?.get('tagInputCtrl')!.setValue(null);
  }
}
