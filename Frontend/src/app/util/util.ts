import * as moment from 'moment';

export class Util {
  public static setLocal(key: string, value: any, expiredTime: number) {
    if (key && value) {
      const item = {
        value: value,
        expiration: Date.now() + expiredTime,
      };
      localStorage.setItem(key, JSON.stringify(item));
    }
  }

  public static getLocal(key: string) {
    const item = localStorage.getItem(key);
    if (item) {
      const parsedItem = JSON.parse(item);
      if (
        Object.keys(parsedItem).length == 0 ||
        (parsedItem.expiration && parsedItem.expiration < Date.now())
      ) {
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

  public static parseDate(dateStr: string, pattern: string): Date {
    return moment(dateStr, pattern).toDate();
  }

  public static formatDate(date: Date, pattern: string) {
    return moment(date).format(pattern);
  }

  public static subtractDate(var1: Date, var2: Date) {
    const momentDate1 = moment(var1);
    const momentDate2 = moment(var2);
    return momentDate1.diff(momentDate2, 'days');
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
