$("#date-range").flatpickr({
    mode: "range",
    showMonths: 2,
    dateFormat: "Y-m-d",
    minuteIncrement: 1,
    defaultDate: [checkIn, checkOut],
    minDate: "today",
});


// tùy chỉnh giá tiền
const rangeInput = document.querySelectorAll(".range-input input"),
      priceInput = document.querySelectorAll(".price-input input"),
      range = document.querySelector(".slider .progress");

let priceGap = 1000;

priceInput.forEach((input) => {
    input.addEventListener("input", (e) => {
        let minPrice = parseInt(priceInput[0].value),
            maxPrice = parseInt(priceInput[1].value);

        if (maxPrice - minPrice >= priceGap && maxPrice <= rangeInput[1].max) {
            console.log(e.target.className)
            if (e.target.className === "input-min") {
                rangeInput[0].value = minPrice;
                range.style.left = (minPrice / rangeInput[0].max) * 100 + "%";
            } else {
                rangeInput[1].value = maxPrice;
                range.style.right = 100 - (maxPrice / rangeInput[1].max) * 100 + "%";
            }
        }
    });
});

rangeInput.forEach((input) => {
    input.addEventListener("input", (e) => {
        let minVal = parseInt(rangeInput[0].value),
            maxVal = parseInt(rangeInput[1].value);

        if (maxVal - minVal < priceGap) {
            if (e.target.className === "range-min") {
                rangeInput[0].value = maxVal - priceGap;
            } else {
                rangeInput[1].value = minVal + priceGap;
            }
        } else {
            priceInput[0].value = minVal;
            priceInput[1].value = maxVal;
            range.style.left = (minVal / rangeInput[0].max) * 100 + "%";
            range.style.right = 100 - (maxVal / rangeInput[1].max) * 100 + "%";
        }
    });
});


// chọn sô lượng người và phòng

let minusGuest = document.querySelector(".minus-guest");
let numberGuest = document.querySelector(".num-guest")
let plusGuest = document.querySelector(".plus-guest")
let countGuest = 1;
minusGuest.addEventListener('click', () => {
    if (countGuest <= 1) {
        return
    }

    countGuest--;
    countGuest = (countGuest < 10) ? '0' + countGuest : countGuest;
    numberGuest.innerText = countGuest;
})

plusGuest.addEventListener('click', () => {
    if (countGuest >= 20) {
        return
    }
    countGuest++;
    countGuest = (countGuest < 10) ? '0' + countGuest : countGuest;
    numberGuest.innerText = countGuest;
})


const minusRoom = document.querySelector(".minus-room");
let numberRoom = document.querySelector(".num-room")
const plusRoom = document.querySelector(".plus-room")

let countRoom = 1;
minusRoom.addEventListener('click', () => {
    if (countRoom <= 1) {
        return
    }
    countRoom--;
    countRoom = (countRoom < 10) ? '0' + countRoom : countRoom;
    numberRoom.innerText = countRoom;
})

plusRoom.addEventListener('click', () => {
    if (countRoom >= 20) {
        return
    }
    countRoom++;
    countRoom = (countRoom < 10) ? '0' + countRoom : countRoom;
    numberRoom.innerText = countRoom
})


// logic tìm kiếm

const btnSearch = document.getElementById('btn-search');
let inputNameCity = document.getElementById('input-name-city');


btnSearch.addEventListener('click', () => {


    const date = document.getElementById('date-range');
    const dateString = date.value;

    // Tách chuỗi thành hai phần bằng từ "to"
    const dateParts = dateString.split(" to ");

    // Phần đầu tiên là ngày bắt đầu
    const dateStartString = dateParts[0];
    console.log(dateStartString)

    // Phần thứ hai là ngày kết thúc
    const dateEndString = dateParts[1];
    console.log(dateEndString)

    window.location.href = "/danh-sach-khach-san?" +
        "nameCity=" + inputNameCity.value + "&checkIn=" + dateStartString +
        "&checkOut=" + dateEndString + "&numberGuest=" + numberGuest.textContent + "&numberRoom=" + numberRoom.textContent;
});
const renderDataSearch = (nameCity, numGuest, numRoom) => {
    if (numGuest<10){
        numberGuest.textContent ="0"+numGuest;
    }else {
        numberGuest.textContent = numGuest;
    }
    if (numRoom<10){
        numRoom.textContent ="0"+numRoom;
    }else {
        numberRoom.textContent = numRoom;
    }


    inputNameCity.value = nameCity


}
window.addEventListener('load',()=>{
    renderListHotel(hotelList)
    renderDataSearch(nameCity, valueNumberGuest, valueNumberRoom);
})









const containerParent = document.querySelector('.container-parent');

let data = hotelList.slice();
let sortHeightRating = () => {
    data.sort((hotel1, hotel2) => hotel2.rating - hotel1.rating);
    console.log(data);
    renderListHotel(data);
    console.log(data);
}
let sortHeightStar = () => {
    data.sort((hotel1, hotel2) => hotel2.star - hotel1.star);
    console.log(data)
    renderListHotel(data);
    console.log(data)
}
let dataDefault = () => {
    renderListHotel(hotelList);
}


const renderListHotel = (hotelList) => {
    let  newContent = '';
    hotelList.forEach((hotel) => {

        let htmlStar = '';
        for (let i = 0; i < hotel.star; i++) {
            htmlStar+=`<i class="fa-solid fa-star" style="color: #CF2061;"></i>`
        }
        let htmlAmenity = '';
        for (let i = 0; i <2; i++) {
            htmlAmenity+=`
                            <span style="font-size: 14px" class="me-3 ">${hotel.amenityHotelList[i].icon} ${hotel.amenityHotelList[i].name}</span>
                    `

        }
        newContent += `<div  class="row container-hotel" >
                                <button class="btn-favourite p-0 m-0" value="${hotel.id}">
                                           <i class="fa-solid fa-heart"></i>
                                </button>

                                <a class="card-hotel text-reset text-decoration-none d-flex my-3 "
                                href="/chi-tiet-khach-san/${hotel.id}">

                                <div class="course w-100 border">
                                    <div class="image-btn">
                                        <img class="course-preview " src="/web/assets/image/uudai.jpg">

                                    </div>
                                    <div class="course-info">
                                       <h4 class="p-0 mt-2 w-100" >${hotel.name}</h4>
                                        <p style="font-size: 15px" class="m-0 w-50 overflow-hidden"><i class="fa-solid fa-location-dot"></i> ${hotel.address}</p>
                                        <div class="mt-1  overflow-hidden" >${htmlStar}</div>
                                        
                                       <div class="w-50 list-amenity d-flex overflow-hidden">
                                               ${htmlAmenity}
                                        </div>

                                        <div class="total-review d-flex ">
                                            <div class="age-rating">${hotel.rating.toFixed(1)} </div>
                                            <div class="infor-rating-hotel">
                                                <h6 class="p-0 m-0" >${hotel.ratingText}</h6>
                                                <span class="quantity">${hotel.reviews.length} nhận xét</span>
                                            </div>
                                        </div>
                                        <ul class="price list-unstyled justify-content-center p-0 m-0">
                                            <span class="description-price">Giá mỗi đêm chưa bao gồm thuế & phí</span>

                                            <li class="p-0 wrapper w-100 d-flex justify-content-end">
                                                <span class="original-price "><del>1.300.500 ₫</del></span>
                                                <span class="discount px-2"> Giảm 15%</span>
                                            </li>
                                            <h4 class="p-0 current-price w-100 d-flex justify-content-end">1.150.000₫</h4>


                                        </ul>
                                    </div>
                                </div>

                                 </a>
                        </div>`


    })
    containerParent.innerHTML=newContent;

    // xủ lý khi người dùng click vào nút yêu thích
    let btnHeart = document.querySelectorAll('.btn-favourite')
    btnHeart.forEach((heart) => {
        heart.addEventListener('click', () => {
            if (!heart.classList.contains('style-heart')) {
                heart.classList.add("style-heart")
            } else {
                heart.classList.remove("style-heart");
            }

        })

    })

    // filter cho khách sạn
    let options = {
        rentalType : [],
        amenityHotel : [],
        amenityRoom : [],
        starHotel : [],

    }
}







