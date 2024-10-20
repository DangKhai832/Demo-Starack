import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {ManageService} from "../manage.service";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-user-management',
  templateUrl: './user-management.component.html',
  styleUrls: ['./user-management.component.css']
})
export class UserManagementComponent implements OnInit {
  users: any[] = [];
  totalItems: number = 0;
  loading: boolean = true;
  searchKeyword: any = '';
  sidebarVisible: boolean = false;
  newMember: any = {};
  isEditing: boolean = false;

  constructor(
    private http: HttpClient,
    private manageService: ManageService,
    private toastr: ToastrService
  ) {}

  first : any = 0;
  rows : any = 10
  ngOnInit(): void {
    this.loadUsers(0);
  }

  showSidebar(isEdit: boolean = false, userData: any = null) {
    this.sidebarVisible = true;

    if (isEdit && userData) {
      this.isEditing = true
      this.newMember = {
        id: userData.id,
        email: userData.email,
        fullName: userData.fullName,
        status: userData.status,
        role : userData.role
      };
    } else {
      this.isEditing = false
      this.newMember = {status: 'WAITING',
        role: 'Member'};
    }
  }

  updateStatus(status: string) {
    this.newMember.status = status;
  }


  loadUserData(userId: string) {
    // Giả sử bạn có một phương thức để lấy dữ liệu người dùng từ API
    this.manageService.getUserById(userId).subscribe(user => {
      this.newMember = {
        id: user.id,
        fullName: user.fullName,
        email: user.email,
        status: user.status,
      };
    });
  }

  addMember() {
    if (!this.newMember.fullName || !this.newMember.email || !this.newMember.password || !this.newMember.confirmPassword || !this.newMember.status) {
      this.toastr.error('Vui lòng nhập đầy đủ thông tin!');
      return; // Dừng lại nếu có trường chưa nhập
    }

    // Kiểm tra xem password và confirm password có trùng nhau không
    if (this.newMember.password !== this.newMember.confirmPassword) {
      this.toastr.error('Mật khẩu và xác nhận mật khẩu không trùng khớp!');
      return; // Dừng lại nếu mật khẩu không khớp
    }
    this.manageService.createUser(this.newMember).subscribe(
      response => {
        console.log('User added successfully!', response);
        this.toastr.success('User added successfully!');
        this.loadUsers(0);
        this.sidebarVisible = false;
      },
      error => {
        debugger
        this.toastr.error(error.error);
      }
    );
  }

  loadUsers(event : any) {
    if (typeof event === 'object' && event !== null) {
      this.first = event.first;
      this.rows = event.rows;
    } else {
      this.first = 0;
      this.rows = 10;
    }
    this.loading = true;
    const page = this.first / this.rows;
    const size = this.rows;

    // Giả sử bạn không cần tìm kiếm, truyền null nếu không có dữ liệu
    const fullName = this.searchKeyword; // Hoặc lấy từ input nào đó
    this.manageService.getDataUserManagement(fullName, page, size).subscribe(
      (response: { users: any[], totalItems: number }) => {
        this.users = response.users; // Lưu danh sách người dùng
        this.totalItems = response.totalItems; // Lưu tổng số người dùng
        this.loading = false; // Đánh dấu là đã tải xong
      },
      error => {
        this.toastr.error(error.error, 'Error!');
        this.loading = false; // Đánh dấu là đã tải xong ngay cả khi có lỗi
      }
    );
  }
  resetForm() {
    this.newMember = {}; // Reset form khi sidebar đóng
    this.isEditing = false; // Đặt lại chế độ chỉnh sửa
  }
  updateUser() {
    debugger
    if (!this.newMember.fullName || !this.newMember.email || !this.newMember.role || !this.newMember.status) {
      this.toastr.error('Vui lòng nhập đầy đủ thông tin!');
      return; // Dừng lại nếu có trường chưa nhập
    }

    this.manageService.updateUser(this.newMember.id, this.newMember).subscribe(
      response => {
        this.toastr.success('Cập nhật thành công!', 'Thành công!');
        this.loadUsers(0); // Tải lại danh sách người dùng
        this.sidebarVisible = false; // Đóng sidebar
      },
      error => {
        this.toastr.error(error.error);
      }
    );
  }

  shortenId(id: string): string {
    return id.length > 10 ? id.substring(0, 10) + '...' : id;
  }
  viewSidebarVisible: boolean = false;

  selectedUser: any
  trackLogData: any[] = []
  viewUser(data: any): void {
    this.selectedUser = data ? { ...data } : { id: '', email: '', fullName: '', role: '', status: '' };
    this.manageService.getTrackLogById(data.id).subscribe(
      response => {
        this.trackLogData = response;
        console.log(this.trackLogData); // Kiểm tra giá trị của trackLogData
      },
      error => {
        this.toastr.error('Lỗi khi lấy tracklog.');
      }
    );
    this.viewSidebarVisible = true;
  }
  closeViewSidebar() {
    this.viewSidebarVisible = false;
    this.selectedUser = null;
  }

  copyId(id: string): void {
    navigator.clipboard.writeText(id).then(() => {
      this.toastr.success("Copy ID successful!")
    }, (err) => {
      this.toastr.error("Get ID fail!")
    });
  }

  showConfirmDialog: boolean = false; // Hiển thị modal xác nhận
  userId: any; // ID người dùng cần xóa
  deleteUser(id: any) {
    debugger
    this.userId = id; // Lưu ID người dùng cần xóa
    this.showConfirmDialog = true; // Hiển thị modal xác nhận
  }

  confirmDelete(id: any) {
    this.manageService.deleteUser(id).subscribe(
      response => {
        this.toastr.success('Xóa người dùng thành công!');
        this.loadUsers(0); // Tải lại danh sách người dùng
        this.showConfirmDialog = false; // Ẩn modal xác nhận
      },
      error => {
        // Kiểm tra kỹ phản hồi lỗi
        if (error.error && error.error.message) {
          this.toastr.error(error.error.message); // Hiển thị thông báo lỗi chi tiết
        } else {
          this.toastr.error('Xảy ra lỗi khi xóa người dùng.');
        }
      }
    );
  }


  closeConfirmDialog() {
    this.showConfirmDialog = false; // Ẩn modal xác nhận
  }
}
