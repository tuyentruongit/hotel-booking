flatpickr("#date-birth-day", {
    altInput: true,
    altFormat: "d-m-Y", // Custom altFormat
    dateFormat: "d-m-Y",
    minDate: "01-01-1900",
    maxDate : "today",
    separator: "",
});
const proFileMenu = document.querySelectorAll('.profile-menu');
const inputName = document.querySelector('.input-name-user');
const inputPhoneNumber = document.querySelector('.input-phone');
const inputAddress = document.querySelector('.address');
// const listMonth = document.querySelectorAll('.month')
// const inputDay = document.querySelector('.day');
// const inputYear = document.querySelector('.year');
// const selectMonth = document.getElementById('listMonth')

const date = document.getElementById('date-birth-day');
const listGender = document.querySelectorAll('.gender-option');





// let month="";
const renderData = ()=>{
    inputName.value = inforUser.name;
    inputPhoneNumber.value = inforUser.phoneNumber;
    inputAddress.value = inforUser.address;
    // flatpickr("#date-birth-day", {
    //     altInput: true,
    //     altFormat: "Y-m-d", // Custom altFormat
    //     dateFormat: "d-m-Y",
    //     minDate: "01-01-1900",
    //     maxDate : "today",
    //     separator: "",
    //     defaultDate: inforUser.birthDay, // Sử dụng giá trị đã chuyển đổi của LocalDate
    //
    // });

    listGender.forEach((gender) =>{
        if (gender.value === inforUser.gender){
            gender.selected = true
        }
    })

}


proFileMenu.forEach(btn =>{
    btn.addEventListener('click',(e)=>{
        e.preventDefault();

        switch (btn.getAttribute('value')){
            case 'profile':
                document.querySelector('.content-setting').classList.remove('d-none');
                document.querySelector('.setting-account').classList.add('d-none');
                break;
            case 'setting':
                document.querySelector('.content-setting').classList.add('d-none');
                document.querySelector('.setting-account').classList.remove('d-none');
                break;

        }

    })
})









const btnSave = document.querySelector('.btn-save');
const formatDate = (dateString) => {
    const date = new Date(dateString);
};
btnSave.addEventListener('click' ,()=>{
    // listMonth.forEach((op) =>{
    //     if (op.selected){
    //         month=op.value;
    //     }
    // })
    let gender = '';
    listGender.forEach((genderEl) =>{
        if (genderEl.selected){
            gender=genderEl.value;
        }
    })
    console.log(gender)

    const date = document.getElementById('date-birth-day');
    console.log( date.value)
    const  dataUser ={
        name : inputName.value,
        phone: inputPhoneNumber.value,
        gender : gender,
        address : inputAddress.value,
        birthDay : date.value
    }
    axios.put("/api/auth/update-user/"+inforUser.id , dataUser)
        .then((response)=>{
            console.log("Cập nhật thành công")
        })
        .catch((err)=>{
            console.log("Lỗi rồi mầy ơi")
        })


})
renderData()


const btnChangePassword = document.querySelector('.btn-change');
btnChangePassword.addEventListener('click',()=>{
    const password = document.querySelector('.old-password');
    const newPassword = document.querySelector('.new-password');
    console.log( password.value)
    console.log( newPassword.value)

    const data = {
        oldPassword : password.value,
        newPassword:  newPassword.value

    }
    axios.put("/api/auth/change-password/"+inforUser.id ,data)
        .then((response) =>{
            console.log("Đổi được rồi ")
        })
        .catch((err)=>{
            console.log(err)
        })
})