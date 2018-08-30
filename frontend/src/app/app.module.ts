import { BrowserModule } from '@angular/platform-browser';
import {APP_ID, Inject, NgModule, PLATFORM_ID} from '@angular/core';

import { AppComponent } from './app.component';
import {isPlatformBrowser} from '@angular/common';
import {HttpClientModule} from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { SpinnerComponent } from './spinner/spinner.component';
import {ReactiveFormsModule} from '@angular/forms';
import { FormPostComponent } from './form-post/form-post.component';
import { PostDetailComponent } from './post-detail/post-detail.component';
import { NotifyComponent } from './notify/notify.component';

@NgModule({
  declarations: [
    AppComponent,
    SpinnerComponent,
    FormPostComponent,
    PostDetailComponent,
    NotifyComponent
  ],
  imports: [
    BrowserModule.withServerTransition({ appId: 'javaurl' }),
    HttpClientModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
  constructor(
    @Inject(PLATFORM_ID) private platformId: Object,
    @Inject(APP_ID) private appId: string) {
    const platform = isPlatformBrowser(platformId) ?
      'in the browser' : 'on the server';
    console.log(`Running ${platform} with appId=${appId}`);
  }

}
