const  formLogin = document.querySelector('.form-login');

const showPassword = document.querySelector('.show-password');
const inputEmail = document.getElementById('email');
const inputPassword = document.getElementById('password');
showPassword.addEventListener('click',()=>{
    if (inputPassword.getAttribute('type') === 'password') {
        inputPassword.setAttribute('type', 'text');
        showPassword.innerHTML=`<i class="fa-regular fa-eye-slash"></i>`

    } else {
        inputPassword.setAttribute('type', 'password');
        showPassword.innerHTML=`<i  class="fa-regular fa-eye"></i>`
    }
})


formLogin.addEventListener('submit', (e)=>{
    if (!$('#form-login').valid()){

        return;
    }

    e.preventDefault();
    const data = {
        email :inputEmail.value,
        password : inputPassword.value
    }
    console.log(data)
    axios.post("/api/auth/login",data)
        .then((res) =>{
            toastr.success("Đăng nhập thành công.")
            setTimeout(()=>{
                window.location.href = "http://localhost:9000/";
            },3000)

        })
        .catch((er)=>{
            toastr.error("Tài khoản hoặc mật khẩu không chính xác")
        })

} )

const btnForgotPassword = document.querySelector('.btn-forgot-password');
const inputForgotPassword = document.querySelector('.input-email-forgot');
btnForgotPassword.addEventListener('click',()=>{
    axios.post("/api/auth/forgot-password?email="+inputForgotPassword.value)
        .then((response)=>{
           toastr.success("Vui lòng xác nhận qua email")
        })
        .catch(()=>{
            toastr.error("Lỗi")
        })
})

$('#form-login').validate({
    rules: {
        email: {
            required: true,
            email:true
        },
        password: {
            required: true,
        },
    },
    messages: {
        email: {
            required: "Email không được để trống",
            email:"Email không đúng định dạng"
        },
        password: {
            required: "Mật khẩu không được để trống",
        },
    },
    errorElement: 'span',
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