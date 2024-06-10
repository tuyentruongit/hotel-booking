
let currentPage = 1 ;
let hotels = [];
let totalPage = null;

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
    if (!$('#form-search').valid()) return;

    const date = document.getElementById('date-range');
    const dateString = date.value;

    // Tách chuỗi thành hai phần bằng từ "to"
    const dateParts = dateString.split(" to ");

    // Phần đầu tiên là ngày bắt đầu
    const dateStartString = dateParts[0];

    // Phần thứ hai là ngày kết thúc
    const dateEndString = dateParts[1];

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
        console.log(numberGuest);
        console.log(numberRoom);
        numberRoom.textContent = "0" + numRoom;
    } else {
        numberRoom.textContent = numRoom;
    }

    inputNameCity.value = nameCity
}




// thẻ chứa phần caard của  hotel đề xuất
const containerParent = document.querySelector('.container-parent');



const searchByNameHotel = document.querySelector('.input-name-hotel');
searchByNameHotel.addEventListener('keydown',(event)=>{
    console.log(event)

    if (event.key === 'Enter'){
        let keyWord = searchByNameHotel.value.trim();
        if (keyWord){
            const  data = hotels.filter(hotel =>
                hotel.name.toLowerCase().includes(keyWord.toLowerCase()))
            renderListHotel(data)

        }else {
            renderListHotel(hotels);
        }

    }

})
let data = hotels.slice();
const listSort = document.querySelectorAll('.type-sort');
listSort.forEach((btn)=>{
    btn.addEventListener('click' , (qualifiedName, value)=> {
        listSort.forEach((btns) => {
            btns.classList.remove('select-sort');
        })
        btn.classList.add("select-sort")

    })
})
// sắp xếp khaách sạn theo luwaj chọn của người dùng
let sortPriceDesc = () => {
    data.sort((hotel1, hotel2) => hotel2.estimatedPrice - hotel1.estimatedPrice);

    functionFilter(options,data);
}
let sortPriceAsc = () => {
    data.sort((hotel1, hotel2) => hotel1.estimatedPrice - hotel2.estimatedPrice);
    functionFilter(options,data);
}

let sortHeightRating = () => {
    data.sort((hotel1, hotel2) => hotel2.rating - hotel1.rating);
    functionFilter(options,data)
}
let sortHeightStar = () => {
    data.sort((hotel1, hotel2) => hotel2.star - hotel1.star);
    functionFilter(options,data)
}
let dataDefault = () => {
    functionFilter(options,hotels);
}


// lấy ra hotel theo trang mà người dùng chọn
const getHotel = (page) =>{
    console.log(page);
    axios.get("/api/hotel?pageNumber="+page,)
        .then((response) =>{
            hotels= response.data.content;
            totalPage= response.data.totalPages;
            data = hotels.slice();
            renderListHotel(hotels);
            renderPagination(totalPage);
        })
        .catch((error)=>{
            console.log(error)
            // toastr.error(error.response.data.message);
        })
}

// render các page
const    renderPagination = (totalPage) =>{
    console.log(totalPage)
    let html = '';
    for (let i = 1; i <= totalPage; i++) {
        html+=` <li class="page-item ${i === currentPage ? 'active' : ''}">
              <a class="page-link" onclick="choosePage(${i})">${i}</a>
            </li>`
    }
    document.querySelector('.pagination-container').innerHTML = `
        ${totalPage > 1 ? `
            <nav aria-label="...">
              <ul class="pagination">
                <li class="page-item ${currentPage === 1 ? 'disabled' : ''}">
                  <a class="page-link" onclick="previousPage()"><i class="fa-solid fa-angle-left"></i></a>
                </li>
                ${html}
                <li class="page-item ${currentPage === totalPage ? 'disabled' : ''}">
                  <a class="page-link" onclick="nextPage()"><i class="fa-solid fa-angle-right"></i></a>
                </li>
              </ul>
            </nav>
        ` : ""}
    `;
}
const choosePage =(pageNumber)=>{
    currentPage=pageNumber
    getHotel(currentPage);
}
const nextPage =()=>{
    if (currentPage < totalPage) {
        currentPage++;
        getHotel(currentPage);
    }
}
const previousPage =()=>{
    if (currentPage>1){
        currentPage--;
        getHotel(currentPage)
    }
}


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
        functionFilter(options, hotels);
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

// format giá
const formatCurrency = (number)=> {
    return number.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
}


//hàm render dữ liệu khách sạn sau khi đã được lọc
const renderListHotel = (hotelList) => {
    let newContent = '';
    // nếu như không có khách sạn nào theo tìm kiếm của người dùngthifif hiển thị nội dung bên dưới
    if (hotelList.length===0){
        document.querySelector('.pagination-container').classList.add("d-none");
        containerParent.innerHTML = ` <h5 class="d-flex justify-content-center" >Chúng tôi không thể tìm thấy kết quả phù hợp với yêu cầu tìm kiếm của bạn. Vui lòng thử tìm lại.</h5>`;
        return;
    }
    else {
        document.querySelector('.pagination-container').classList.remove("d-none");
    }
    // hiển thị dữ liệu ra ngoài giao diện
    hotelList.forEach((hotel) => {
        let htmlStar = '';
        for (let i = 0; i < hotel.star; i++) {
            htmlStar += `<i class="fa-solid fa-star" style="color: #CF2061;"></i>`
        }
        let htmlAmenity = '';
        for (let i = 0; i < 2; i++) {
            htmlAmenity += `
                            <span style="font-size: 14px" class="me-3 "> ${hotel.nameAmenity[i]}</span>
                    `
        }
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
                                                 <span class="quantity">${hotel.totalReviews} nhận xét</span>
                                            </div>
                                        </div>
                                        <ul class="price list-unstyled justify-content-center p-0 m-0">
                                            <span class="description-price">Giá ước tính mỗi đêm đã bao gồm thuế & phí</span>

                                            <li class="p-0 wrapper w-100 d-flex justify-content-end">
                                                <span class="original-price "><del>1.300.500 ₫</del></span>
                                                <span class="discount px-2"> Giảm 15%</span>
                                            </li>
                                            <h4 class="p-0 current-price w-100 d-flex justify-content-end">${formatCurrency(hotel.estimatedPrice)}</h4>


                                        </ul>
                                    </div>
                                </div>

                                 </a>
                        </div>`


    })
    containerParent.innerHTML = newContent;
    if (hotelsFavourite!=null){
        listFavouriteHotelUser()
    }
}
// nêú người dùng đã đăng nhập thì kiểm tra các khách sạn mà người dùng đã thêm vào trước đó
const listFavouriteHotelUser = ()=>{
    // lấy ra danh sách id của khách sạn
    const listIdHotel = hotelsFavourite.map(hotel => Number(hotel.id));
    // xủ lý khi người dùng click vào nút yêu thích
    let btnHeart = document.querySelectorAll('.btn-favourite')
    btnHeart.forEach((heart) => {
        let idHotelAtButtton = Number(heart.value)
        // highlight button yêu thích nếu khách sạn đó nằm trong danh sách yêu thích của người dùng
        if (listIdHotel.includes(idHotelAtButtton)){
            heart.classList.add("style-heart")
            heart.setAttribute("type-button","delete")
        }
        // hàm xử lý khi clich vào button yêu thích
        heart.addEventListener('click', () => {
            if (heart.getAttribute("type-button")==="add") {
                addHotelFavouriteList(heart,heart.value);

            } else {
                deleteFavourite(heart , heart.value)
            }
        })

    })
}

// gọi api xử lý khi người dùng thêm khách sạn vào danh sách yêu thích
const  addHotelFavouriteList = (heart , id)=>{
    axios.post("/api/hotel/favourite/"+id)
        .then(()=>{
            toastr.success("Đã thêm khách sạn vào danh sách yêu thích.")
            heart.classList.add("style-heart")
            heart.setAttribute("type-button","delete")
        })
        .catch((err)=>{
            if (err.response===401){
                toastr.error("Vui lòng đăng nhập");
            }
        })
}
// gọi api xử lý khi người dùng không muốn thêm khách sạn vào danh sách yêu thích
const   deleteFavourite = (heart,id) =>{
    console.log(id)
    axios.delete("/api/hotel/delete/favourite/"+id)
        .then((err)=>{
            console.log(err)
            toastr.success("Đã xóa khách sạn khỏi danh sách yêu thích.")
            heart.classList.remove("style-heart");
            heart.setAttribute("type-button","add")
        })
        .catch((err)=>{
            if (err.response===401){
                toastr.error("Vui lòng đăng nhập");
            }
        })

}

// xử lý validation dữ liệu
$('#form-search').validate({
    rules: {
        nameCity: {
            required: true,
        },
    },
    messages: {
        nameCity: {
            required: "Vui lòng nhập địa chỉ tên thành phố mà bạn muốn tìm kiếm",
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

getHotel(currentPage);
renderDataSearch(nameCity, valueNumberGuest, valueNumberRoom);










