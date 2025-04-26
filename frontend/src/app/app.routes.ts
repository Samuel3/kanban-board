import { Route } from '@angular/router';
import { OverviewComponent } from './overview/overview.component';
import { ListViewComponent } from './list-view/list-view.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';

export const appRoutes: Route[] = [
  {
    path: '',
    component: OverviewComponent,
    children: [
      {
        path: 'list',
        redirectTo: '',
      },
    ],
  },
  { path: 'list/:id', component: ListViewComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent }
];
