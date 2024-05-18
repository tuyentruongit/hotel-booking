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
    if (numGuest < 10) {
        numberGuest.textContent = "0" + numGuest;
    } else {
        numberGuest.textContent = numGuest;
    }
    if (numRoom < 10) {
        numRoom.textContent = "0" + numRoom;
    } else {
        numberRoom.textContent = numRoom;
    }

    inputNameCity.value = nameCity
}

// khi vừa vào trang thì render ra dữ liệu
window.addEventListener('load', () => {
    // data hotel
    renderListHotel(hotelList);
    // data input tìm kiếm
    renderDataSearch(nameCity, valueNumberGuest, valueNumberRoom);
})

// thẻ chứa phần caard của  hotel đề xuất
const containerParent = document.querySelector('.container-parent');


let data = hotelList.slice();
let sortHeightRating = () => {
    data.sort((hotel1, hotel2) => hotel2.rating - hotel1.rating);
    functionFilter(options,data)
}
let sortHeightStar = () => {
    data.sort((hotel1, hotel2) => hotel2.star - hotel1.star);
    functionFilter(options,data)
}
let dataDefault = () => {
    functionFilter(options,hotelList);
}

const searchByNameHotel = document.querySelector('.input-name-hotel');
searchByNameHotel.addEventListener('keydown',(event)=>{
    console.log(event)

    if (event.key === 'Enter'){
        let keyWord = searchByNameHotel.value.trim();
        console.log(keyWord+'tên khách sạn tìm kiếm')

        if (keyWord){
            const  data = hotelList.filter(hotel =>
                hotel.name.toLowerCase().includes(keyWord.toLowerCase()))
            renderListHotel(data)

        }else {
            renderListHotel(hotelList);
        }

    }

})


// filter cho khách sạn
let options = {
    rentalType: [],
    amenityHotel: [],
    amenityRoom: [],
    starHotel: [],
    paymentMethod: [],
    rating: []
}
// lọc qua từng ô check box
const checkBoxList = document.querySelectorAll('.custom-checkbox');
checkBoxList.forEach(checkBox => {
    // lắng nghe sự kiện khi người dùng click vào ô
    checkBox.addEventListener('change', () => {
        if (checkBox.checked) {
            // gọi hàm xử lý filter
            filterChecked(checkBox);
        } else {
            // gọi hàm xử lý khi nguười dùng unchecked
            filterUnChecked(checkBox)
        }
        // gọi hàm lọc dữ liệu khách sạn
        functionFilter(options, hotelList);
    });
});
function filterChecked(checkBox) {
    let filterHotel = checkBox.getAttribute('type-check');
    switch (filterHotel) {
        case 'rental-type':
            options.rentalType.push(checkBox.value);
            break;
        case 'star':
            options.starHotel.push(checkBox.value);
            console.log(checkBox.value)
            break;
        case 'rating':
            options.rating.splice(0, 1);
            let ratingNumber = parseInt(checkBox.value)
            options.rating.push(ratingNumber);
            break;

    }

}


// xóa filter khi người dùng bỏ check box
function filterUnChecked(checkBox) {
    let filterHotel = checkBox.getAttribute('type-check');
    switch (filterHotel) {
        case 'rental-type':
            // lấy ra vị trí của filter đó trong option
            const indexRentalType = options.rentalType.indexOf(checkBox.value);
            if (indexRentalType !== -1) {
                options.rentalType.splice(indexRentalType, 1);
            }
            break;
        case 'star':
            // lấy ra vị trí của filter đó trong option
            const indexStar = options.starHotel.indexOf(checkBox.value);
            if (indexStar !== -1) {
                options.starHotel.splice(indexStar, 1);
            }
            break;

    }

}


// hàm lọc dữ liệu khách sạn
const functionFilter = (options,hotelList) => {
    const hotelListAfterFiltering = hotelList.filter(hotel => {
        return (
            (!options.rentalType.length || options.rentalType.includes(hotel.rentalType))&&
            (!options.starHotel.length || options.starHotel.includes(hotel.star.toString()))&&
            (!options.rating.length || options.rating.some(num =>num <= hotel.rating))

        );
    });

    // ggọi hàm render dữ liệu khách sạn sau khi đã được lọc
    renderListHotel(hotelListAfterFiltering);
};


//hàm render dữ liệu khách sạn sau khi đã được lọc
const renderListHotel = (hotelList) => {
    let newContent = '';
    if (hotelList.length===0){
        containerParent.innerHTML = ` <h5 class="d-flex justify-content-center" >Chúng tôi không thể tìm thấy kết quả phù hợp với yêu cầu tìm kiếm của bạn. Vui lòng thử tìm lại.</h5>`;
        return;
    }
    hotelList.forEach((hotel) => {


        let htmlStar = '';
        for (let i = 0; i < hotel.star; i++) {
            htmlStar += `<i class="fa-solid fa-star" style="color: #CF2061;"></i>`
        }
        let htmlAmenity = '';
        for (let i = 0; i < 2; i++) {
            htmlAmenity += `
                            <span style="font-size: 14px" class="me-3 ">${hotel.amenityHotelList[i].icon} ${hotel.amenityHotelList[i].name}</span>
                    `

        }
        //<span class="quantity">${hotel.reviews.length} nhận xét</span>
        newContent += `
                        <div  class="row container-hotel" >
                                <button  class="btn-favourite p-0 m-0" value="${hotel.id}" type-button="add">
                                           <i class="fa-solid fa-heart"></i>
                                </button>

                                <a class="card-hotel text-reset text-decoration-none d-flex my-3 "
                                href="/chi-tiet-khach-san/${hotel.id}?nameCity=${nameCity}&checkIn=${checkIn}&checkOut=${checkOut}&numberGuest=${valueNumberGuest}&numberRoom=${valueNumberRoom}">

                                <div class="course w-100 border">
                                    <div class="image-btn">
                                        <img class="course-preview " src="/web/assets/image/dep.jpg">

                                    </div>
                                    <div class="course-info">
                                       <h4 class="p-0 mt-2 w-100" >${hotel.name}</h4>
                                        <p style="font-size: 14px; color: #0d6efd"  class="m-0 overflow-hidden"><i class="fa-solid fa-location-dot"></i> ${hotel.address}</p>
                                        <div class="mt-1  overflow-hidden" >${htmlStar}</div>

                                       <div class="w-50 list-amenity d-flex overflow-hidden">
                                               ${htmlAmenity}
                                        </div>

                                        <div class="total-review d-flex ">
                                            <div class="age-rating">${hotel.rating.toFixed(1)} </div>
                                            <div class="infor-rating-hotel">
                                                <h6 class="p-0 m-0" >${hotel.ratingText}</h6>
                                               
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
    containerParent.innerHTML = newContent;


    const listIdHotel = hotelsFavourite.map(hotel => Number(hotel.id));
    console.log(listIdHotel)

    // xủ lý khi người dùng click vào nút yêu thích
    let btnHeart = document.querySelectorAll('.btn-favourite')
    btnHeart.forEach((heart) => {
        let idHotelAtButtton = Number(heart.value)
        if (listIdHotel.includes(idHotelAtButtton)){
            heart.classList.add("style-heart")
            heart.setAttribute("type-button","delete")
        }
        heart.addEventListener('click', () => {
            if (!heart.classList.contains('style-heart')) {
                axios.post("/api/hotel/favourite/"+heart.value)
                    .then(()=>{
                        toastr.success("Đã thêm khách sạn vào danh sách yêu thích.")
                        heart.classList.add("style-heart")
                        heart.setAttribute("type-button","delete")
                    })
                    .catch((err)=>{
                        if (err.response.status===401){
                            toastr.error("Vui lòng đăng nhập");
                        }
                    })

            } else {
                axios.delete("/api/hotel/favourite/"+heart.value)
                    .then(()=>{
                        toastr.success("Đã xóa khách sạn khỏi danh sách yêu thích.")
                        heart.classList.remove("style-heart");
                        heart.setAttribute("type-button","add")
                    })
                    .catch((err)=>{
                       if (err.response.status===401){
                           toastr.error("Vui lòng đăng nhập");
                       }
                    })

            }
        })

    })
}
console.log(hotelsFavourite);









