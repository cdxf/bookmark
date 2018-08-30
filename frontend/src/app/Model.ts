export interface Category {
  id: number;
  name: string;
  description: string;
  order: number;
  posts: Post[];
}
export interface Post {
  id: number;
  name: string;
  url: string;
  description: string;
  order: number;
}
