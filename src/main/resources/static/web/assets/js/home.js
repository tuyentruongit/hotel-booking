
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
            items:2.2
        },
        600:{
            items:3.2
        },
        1000:{
            items:4.2
        }
    }
})







// tùy chọn quantity cho room và  guest
const minusGuest = document.querySelector(".minus-guest");// nút tăng số lượng khách hàng
let numberGuest = document.querySelector(".num-guest"); // quantity guest
const plusGuest = document.querySelector(".plus-guest");//nút giảm số lượng khách hàng
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




// xử lý input data cho tìm kiếm
const btnSearch = document.getElementById('btn-search');
const inputNameCity = document.getElementById('input-name-city'); // input dữ liệu thành phố người dùng nhập vào

const showHotelCity = document.getElementById("show-hotel-city");// btn chuyển hướng từ gợi ý khách sạn theo thanh phố

// chuyển hướng tới trang sanh sách khi người dùng click vào ô tìm kiếm
btnSearch.addEventListener('click' ,()=>{
    if (!$('#name-city').valid()) return;
    let nameCity = inputNameCity.value;
    navigation(nameCity);

});

inputNameCity.addEventListener('keydown',(e)=>{
    if (!$('#name-city').valid()) return;

    if (e.key === 'Enter'){
        navigation(inputNameCity.value);
    }
})

// click vào các khách sạn sẽ chuyển hướng tới trang danh sách khách sạn theo thành phố người dùng đã chọn
showHotelCity.addEventListener('click' , (e)=>{
    let nameCity= showHotelCity.getAttribute('value');
    console.log(nameCity);
    navigation(nameCity);
})


const sliderCity = document.querySelectorAll('.outstanding-city');
sliderCity.forEach(city =>{

})
const  choiceOutstandingCity = () =>{
    let nameCity= showHotelCity.getAttribute('value');
    console.log(nameCity);
    navigation(nameCity);


}


// chuyển hướng tới trang danh sách
const navigation = (inputNameCity) =>{
    const date = document.getElementById('date-range');
    const dateString = date.value;

    // Tách chuỗi thành hai phần bằng từ "to"
    const dateParts = dateString.split(" to ");

    // Phần đầu tiên là ngày bắt đầu
    const dateStartString = dateParts[0];

    // Phần thứ hai là ngày kết thúc
    const dateEndString = dateParts[1];

    console.log(inputNameCity)


    window.location.href="/danh-sach-khach-san?" +
        "nameCity=" + inputNameCity + "&checkIn=" + dateStartString +
        "&checkOut=" + dateEndString + "&numberGuest=" +
        numberGuest.textContent + "&numberRoom=" + numberRoom.textContent;
}



const btnList = document.querySelectorAll('.btn-name-city');
const listCardHotel = document.getElementById('list-card')





// dữ liệu mặc định
let defaultValue = btnList[0];
// khi vào trang thì gọi render dữ liệu mặc định cho recommend hotel
window.addEventListener('load', () => {
    defaultValue.classList.add('active-city')
    // Gọi API để lấy dữ liệu cho thành phố mặc định và render khách sạn
    renderCityData(defaultValue.textContent);
    showHotelCity.innerHTML = `<span>Xem tất cả khách sạn ${defaultValue.textContent}</span>`
    showHotelCity.setAttribute('value', defaultValue.textContent);
});




// lấy value của các tham sô tìm kiếm
btnList.forEach((btn)=>{
    btn.addEventListener('click' , (qualifiedName, value)=>{
        btnList.forEach((btns) =>{
            btns.classList.remove('active-city');
        })

        btn.classList.add("active-city")
        let city = btn.textContent;
        // showHotelCity.innerText = 'Xem thêm khách sạn của ' + city;
        showHotelCity.innerHTML = `<span>Xem tất khách sạn ${city}</span>`
        showHotelCity.setAttribute('value',city);
        renderCityData(city);


    })
})



// gọi api để render dữ liệu
const renderCityData = (value) => {
    axios.get('/api/search/' + value)
        .then((res) => {
            let data = res.data;
            renderHotel(data);
        })
        .catch((err) => {
           console.log(err)
        });
};



// render khách sạn recommend

const renderHotel = (data) =>{

    const date = document.getElementById('date-range');
    const dateString = date.value;

    // Tách chuỗi thành hai phần bằng từ "to"
    const dateParts = dateString.split(" to ");

    // Phần đầu tiên là ngày bắt đầu
    const dateStartString = dateParts[0];

    // Phần thứ hai là ngày kết thúc
    const dateEndString = dateParts[1];

    let html = '';
    let count = 1 ;
    data.forEach((hotel)=> {
        // <span class="p-1 m-0 h-100" >(${hotel.reviews.length}nhận xét)</span>
        // console.log(hotel.city.name , checkIn , checkOut)
        if (count > 8) {
            return;
        }
        html += `
                    <div class="col-3 h-100">
                        <a class="card mb-5 text-reset text-decoration-none  h-100" href="/chi-tiet-khach-san/${hotel.id}?nameCity=${hotel.city.name}&checkIn=${dateStartString}&checkOut=${dateEndString}">
                            <img class="image-hotel" src="/web/assets/image/dep.jpg" alt="Ảnh Hotel">
                            <div class="p-2 h-100">
                                <div class="d-flex justify-content-start align-content-center h-100">
                                    <span class="p-1 m-0 score-rating h-100">${hotel.rating.toFixed(1)}</span>
                                    <span class="p-1 m-0 h-100" >${hotel.ratingText}</span>
                                  
                                </div>
                            
                                <h5 class="p-0 m-0 h-100" >${hotel.name}</h5>
                                <p class="p-0 m-0 d address-hotel h-100" > <i class="fa-solid fa-location-dot"></i> ${hotel.address}</p>
                                <div class="price list-unstyledp-0 m-0">
                                           

                                            <div class="p-0 wrapper w-100 d-flex">
                                                <span class="original-price "><del>1.300.500 ₫</del></span>
                                            </div>
                                            <h4 class="p-0 current-price w-100 d-flex">1.150.000₫</h4>
                                            <p class="description-price w-100 pb-1 m-0">Giá mỗi đêm chưa bao gồm thuế & phí</p>
                                            <span class="discount mt-3"> Giảm 15%</span>


                                   </div>
                            </div>
                        </a>
                    </div>
                    `
        count++;

    })
    listCardHotel.innerHTML = html;


}






