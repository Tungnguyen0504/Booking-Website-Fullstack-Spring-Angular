import { LiveAnnouncer } from '@angular/cdk/a11y';
import { Component, ElementRef, OnInit, ViewChild, inject } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { MatChipInputEvent } from '@angular/material/chips';
import { Observable, startWith, map } from 'rxjs';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { AddressService } from 'src/app/service/address.service';
import axios from 'axios';

@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.css'],
})

export class TestComponent implements OnInit {
  public testForm: FormGroup = {} as FormGroup;

  separatorKeysCodes: number[] = [ENTER, COMMA];
  filteredFruits: Observable<string[]>;
  fruits: string[] = ['Lemon'];
  allFruits: string[] = ['Apple', 'Lemon', 'Lime', 'Orange', 'Strawberry'];

  @ViewChild('fruitInput') fruitInput!: ElementRef<HTMLInputElement>;

  announcer = inject(LiveAnnouncer);

  constructor(
    private $formBuilder: FormBuilder,
    private $addressService: AddressService
  ) {
    this.testForm = this.$formBuilder.group({
      fruitCtrl: [""],
      fruits: [{}]
    });

    this.filteredFruits = this.testForm.get("fruitCtrl")!.valueChanges.pipe(
      startWith(null),
      map((fruit: string | null) =>
        fruit ? this._filter(fruit) : this.allFruits.slice()
      )
    );

    axios.get("https://raw.githubusercontent.com/kenzouno1/DiaGioiHanhChinhVN/master/data.json")
      .then(response => {
        console.log(response.data);
      })
      .catch(error => {
        console.log(error);
      });
  }

  ngOnInit(): void {

  }

  add(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();

    // Add our fruit
    if (value && !this.fruits.includes(value)) {
      this.fruits.push(value);
      this.testForm.get("fruits")!.setValue(this.fruits);
    }

    // Clear the input value
    event.chipInput!.clear();

    this.testForm.get("fruitCtrl")!.setValue(null);
  }

  remove(fruit: string): void {
    const index = this.fruits.indexOf(fruit);

    if (index >= 0) {
      this.fruits.splice(index, 1);
      this.testForm.get("fruits")!.setValue(this.fruits);

      this.announcer.announce(`Removed ${fruit}`);
    }
  }

  selected(event: MatAutocompleteSelectedEvent): void {
    if (!this.fruits.includes(event.option.viewValue)) {
      this.fruits.push(event.option.viewValue);
      this.testForm.get("fruits")!.setValue(this.fruits);
    }
    this.fruitInput.nativeElement.value = '';
    this.testForm.get("fruitCtrl")!.setValue(null);
  }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();

    return this.allFruits.filter((fruit) =>
      fruit.toLowerCase().includes(filterValue)
    );
  }

  submitForm() {
    console.log(this.testForm.value);
  }
}
