import {Component, Input, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, ValidationErrors, ValidatorFn, Validators} from '@angular/forms';
import {Post} from '../Model';

@Component({
  selector: 'app-form-post',
  templateUrl: './form-post.component.html',
  styleUrls: ['./form-post.component.css']
})
export class FormPostComponent implements OnInit {
  @Input() formGroup: FormGroup;

  constructor() {
  }
  static url(c: AbstractControl): ValidationErrors | null {
    try{
      const _ = new URL(c.value);
      return null;
    }
    catch (e) {
      return { url: 'Not a valid url' };
    }
  }
  static build(post: Post, fb: FormBuilder) {
    return fb.group({
      id: [post.id],
      url: [post.url, this.url],
      deleted: [false],
      name: [post.name, [Validators.required]],
      description: [post.description],
      order: [post.order]
    });
  }

  ngOnInit() {
  }

}
