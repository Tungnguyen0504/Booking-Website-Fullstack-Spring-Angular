export function setLocal(key: string, value: any): void {
  localStorage.setItem(key, JSON.stringify(value));
}

export function removeLocal(key: string) {
  localStorage.removeItem(key);
}
