import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Website } from '../common/website';

@Injectable({
  providedIn: 'root'
})
export class CrudService {

  private websiteUrl="http://localhost:8080/api/monitor-screen";

  constructor(private httpClient:HttpClient) { }

  addWebsiteName(urlStr:string):Observable<Website>{
    const url=`${this.websiteUrl}/add`;
    const body = new Website(null,urlStr,null);
    return this.httpClient.post<Website>(url,body);
  }

  getWebsites():Observable<Website[]>{
    const url=`${this.websiteUrl}/list`;
    return this.httpClient.get<Website[]>(url);
  }

}
