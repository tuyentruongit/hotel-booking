const inputName = document.querySelector(".name");
const inputEmail = document.querySelector(".email");
const inputPassword = document.querySelector(".password");
const inputConfirmPassword = document.querySelector(".confirm-password");
const btnRegister = document.querySelector(".btn-register");

const formRegister = document.querySelector('.form-register');

formRegister.addEventListener('submit' ,(e)=>{
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
