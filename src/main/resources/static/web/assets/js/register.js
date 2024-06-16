const inputName = document.querySelector(".name");
const inputEmail = document.querySelector(".email");
const btnRegister = document.querySelector(".btn-register");

const formRegister = document.getElementById('form-register');
const showPassword = document.querySelector('.show-password');
const showPasswordConfirm = document.querySelector('.show-password-confirm');
const inputPassword = document.querySelector(".password");
const inputConfirmPassword = document.querySelector(".confirm-password");


// <Hiển thị mật khẩu>
showPassword.addEventListener('click',()=>{
    if (inputPassword.getAttribute('type') === 'password') {
        inputPassword.setAttribute('type', 'text');
        showPassword.innerHTML=`<i class="fa-regular fa-eye-slash"></i>`

    } else {
        inputPassword.setAttribute('type', 'password');
        showPassword.innerHTML=`<i  class="fa-regular fa-eye"></i>`
    }
})
showPasswordConfirm.addEventListener('click',()=>{
    if (inputConfirmPassword.getAttribute('type') === 'password') {
        inputConfirmPassword.setAttribute('type', 'text');
        showPasswordConfirm.innerHTML=`<i class="fa-regular fa-eye-slash"></i>`

    } else {
        inputConfirmPassword.setAttribute('type', 'password');
        showPasswordConfirm.innerHTML=`<i  class="fa-regular fa-eye"></i>`
    }
})


formRegister.addEventListener('submit' ,(e)=>{
    e.preventDefault();
    if (!$('#form-register').valid()) return;
    const data = {
        name:inputName.value,
        email : inputEmail.value,
        password: inputPassword.value,
        confirmPassword: confirmPassword.value
    }
    axios.post("/api/auth/register",data)
        .then((res) =>{
            console.log(res)
            toastr.success("Đăng ký thành công. Vui lòng kiểm tra email xác thực tài khoản ")
        })
        .catch((er)=>{
            console.log(er)
            toastr.error(er.response.data.message);
        })
})

$('#form-register').validate({
    rules: {
        nameUser: {
            required: true,
        },
        email: {
            required: true,
            email:true
        },
        inputPassword: {
            required: true,
        },
        confirmPassword: {
            required: true,
            equalTo: "[name='inputPassword']"
        },
    },
    messages: {
        nameUser: {
            required: "Vui lòng nhập tên của bạn",
        },
        email: {
            required: "Vui lòng email của bạn",
            email:"email không đúng định dạng"
        },
        inputPassword: {
            required: "Vui lòng nhập mật khẩu",
        },
        confirmPassword: {
            required: "Vui lòng nhập tên của bạn",
            equalTo: "Mật khẩu không trùng khớp",
        },
    },
    errorElement: 'label',
    errorPlacement: function (error, element) {
        error.addClass('invalid-feedback');
        element.closest('.form-group').append(error);
    },
    highlight: function (element, errorClass, validClass) {
        $(element).addClass('is-invalid');
    },
    unhighlight: function (element, errorClass, validClass) {
        $(element).removeClass('is-invalid');
    }
});