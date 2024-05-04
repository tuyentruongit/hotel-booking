const mySetting = document.querySelector('.my-setting-container')


const renderEditProfile = () => {
    mySetting.innerHTML = `
                    <div class="information-user">
                    <div class="row">
                        <div class="col-10">
                            <h3 class="m-0">Thông tin cơ bản</h3>
                            <span>Cập nhật thông tin của bạn và tìm hiểu các thông tin này được sử dụng ra sao.</span>
                           
                        </div>
                    </div>
                    <hr>
                   
                   <div class="edit-name-user">
                        <div class="row">
                            <div class="col-3">
                                <span class="w-100">Tên</span>
                            </div>
                            <div class="col-6 d-flex">
                                    <div class="edit-name">
                                         <h6>Tên</h6>
                                         <input  type="text" placeholder="Nhập tên">
                                    </div>
                                     <div class="edit-name">
                                         <h6>Họ</h6>
                                         <input type="text" placeholder="Nhập tên">
                                    </div>
                            </div>
                            
                        </div>
                   </div>
                    <hr>
                  
                   <div class="edit-email-user">
                        <div class="row">
                            <div class="col-3">
                                <span class="w-100">Địa chỉ email</span>
                            </div>
                            <div class="col-6 d-flex">
                                     <div class="edit-email">
                                         <input type="text" placeholder="Nhập email">
                                    </div>
                            
                            </div>
                            
                        </div>
                   </div>
                    <hr>
                   
                   <div class="edit-phone-number-user">
                        <div class="row">
                            <div class="col-3">
                                <span class="w-100">Số điện thoại</span>
                            </div>
                            <div class="col-6 d-flex">
                                    <div class="edit-phone-number">
                                         <input  type="text" placeholder="Nhập tên">
                                    </div>
                            </div>
                           
                        </div>
                   </div>
                    <hr>
                 
                   <div class="edit-birthday-user">
                        <div class="row">
                            <div class="col-3">
                                <span class="w-100">Ngày sinh</span>
                            </div>
                            <div class="col-6 d-flex">
                                    <div class="edit-phone-number">
                                         <input  type="date" placeholder="Nhập tên">
                                    </div>
                            </div>
                           
                        </div>
                   </div>
                    <hr>
                    
                   <div class="edit-gender-user">
                        <div class="row">
                            <div class="col-3">
                                <span class="w-100">Giới tính</span>
                            </div>
                            <div class="col-6 d-flex">
                                    <div class="edit-gender-number">
                                         <select name="" id="">
                                            <option value="Nam">Nam</option>
                                            <option value="Nữ">Nữ</option>
                                            <option value="Khác">Khác</option>
                                         </select>
                                    </div>
                            </div>
                            
                        </div>
                   </div>
                   <hr>
                  
                   <div class="edit-address-user">
                        <div class="row">
                            <div class="col-3">
                                <span class="w-100">Địa chỉ</span>
                            </div>
                            <div class="col-6 d-flex">
                                    <div class="edit-address-number">
                                       <input type="text" placeholder="Nhập địa chỉ">
                                    </div>
                            </div>
                           
                        </div>
                   </div>
                   </div>
                   
                    <div class="d-flex justify-content-center">
                        <button class="cancel">Hủy</button>
                        <button class="btn-save">Lưu lại</button>
                    </div>
                    
                    `

}


const renderSetting = () => {
    mySetting.innerHTML = `
                    <div class="setting-account">
                    <div class="row">
                        <div class="col-10">
                            <h3 class="m-0">Thiết lập</h3>
                            <span>Thay đổi thiết lập bảo mật hoặc xóa tài khoản của Quý vị.</span>
                        </div>
                    </div>
                    <hr>
                    <div class="row">
                        <div class="col-2">
                            <span class="name w-100">Mật khẩu</span>
                        </div>
                        <div class="col-7">Để thay đổi mật khẩu, chúng tôi cần gửi link tạo lại mật khẩu đến địa chỉ email của bạn</div>
                        <div class="col-3 d-flex justify-content-center">
                            <button  type="button" class="btn-change-password" data-bs-toggle="modal" data-bs-target="#exampleModal">Đổi mật khẩu</button>
                        </div>
                    </div>
                    <hr>
                    <div class="row">
                        <div class="col-2">
                                <span class="name w-100">Xóa tài khoản</span>
                        </div>
                        <div class="col-7">Xóa tài khoản STAYESEA vĩnh viễn</div>
                        <div class="col-3 d-flex justify-content-center">
                            <button class="">Xóa tài khoản</button>
                        </div>
                    </div>
                    </div>
                    `
}

renderEditProfile()


const btnSave = document.querySelectorAll('.btn-save');
console.log(btnSave)





