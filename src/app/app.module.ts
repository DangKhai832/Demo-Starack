import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';  // Đảm bảo import đúng AppRoutingModule
import { AppComponent } from './app.component';
import { RegisterComponent } from './register/register.component';
import { FormsModule } from "@angular/forms";
import { InputTextModule } from "primeng/inputtext";
import { PasswordModule } from "primeng/password";
import { ButtonModule } from "primeng/button";
import { RippleModule } from "primeng/ripple";
import { CheckboxModule } from "primeng/checkbox";
import { FloatLabelModule } from "primeng/floatlabel";
import { HttpClientModule } from "@angular/common/http";
import { ToastrModule } from "ngx-toastr";
import { LoginComponent } from './login/login.component';
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { NgxPaginationModule } from "ngx-pagination";
import { ManageComponent } from './manage/manage.component';
import { OverviewComponent } from './manage/overview/overview.component';
import { UserManagementComponent } from './manage/user-management/user-management.component';
import { TableModule } from "primeng/table";
import { DropdownModule } from "primeng/dropdown";
import { ChipModule } from "primeng/chip";
import { SidebarModule } from "primeng/sidebar";
import { PaginatorModule } from "primeng/paginator";
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { MessageService } from "primeng/api";
import { TabViewModule } from "primeng/tabview";

@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    LoginComponent,
    ManageComponent,
    OverviewComponent,
    UserManagementComponent,
  ],
  imports: [
    BrowserModule,
    InputTextModule,
    ConfirmDialogModule,
    PaginatorModule,
    BrowserAnimationsModule,
    PasswordModule,
    SidebarModule,
    ButtonModule,
    CheckboxModule,
    RippleModule,
    ToastrModule.forRoot(),
    HttpClientModule,
    NgxPaginationModule,
    AppRoutingModule,  // Đảm bảo import AppRoutingModule
    FloatLabelModule,
    FormsModule,
    TableModule,
    TabViewModule,
    ChipModule,
    DropdownModule,
  ],
  providers: [MessageService],
  bootstrap: [AppComponent]
})
export class AppModule { }
