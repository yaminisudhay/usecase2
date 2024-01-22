import { Component } from '@angular/core';
import { CrudService } from './service/crud.service';
import { Website } from './common/website';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'monitor-screen';
  webTxt='';
  addedRow:Website|undefined;
  newList:Website[]=[];
  inter: NodeJS.Timeout|undefined;
  
  constructor(private service:CrudService){}

  ngOnInit(){
    
    this.service.getWebsites().subscribe(
      data => {this.newList=data}
      );
    this.inter =setInterval(()=>{
      //console.log("before subs");
      this.service.getWebsites().subscribe(
        data => {this.newList=data});
        //console.log("data "+this.newList);
    },2*60*1000);
  }

  ngOnDestroy(){
    if(this.inter)
      clearInterval(this.inter);
  }

  addDetail(){
    this.service.addWebsiteName(this.webTxt).subscribe(
      data=> {
        this.addedRow=data;
        this.newList[this.newList.length]=this.addedRow;
      }
    );
  }
    
}
