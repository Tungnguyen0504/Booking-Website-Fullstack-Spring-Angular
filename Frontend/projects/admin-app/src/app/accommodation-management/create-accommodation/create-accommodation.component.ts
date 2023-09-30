import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Accommodation } from 'src/app/model/Accommodation.model';
import { City } from 'src/app/model/City.model';
import { CityService } from 'src/app/service/city.service';

declare var $: any;

@Component({
  selector: 'app-create-accommodation',
  templateUrl: './create-accommodation.component.html',
  styleUrls: ['./create-accommodation.component.css'],
})
export class CreateAccommodationComponent implements OnInit {
  @ViewChild('multiSelect') multiSelect: any;
  @ViewChild('form', { static: false }) form: NgForm = {} as NgForm;

  cities: City[] = [];
  settings = {};
  selectedItems: any[] = [];

  accommodation: Accommodation = {} as Accommodation;

  constructor(private cityService: CityService) {
    this.cityService.getTopCity(100).subscribe((response) => {
      this.cities = response;
    });
    console.log(this.cities);
  }

  ngOnInit(): void {


    this.settings = {
      singleSelection: false,
      idField: 'id',
      textField: 'cityName',
      enableCheckAll: true,
      selectAllText: 'Chọn All',
      unSelectAllText: 'Hủy chọn',
      allowSearchFilter: true,
      limitSelection: -1,
      clearSearchFilter: true,
      maxHeight: 197,
      itemsShowLimit: 3,
      searchPlaceholderText: 'Tìm kiếm',
      noDataAvailablePlaceholderText: 'Không có dữ liệu',
      closeDropDownOnSelection: false,
      showSelectedItemsAtTop: false,
      defaultOpen: false,
    };

    this.initComponentJquery();
  }

  initComponentJquery() {
    $('input[name="image"]').fileinput({
      allowedFileTypes: ['image'],
      initialPreviewAsData: true,
      showUpload: false,
      showCancel: false,
    });

    // setting and support i18n
  }

  public resetForm() {
    // beacuse i need select all crickter by default when i click on reset button.
    this.multiSelect.toggleSelectAll();
    // i try below variable isAllItemsSelected reference from your  repository but still not working
    // this.multiSelect.isAllItemsSelected = true;
  }

  public onFilterChange(item: any) {
    console.log(item);
  }
  public onDropDownClose(item: any) {
    console.log(item);
  }

  public onItemSelect(item: any) {
    console.log(item);
  }
  public onDeSelect(item: any) {
    console.log(item);
  }

  public onSelectAll(items: any) {
    console.log(items);
  }
  public onDeSelectAll(items: any) {
    console.log(items);
  }

  create() {
    console.log(this.selectedItems);
    console.log(this.form?.value);
  }
}
