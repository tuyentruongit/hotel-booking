
const mySetting = document.querySelector('.my-setting-container')


const renderEditProfile =()=>{
    mySetting.innerHTML = `
                    <div class="row">
                        <div class="col-10">
                            <h3 class="m-0">Thông tin cơ bản</h3>
                            <span>Cập nhật thông tin của bạn và tìm hiểu các thông tin này được sử dụng ra sao.</span>
                        </div>
                    </div>
                    <hr>
                    <div class="row">
                        <div class="col-3">
                            <div class="w-100">Tên</div>
                        </div>
                        <div class="col-6">Truong van Tuyen</div>
                        <div class="col-3 d-flex justify-content-center">
                            <button>Chỉnh sửa</button>
                        </div>
                    </div>
                    <hr>
                    <div class="row">
                        <div class="col-3">
                                <div class=" w-100">Địa chỉ email</div>
                        </div>
                        <div class="col-6">truongvantuyen.it@gmail.com</div>
                        <div class="col-3 d-flex justify-content-center">
                            <button>Chỉnh sửa</button>
                        </div>
                    </div>
                    <hr>
                    <div class="row">
                        <div class="col-3">
                            <div class=" w-100">Số điện thoại</div>
                        </div>
                        <div class="col-6">0123456789</div>
                        <div class="col-3 d-flex justify-content-center">
                            <button>Chỉnh sửa</button>
                        </div>
                    </div>
                    <hr>
                    <div class=row>
                        <div class="col-3">
                            <div class=" w-100">Ngày sinh</div>
                        </div>
                        <div class="col-6">23/10/2001</div>
                        <div class="col-3 d-flex justify-content-center">
                            <button>Chỉnh sửa</button>
                        </div>
                    </div>
                    <hr>
                    <div class="row">
                        <div class="col-3">
                            <div class="name w-100">Giới tính</div>
                        </div>
                        <div class="col-6">Nam</div>
                        <div class="col-3 d-flex justify-content-center">
                            <button>Chỉnh sửa</button>
                        </div>
                    </div>
                    <hr>
                    <div class="row">
                        <div class="col-3">
                            <div class="name w-100">Địa chỉ</div>
                        </div>
                        <div class="col-6">Xuân Khê, Lý Nhân , Hà Nam</div>
                        <div class="col-3 d-flex justify-content-center">
                            <button>Chỉnh sửa</button>
                        </div>
                    </div>`

}

const renderEditPayment = ()=>{

    mySetting.innerHTML = `
                    <div class="row">
                        <div class="col-10">
                            <h3 class="m-0">Thông tin cơ bản</h3>
                            <span>Cập nhật thông tin của bạn và tìm hiểu các thông tin này được sử dụng ra sao.</span>
                        </div>
                    </div>
                    <hr>
                    <div class="row">
                        <div class="col-3">
                            <div class="w-100">Tên</div>
                        </div>
                        <div class="col-6">Truong van Tuyen</div>
                        <div class="col-3 d-flex justify-content-center">
                            <button>Chỉnh sửa</button>
                        </div>
                    </div>
                    <hr>
                    <div class="row">
                        <div class="col-3">
                                <div class=" w-100">Địa chỉ email</div>
                        </div>
                        <div class="col-6">truongvantuyen.it@gmail.com</div>
                        <div class="col-3 d-flex justify-content-center">
                            <button>Chỉnh sửa</button>
                        </div>
                    </div>`

}

const renderSetting = ()=>{
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
                            <div class="name w-100">Mật khẩu</div>
                        </div>
                        <div class="col-7">Để thay đổi mật khẩu, chúng tôi cần gửi link tạo lại mật khẩu đến địa chỉ email của bạn</div>
                        <div class="col-3 d-flex justify-content-center">
                            <button>Đổi mật khẩu</button>
                        </div>
                    </div>
                    <hr>
                    <div class="row">
                        <div class="col-2">
                                <div class="name w-100">Xóa tài khoản</div>
                        </div>
                        <div class="col-7">Xóa tài khoản STAYESEA vĩnh viễn</div>
                        <div class="col-3 d-flex justify-content-center">
                            <button>Xóa tài khoản</button>
                        </div>
                    </div>
                   
                    `

}

renderEditProfile()


