import { Injectable } from '@angular/core';
import * as CryptoJS from 'crypto-js';
import { Buffer } from 'buffer';
@Injectable({
  providedIn: 'root'
})
export class LocalService {

  private readonly key = "OfsTWfuOh*LT5FkGt(DCY*WJG&p0M3DOTM8d";
  readonly SESSION = 'session'
  constructor() { }

  public saveData(key: string, value: string) {
    localStorage.setItem(key, this.encrypt(value));
  }

  public getData(key: string) {
    let data = localStorage.getItem(key) || "";
    return this.decrypt(data);
  }
  public removeData(key: string) {
    localStorage.removeItem(key);
  }

  public clearData() {
    localStorage.clear();
  }

  private encrypt(txt: string): string {
    return CryptoJS.AES.encrypt(txt, this.key).toString();
  }

  private decrypt(txtToDecrypt: string) {
    return CryptoJS.AES.decrypt(txtToDecrypt, this.key).toString(CryptoJS.enc.Utf8);
  }

  public asSession():boolean{
   return  this.expirateToken()
  }

  decodeToken(){
    try {
      const token = this.getData(this.SESSION);
      const buffer: any = Buffer.from(token.split('.')[1], 'base64');
      const decodedJwt = JSON.parse(buffer);
      return decodedJwt;
    } catch (e) {
      return null;
    }
  }

 private expirateToken = (exp?: any) => {
    try {
      const token = this.getData(this.SESSION);
      const buffer: any = Buffer.from(token.split('.')[1], 'base64');
      const decodedJwt = JSON.parse(buffer);
      if (exp) decodedJwt.exp = exp;
      if (decodedJwt.exp * 1000 < Date.now()) return false;
      else return true;
    } catch (e) {
      return false;
    }
  };

}