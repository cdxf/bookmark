import {Component, Input, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {delay, tap} from 'rxjs/operators';

@Component({
  selector: 'app-notify',
  templateUrl: './notify.component.html',
  styleUrls: ['./notify.component.css']
})
export class NotifyComponent implements OnInit {

  constructor() {
  }
  public show = false;
  @Input() change: Observable<string>;

  ngOnInit() {
    this.change
      .pipe(
        tap(() => this.show = true),
        delay(1000),
        tap(() => this.show = false)).subscribe();
  }

}
