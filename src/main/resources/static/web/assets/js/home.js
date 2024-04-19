let startDate, endDate;

// thư viện để sử dụng trình chọn ngày
flatpickr("#date-range", {
    mode: "range",
    altInput: true,
    altFormat: "j \\t\\h\\á\\n\\g n", // Custom altFormat
    dateFormat: "Y-m-d",
    minDate: "today", // Chỉ hiển thị ngày từ ngày hiện tại
    defaultDate: [new Date().fp_incr(4), new Date().fp_incr(8)],
    showMonths: 2,
    separator: "",
});
// slider
$('.owl-carousel').owlCarousel({
    loop:true,
    margin:15,
    autoHeight: false,
    nav:true,
    navText:['<i class="fa-solid fa-angle-left"></i>','<i class="fa-solid fa-angle-right"></i>'],
    dots: false,
    responsive:{
        0:{
            items:1.2
        },
        600:{
            items:2.2
        },
        1000:{
            items:3.2
        }
    }
})









// chọn sô lượng người và phòng

const minusGuest = document.querySelector(".minus-guest");
let numberGuest = document.querySelector(".num-guest")
const plusGuest = document.querySelector(".plus-guest")
let countGuest = 1;
minusGuest.addEventListener('click',()=>{
    if(countGuest<=1){
      return
    }

    countGuest--;
    countGuest=(countGuest<10)?'0'+countGuest : countGuest;
    numberGuest.innerText = countGuest;
})

plusGuest.addEventListener('click',()=>{
    if(countGuest>=20){
       return
    }
    countGuest++;
    countGuest=(countGuest<10)?'0'+countGuest : countGuest;
    numberGuest.innerText = countGuest;
})



const minusRoom = document.querySelector(".minus-room");
let numberRoom = document.querySelector(".num-room")
const plusRoom = document.querySelector(".plus-room")

let countRoom = 1;
minusRoom.addEventListener('click',()=>{
    if(countRoom<=1){
        return
    }
    countRoom--;
    countRoom=(countRoom<10)?'0'+countRoom : countRoom;
    numberRoom.innerText = countRoom;
})

plusRoom.addEventListener('click',()=>{
    if(countRoom >=20){
        return
    }
    countRoom++;
    countRoom=(countRoom<10)?'0'+countRoom : countRoom;
    numberRoom.innerText = countRoom
})





// logic tìm kiếm

const btnSearch = document.getElementById('btn-search');



btnSearch.addEventListener('click' ,()=>{
    const inputNameCity = document.getElementById('input-name-city').value;
    const date = document.getElementById('date-range');
    const dateString = date.value;

    // Tách chuỗi thành hai phần bằng từ "to"
    const dateParts = dateString.split(" to ");

    // Phần đầu tiên là ngày bắt đầu
    const dateStartString = dateParts[0];

    // Phần thứ hai là ngày kết thúc
    const dateEndString = dateParts[1];

    window.location.href="/danh-sach-khach-san?" +
        "nameCity=" + inputNameCity + "&checkIn=" + dateStartString +
        "&checkOut=" + dateEndString + "&numberGuest=" + numberGuest.textContent + "&numberRoom=" + numberRoom.textContent;
});


