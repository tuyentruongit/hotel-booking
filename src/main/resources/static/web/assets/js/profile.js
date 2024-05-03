const mySetting = document.querySelector('.my-setting-container')


const renderEditProfile = () => {
    mySetting.innerHTML = `
                    <div class="row">
                        <div class="col-10">
                            <h3 class="m-0">Thông tin cơ bản</h3>
                            <span>Cập nhật thông tin của bạn và tìm hiểu các thông tin này được sử dụng ra sao.</span>
                           
                        </div>
                    </div>
                    <hr>
                   <div class="show-name-user ">
                        <div class="row">
                            <div class="col-3">
                                <span class="w-100">Tên</span>
                            </div>
                            <div class="col-6">Truong van Tuyen</div>
                            <div class="col-3 d-flex justify-content-center">
                                <button class="btn-edit" edit-for="name">Chỉnh sửa</button>
                            </div>
                        </div>
                   </div>
                   <div class="edit-name-user disable-edit">
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
                            <div class="col-3 d-flex justify-content-center">
                                <button class="btn-save" save-for="name">Lưu lại</button>
                            </div>
                        </div>
                   </div>
                    <hr>
                   <div class="show-email-user">
                        <div class="row">
                            <div class="col-3">
                                <span class="w-100">Địa chỉ email</span>
                            </div>
                            <div class="col-6">truongvantuyen@gmail.com</div>
                            <div class="col-3 d-flex justify-content-center">
                                <button class="btn-edit" edit-for="email">Chỉnh sửa</button>
                            </div>
                        </div>
                   </div>
                   <div class="edit-email-user disable-edit">
                        <div class="row">
                            <div class="col-3">
                                <span class="w-100">Địa chỉ email</span>
                            </div>
                            <div class="col-6 d-flex">
                                     <div class="edit-email">
                                         <input type="text" placeholder="Nhập email">
                                    </div>
                            
                            </div>
                            <div class="col-3 d-flex justify-content-center">
                                <button class="btn-save" save-for="email">Lưu lại</button>
                            </div>
                        </div>
                   </div>
                    <hr>
                   <div class="show-phone-number-user">
                        <div class="row">
                            <div class="col-3">
                                <span class="w-100">Số điện thoại</span>
                            </div>
                            <div class="col-6">0123456789</div>
                            <div class="col-3 d-flex justify-content-center">
                                <button class="btn-edit" edit-for="phone-number">Chỉnh sửa</button>
                            </div>
                        </div>
                   </div>
                   <div class="edit-phone-number-user disable-edit">
                        <div class="row">
                            <div class="col-3">
                                <span class="w-100">Số điện thoại</span>
                            </div>
                            <div class="col-6 d-flex">
                                    <div class="edit-phone-number">
                                         <input  type="text" placeholder="Nhập tên">
                                    </div>
                            </div>
                            <div class="col-3 d-flex justify-content-center">
                                <button class="btn-save" save-for="phone-number">Lưu lại</button>
                            </div>
                        </div>
                   </div>
                    <hr>
                   <div class="show-birthday-user ">
                        <div class="row">
                            <div class="col-3">
                                <span class="w-100">Ngày sinh</span>
                            </div>
                            <div class="col-6">23/10/1001</div>
                            <div class="col-3 d-flex justify-content-center">
                                <button class="btn-edit" edit-for="birthday"">Chỉnh sửa</button>
                            </div>
                        </div>
                   </div>
                   <div class="edit-birthday-user disable-edit">
                        <div class="row">
                            <div class="col-3">
                                <span class="w-100">Ngày sinh</span>
                            </div>
                            <div class="col-6 d-flex">
                                    <div class="edit-phone-number">
                                         <input  type="date" placeholder="Nhập tên">
                                    </div>
                            </div>
                            <div class="col-3 d-flex justify-content-center">
                                <button class="btn-save" save-for="birthday">Lưu lại</button>
                            </div>
                        </div>
                   </div>
                    <hr>
                    <div class="show-gender-user">
                        <div class="row">
                            <div class="col-3">
                                <span class="w-100">Giới tính</span>
                            </div>
                            <div class="col-6">Nam</div>
                            <div class="col-3 d-flex justify-content-center">
                                <button class="btn-edit" edit-for="gender">Chỉnh sửa</button>
                            </div>
                        </div>
                   </div>
                   <div class="edit-gender-user disable-edit">
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
                            <div class="col-3 d-flex justify-content-center">
                                <button class="btn-save" save-for="gender">Lưu lại</button>
                            </div>
                        </div>
                   </div>
                   <hr>
                   <div class="show-address-user">
                        <div class="row">
                            <div class="col-3">
                                <span class="w-100">Địa chỉ</span>
                            </div>
                            <div class="col-6">Xuân khê , Lý Nhân , Hà Nam</div>
                            <div class="col-3 d-flex justify-content-center">
                                <button class="btn-edit" edit-for="address">Chỉnh sửa</button>
                            </div>
                        </div>
                   </div>
                   <div class="edit-address-user disable-edit">
                        <div class="row">
                            <div class="col-3">
                                <span class="w-100">Địa chỉ</span>
                            </div>
                            <div class="col-6 d-flex">
                                    <div class="edit-address-number">
                                       <input type="text" placeholder="Nhập địa chỉ">
                                    </div>
                            </div>
                            <div class="col-3 d-flex justify-content-center">
                                <button class="btn-save" save-for="address">Lưu lại</button>
                            </div>
                        </div>
                   </div>
                    `

}

const renderEditPayment = () => {
    mySetting.innerHTML = `
                    <div class="row">
                        <div class="col-10">
                            <h3 class="m-0">Thông tin thanh toán</h3>
                            <span>Thêm hoặc bỏ các phương thức thanh toán một cách bảo mật để dễ đặt hơn.</span>
                        </div>
                    </div>
                    <hr>
                    <div class="show-payment-user">
                        <div class="row">
                            <div class="col-3">
                                <span class="w-100">Thẻ thanh toán</span>
                            </div>
                            <div class="col-6">Truong van Tuyen</div>
                            <div class="col-3 d-flex justify-content-center">
                                <button class="btn-edit" edit-for="payment">Thêm thẻ</button>
                            </div>
                        </div>
                    </div>
               
                    <div class="edit-payment-user disable-edit">
                        <div class="row">
                            <div class="col-3">
                                <span class="w-100">thẻ thanh toán</span>
                            </div>
                            <div class="col-6 d-flex">
                                    <div class="edit-address-number">
                                       <input type="text" placeholder="Thêm thẻ thanh toán">
                                    </div>
                            </div>
                            <div class="col-3 d-flex justify-content-center">
                                <button class="btn-save" save-for="payment">Lưu lại</button>
                            </div>
                        </div>
                   </div>`

}

const renderSetting = () => {
    mySetting.innerHTML = `
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
                            <button>Đổi mật khẩu</button>
                        </div>
                    </div>
                    <hr>
                    <div class="row">
                        <div class="col-2">
                                <span class="name w-100">Xóa tài khoản</span>
                        </div>
                        <div class="col-7">Xóa tài khoản STAYESEA vĩnh viễn</div>
                        <div class="col-3 d-flex justify-content-center">
                            <button>Xóa tài khoản</button>
                        </div>
                    </div>
                   
                    `

}

renderEditProfile()

const btnEdit = document.querySelectorAll('.btn-edit');
btnEdit.forEach((edit) => {
    edit.addEventListener('click', () => {
        let editFor = edit.getAttribute('edit-for')
        switch (editFor) {
            case 'name':
                document.querySelector('.show-name-user').classList.add('disable-edit')
                document.querySelector('.edit-name-user').classList.remove('disable-edit')
                break;
            case 'email':
                document.querySelector('.show-email-user').classList.add('disable-edit')
                document.querySelector('.edit-email-user').classList.remove('disable-edit')
                break;
            case 'phone-number':
                document.querySelector('.show-phone-number-user').classList.add('disable-edit')
                document.querySelector('.edit-phone-number-user').classList.remove('disable-edit')
                break;
            case 'birthday':
                document.querySelector('.show-birthday-user').classList.add('disable-edit')
                document.querySelector('.edit-birthday-user').classList.remove('disable-edit')
                break;
            case 'address':
                document.querySelector('.show-address-user').classList.add('disable-edit')
                document.querySelector('.edit-address-user').classList.remove('disable-edit')
                break;
            case 'gender':
                document.querySelector('.show-gender-user').classList.add('disable-edit')
                document.querySelector('.edit-gender-user').classList.remove('disable-edit')
                break;
            case 'payment':
                document.querySelector('.show-payment-user').classList.add('disable-edit')
                document.querySelector('.edit-payment-user').classList.remove('disable-edit')
                break;

        }
    })
})

const btnSave = document.querySelectorAll('.btn-save');
console.log(btnSave)
btnSave.forEach((save) => {
    save.addEventListener('click', () => {
        let saveFor = save.getAttribute('save-for')
        switch (saveFor) {
            case 'name':
                document.querySelector('.show-name-user').classList.remove('disable-edit')
                document.querySelector('.edit-name-user').classList.add('disable-edit')
                break;
            case 'email':
                document.querySelector('.show-email-user').classList.remove('disable-edit')
                document.querySelector('.edit-email-user').classList.add('disable-edit')
                break;
            case 'phone-number':
                document.querySelector('.show-phone-number-user').classList.remove('disable-edit')
                document.querySelector('.edit-phone-number-user').classList.add('disable-edit')
                break;
            case 'birthday':
                document.querySelector('.show-birthday-user').classList.remove('disable-edit')
                document.querySelector('.edit-birthday-user').classList.add('disable-edit')
                break;
            case 'address':
                document.querySelector('.show-address-user').classList.remove('disable-edit')
                document.querySelector('.edit-address-user').classList.add('disable-edit')
                break;
            case 'gender':
                document.querySelector('.show-gender-user').classList.remove('disable-edit')
                document.querySelector('.edit-gender-user').classList.add('disable-edit')
                break;
            case 'payment':
                document.querySelector('.show-payment-user').classList.remove('disable-edit')
                document.querySelector('.edit-payment-user').classList.add('disable-edit')
                break;

        }
    })
})





