import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RegisterComponent } from "./register/register.component";
import { LoginComponent } from "./login/login.component";
import { ManageComponent } from "./manage/manage.component";
import { UserManagementComponent } from "./manage/user-management/user-management.component";
import { OverviewComponent } from "./manage/overview/overview.component";
import { AuthGuard } from "./auth.guard";

const routes: Routes = [
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  {
    path: 'manage',
    component: ManageComponent,
    canActivate: [AuthGuard],
    children: [
      { path: 'user-management', component: UserManagementComponent },
      { path: 'overview', component: OverviewComponent },
    ]
  },
  { path: '', redirectTo: '/login', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
