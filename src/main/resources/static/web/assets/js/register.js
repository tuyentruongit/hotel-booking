const inputName = document.querySelector(".name");
const inputEmail = document.querySelector(".email");
const inputPassword = document.querySelector(".password");
const inputConfirmPassword = document.querySelector(".confirm-password");
const btnRegister = document.querySelector(".btn-register");

const formRegister = document.querySelector('.form-register');

formRegister.addEventListener('submit' ,(e)=>{

    if (!$('#form-register').valid())return
    e.preventDefault();
    const data = {
        name:inputName.value,
        email : inputEmail.value,
        password: inputPassword.value
    }

    axios.post("/api/auth/register",data)
        .then((res) =>{
            window.location.href = "http://localhost:9000/";
        })
        .catch((er)=>{
            console.log("Nhập sai rồi mày ơi");
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
        password: {
            required: true,
            minLength:6
        },
        confirmPassword: {
            required: true,
            equalTo: "#password",
        },
    },
    messages: {
        nameUser: {
            required: "Vui lòng nhập tên của bạn",
        },
        email: {
            required: "Vui lòng nhập tên của bạn",
            email:"email không đúng định dạng"
        },
        password: {
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
