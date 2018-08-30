import {Component, Input, OnInit} from '@angular/core';
import {Category} from '../Model';

@Component({
  selector: 'app-post-detail',
  templateUrl: './post-detail.component.html',
  styleUrls: ['./post-detail.component.css']
})
export class PostDetailComponent implements OnInit {
  @Input() categories: Category[];
  constructor() { }
  getNotDeletedPost(posts) {
    return posts.filter(it => !it.deleted);
  }
  ngOnInit() {
  }

}
