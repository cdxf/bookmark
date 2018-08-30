import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Category, Post} from './Model';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private httpClient: HttpClient) {
  }

  public isCatChanged(oldCat: Category, newCat: Category) {
    if (oldCat.id !== newCat.id) { return true; }
    if (oldCat.name !== newCat.name) { return true; }
    if (oldCat.description !== newCat.description) { return true; }
    return false;
  }

  public isPostChanged(oldPost: Post, newPost: Post) {
    if (oldPost.id !== newPost.id) { return true; }
    if (oldPost.name !== newPost.name) { return true; }
    if (oldPost.description !== newPost.description) { return true; }
    if (oldPost.url !== newPost.url) { return true; }

    return false;
  }
  public getPosts() {
    return this.httpClient.get('http://localhost:8080');
  }
  public changed(category: Category[]) {
    return this.httpClient.post('http://localhost:8080/populate', category);
  }
}
