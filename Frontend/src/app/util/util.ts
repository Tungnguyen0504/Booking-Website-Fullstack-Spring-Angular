import * as moment from 'moment';
import { DATETIME_FORMAT1 } from '../constant/Abstract.constant';
import { FileDto } from '../model/FileDto.model';

export class Util {
  public static setLocal(key: string, value: any, expiredTime: number) {
    if (key && value) {
      const item = {
        value: value,
        expiration: this.formatDate(this.parseDate1(Date.now() + expiredTime), DATETIME_FORMAT1),
      };
      localStorage.setItem(key, JSON.stringify(item));
    }
  }

  public static getLocal(key: string) {
    const item = localStorage.getItem(key);
    if (item) {
      const parsedItem = JSON.parse(item);
      const milliseconds = this.toMilliseconds(
        this.parseDate2(parsedItem.expiration, DATETIME_FORMAT1)
      );
      if (Object.keys(parsedItem).length == 0 || (milliseconds && milliseconds < Date.now())) {
        localStorage.removeItem(key);
        return null;
      }
      return parsedItem.value;
    }
    return null;
  }

  public static removeLocal(key: string): void {
    localStorage.removeItem(key);
  }

  public static parseDate1(milliseconds: number): Date {
    return moment(milliseconds).toDate();
  }

  public static parseDate2(dateStr: string, pattern: string): Date {
    return moment(dateStr, pattern).toDate();
  }

  public static formatDate(date: Date, pattern: string) {
    return moment(date).format(pattern);
  }

  public static toMilliseconds(date: Date) {
    return moment(date).valueOf();
  }

  public static subtractDate1(var1: Date, var2: Date) {
    const momentDate1 = moment(var1);
    const momentDate2 = moment(var2);
    return momentDate1.diff(momentDate2, 'days');
  }

  public static subtractDate2(var1: Date, num: number) {
    return moment(var1).subtract(2, 'days');;
  }

  public static filterActive() {
    return {
      key: 'status',
      values: ['ACTIVE'],
      operator: 'EQUAL',
      fieldType: 'STRING',
    };
  }

  public static getFileImages(obj: any) {
    return obj.files
      .filter((file: any) => file.fileType === 'image/jpeg')
      .map((file: any) => file.base64String);
  }

  public static convertBase64ToFileList(files: FileDto[]): File[] {
    var fileList = [];
    for (let i = 0; i < files.length; i++) {
      const base64Data = files[i].base64String;
      const byteString = base64Data.replace('data:image/jpeg;base64,', '');
      const byteCharacters = atob(byteString);
      const byteNumbers = new Array(byteCharacters.length);
      for (let i = 0; i < byteCharacters.length; i++) {
        byteNumbers[i] = byteCharacters.charCodeAt(i);
      }
      const byteArray = new Uint8Array(byteNumbers);
      const blob = new Blob([byteArray], { type: 'image/jpeg' });
      const file = new File([blob], files[i].fileName);
      fileList.push(file);
    }
    return fileList;
  }
}
