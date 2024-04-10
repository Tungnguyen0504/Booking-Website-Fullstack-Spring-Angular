import { DatePipe } from '@angular/common';
import { Injectable } from '@angular/core';
import * as moment from 'moment';

export class Util {
  constructor(private datePipe: DatePipe) {}

  public static setLocal(key: string, value: any): void {
    localStorage.setItem(key, JSON.stringify(value));
  }

  public static getLocal(key: string): any {
    const data = localStorage.getItem(key);
    if (data) {
      return JSON.parse(data);
    }
    return {};
  }

  public static removeLocal(key: string): void {
    localStorage.removeItem(key);
  }

  public static parseDate(dateStr: string, pattern: string): Date {
    return moment(dateStr, pattern).toDate();
  }

  public static formatDate(date: Date, pattern: string) {
    const datePipe = new DatePipe('en-US');
    return datePipe.transform(date, pattern);
  }

  public static filterActive() {
    return {
      key: 'status',
      values: ['ACTIVE'],
      operator: 'EQUAL',
      fieldType: 'STRING',
    };
  }
}
