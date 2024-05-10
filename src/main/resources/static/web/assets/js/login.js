const  formLogin = document.querySelector('.form-login');

formLogin.addEventListener('submit', (e)=>{
    const inputEmail = document.getElementById('email');
    const inputPassword = document.getElementById('password');

    e.preventDefault();
    const data = {
        email :inputEmail.value,
        password : inputPassword.value
    }
    console.log(data)
    axios.post("/api/auth/login",data)
        .then((res) =>{
            window.location.href = "http://localhost:9000/";
        })
        .catch((er)=>{
            console.log("Nhập sai rồi mày ơi");
        })

} )

const btnForgotPassword = document.querySelector('.btn-forgot-password');
const inputForgotPassword = document.querySelector('.input-email-forgot');
btnForgotPassword.addEventListener('click',()=>{
    axios.post("/api/auth/forgot-password?email="+inputForgotPassword.value)
        .then((response)=>{
           console.log("thành công ");
        })
        .catch(()=>{
            console.log("Nhập sai rồi mày ơi");
        })
})