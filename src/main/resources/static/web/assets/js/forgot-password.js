const  btnPasswordRetrieval = document.querySelector('.btn-password-retrieval');
const  confirmPassword = document.querySelector('.confirm-password');



btnPasswordRetrieval.addEventListener('click',()=>{
    let data = {
        nameToken : currentToken,
        password : confirmPassword.value
    }
    console.log(data)
    axios.put('/api/auth/password-retrieval',data)
        .then((res) =>{
            console.log("Thành công")
        }).catch((err) =>{
            console.log("Thất bại")
        })
})


