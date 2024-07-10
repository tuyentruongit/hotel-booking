// trang hiện tại
let currentPage = 1 ;
// danh sách khách sẽ được lưu vào biến này
let hotels = [];
let totalPage = null;

// hàm format date theo dạng yyyy/mm/dd
function formatDate(date) {
    let year = date.getFullYear(); // Sử dụng getFullYear() để lấy năm đầy đủ
    let month = String(date.getMonth() + 1).padStart(2, '0'); // Tháng được đánh số từ 0-11, cần +1 và đảm bảo có 2 chữ số
    let day = String(date.getDate()).padStart(2, '0'); // Đảm bảo ngày có 2 chữ số
    return `${year}-${month}-${day}`;
}
// đăặt ngày bắt đầu và ngày kêt thúc mặc định
let startDate = formatDate(new Date());
let endDate =  formatDate(moment().add(4, 'days').toDate());
//  thư viện chọn ngày
$(function() {
    // Khởi tạo daterangepicker và thiết lập ngày mặc định
    $('#date-range').daterangepicker({
        opens: 'left',
        startDate: new Date(),
        endDate:  moment().add(4, 'days').toDate(),
        minDate: new Date()
    }, function(start, end, label) {
        // Khi người dùng chọn ngày, cập nhật giá trị của các biến bên ngoài
        startDate = start.format('YYYY-MM-DD');
        endDate = end.format('YYYY-MM-DD');
    });
});

// truy cập vào cac phần tử thay đổi giá
const rangeInput = document.querySelectorAll(".range-input input"),
    priceInput = document.querySelectorAll(".price-input input"),
    range = document.querySelector(".slider .progress");
let priceGap = 100000;
// nếu người dung nhập giá thì chỉnh thanh lider theo giá mà người dùng đã nhâpj
let minPrice = parseInt(priceInput[0].value),
    maxPrice = parseInt(priceInput[1].value);
priceInput.forEach((input) => {
    var min = 100000;
    var max = 20000000;
    input.addEventListener("change", (e) => {
        // lấy ra gi trị của từng ô input
         minPrice = parseInt(priceInput[0].value);
         maxPrice = parseInt(priceInput[1].value);
        // kiểm tra điều kiện (mã - min phải lơn hơn khoảng đã cho, max ô input phải nhỏ vơn với giá trị max ơt thanh slider)
        if (maxPrice - minPrice >= priceGap && maxPrice <= rangeInput[1].max) {
            // kiểm tra xem ngươời dùng nhập gias vào  nào
            if (e.target.className === "input-min") {
                rangeInput[0].value = minPrice;
                // chỉnh thanh slider theo giá
                range.style.left = (minPrice / rangeInput[0].max) * 100 + "%";
            } else {
                rangeInput[1].value = maxPrice;
                range.style.right = 100 - (maxPrice / rangeInput[1].max) * 100 + "%";
            }
        }else {
            if (minPrice < min || minPrice > max || input.value === ''){
                minPrice = min;
                input.value = min;
            }
            if (maxPrice > max || maxPrice< min || input.value === ''){
                maxPrice = max;
                input.value = max
            }
        }

        // lấy giá người dùng thay đổi để lọc các khách sạn
        options.minPriceHotel = minPrice;
        options.maxPriceHotel = maxPrice;
        functionFilter(options,hotels)
    });
});
// giá tiền hiện ô input
rangeInput.forEach((input) => {
    input.addEventListener("input", (e) => {
        // lấy giá trị thanh slider
        let minVal = parseInt(rangeInput[0].value),
            maxVal = parseInt(rangeInput[1].value);
        // nếu mex-min mà nhỏ hơn khoảng thất
        if (maxVal - minVal < priceGap) {
            if (e.target.className === "range-min") {
                rangeInput[0].value = maxVal - priceGap;
            } else {
                rangeInput[1].value = minVal + priceGap;
            }
        } else {
            priceInput[0].value = minVal;
            priceInput[1].value = maxVal;
            // chỉnh thanh slider theo di chuyển
            range.style.left = (minVal / rangeInput[0].max) * 100 + "%";
            range.style.right = 100 - (maxVal / rangeInput[1].max) * 100 + "%";
        }
        // lấy giá người dùng thay đổi để lọc các khách sạn
        options.maxPriceHotel = maxVal;
        options.minPriceHotel = minVal;
        functionFilter(options,hotels)
    });
});


// chọn sô lượng người và phòng
let minusGuest = document.querySelector(".minus-guest");
let numberGuest = document.querySelector(".num-guest")
let plusGuest = document.querySelector(".plus-guest")
const minusRoom = document.querySelector(".minus-room");
let numberRoom = document.querySelector(".num-room")
const plusRoom = document.querySelector(".plus-room")
let countGuest = valueNumberGuest;
minusGuest.addEventListener('click', () => {
    if (countGuest <= 1) {
        return;
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


let countRoom = valueNumberRoom;
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


//  xử lý khi người dùng clicj vào ô tìm kiếm
btnSearch.addEventListener('click', () => {
    if (!$('#form-search').valid()) return;
    window.location.href = "/danh-sach-khach-san?" +
        "nameCity=" + inputNameCity.value + "&checkIn=" + startDate +
        "&checkOut=" + endDate + "&numberGuest=" + numberGuest.textContent + "&numberRoom=" + numberRoom.textContent;
});
// hiển thị dữu liệu người dùng đã tìm kiếm trước đó
const renderDataSearch = (nameCity, numGuest, numRoom) => {
    if (numGuest < 10) {
        numberGuest.textContent = "0" + numGuest;
    } else {
        numberGuest.textContent = numGuest;
    }
    if (numRoom < 10) {
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


// lấy ra trang danh sách khách sạ theo page mà người dùng chọn
const getHotel = (page) =>{
    axios.get("/api/hotel?pageNumber="+page,)
        .then((response) =>{
            hotels= response.data.content;
            totalPage= response.data.totalPages;
            data = hotels.slice();
            renderListHotel(hotels);
            renderPagination(totalPage);
            functionFilter(options, hotels);
        })
        .catch((error)=>{
            toastr.error(error.response.data.message);
        })
}

// render các page
const    renderPagination = (totalPage) =>{
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
    rating: [],
    minPriceHotel : minPrice,
    maxPriceHotel : maxPrice
}
// lọc qua từng ô check box
let checkBoxList = document.querySelectorAll('.custom-checkbox');
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

// thêm cac trường lọc vào mảng option để duyệt dữ liệu
function filterChecked(checkBox) {
    let filterHotel = checkBox.getAttribute('type-check');
    switch (filterHotel) {
        case 'rental-type':
            options.rentalType.push(checkBox.value);
            break;
        case 'amenityHotel':
            options.amenityHotel.push(checkBox.value);
            break;
        case 'amenityRoom':
            options.amenityRoom.push(checkBox.value);
            console.log(options)
            break;
        case 'star':
            options.starHotel.push(checkBox.value);
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
    // lấy giá trị được chọn trước đó
    let filterHotel = checkBox.getAttribute('type-check');
    switch (filterHotel) {
        case 'rental-type':
            // lấy ra vị trí của filter đó trong option
            const indexRentalType = options.rentalType.indexOf(checkBox.value);
            if (indexRentalType !== -1) {
                options.rentalType.splice(indexRentalType, 1);
            }
            break;

        case 'amenityRoom':
            // lấy ra vị trí của filter đó trong option
            const indexAmenityRoom = options.amenityRoom.indexOf(checkBox.value);
            if (indexAmenityRoom !== -1) {
                options.amenityRoom.splice(indexAmenityRoom, 1);
            }
            break;

        case 'amenityHotel':
            // lấy ra vị trí của filter đó trong option
            const indexAmenityHotel = options.amenityHotel.indexOf(checkBox.value);
            if (indexAmenityHotel !== -1) {
                options.amenityHotel.splice(indexAmenityHotel, 1);
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
    console.log(options)
    const hotelListAfterFiltering = hotelList.filter(hotel => {
        console.log(hotel.nameAmenityRoom)
        return (
            (!options.rentalType.length || options.rentalType.includes(hotel.rentalType)) &&
            (!options.starHotel.length || options.starHotel.includes(hotel.star.toString())) &&
            (!options.amenityHotel.length ||options.amenityHotel.every(amenity => hotel.nameAmenity.includes(amenity)))&&
            (!options.amenityRoom.length ||options.amenityRoom.every(amenity => hotel.nameAmenityRoom.includes(amenity)))&&
            (!options.rating.length || options.rating.some(num => num <= hotel.rating)) &&
            (hotel.estimatedPrice >= options.minPriceHotel && hotel.estimatedPrice <= options.maxPriceHotel)
        );
    });
    console.log(hotelListAfterFiltering);
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
                                        <img class="course-preview " src="${hotel.poster}">

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
                                            <span style="font-size: 10px" class="description-price">Giá ước tính mỗi đêm đã bao gồm thuế & phí</span>

<!--                                            <li class="p-0 wrapper w-100 d-flex justify-content-end">-->
<!--                                                <span class="original-price "><del>1.300.500 ₫</del></span>-->
<!--                                                <span class="discount px-2"> Giảm 15%</span>-->
<!--                                            </li>-->
                                            <h4 class="p-0 current-price w-100 d-flex justify-content-end">${formatCurrency(hotel.estimatedPrice)}</h4>


                                        </ul>
                                    </div>
                                </div>

                                 </a>
                        </div>`
    })
    containerParent.innerHTML = newContent;
    // kiểm tra danh sách yêu thích của người dùng nếu khác null
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
// gọi api xử lý khi người dùng xóa khách sạn khỏi danh sách yêu thích
const   deleteFavourite = (heart,id) =>{
    axios.delete("/api/hotel/delete/favourite/"+id)
        .then((err)=>{
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

document.addEventListener('DOMContentLoaded', function() {
    const showAmenityHotel = document.querySelector('.btn-amenityHotel');

    // Sử dụng event delegation để lắng nghe sự kiện click trên .wrap-amenity-hotel
    document.querySelector('.wrap-amenity-hotel').addEventListener('click', function(event) {
        if (event.target.classList.contains('btn-amenityHotel')) {
            event.preventDefault();
            const typeBtn = event.target.getAttribute('type-btn');

            if (typeBtn === 'show') {
                axios.get("/api/amenity/hotel/get-all")
                    .then((response) =>{
                        console.log(response);
                        const data = response.data;
                        renderAmenityHotel(data, 'hide');
                    })
                    .catch((err) =>{
                        console.log(err);
                    });
            } else if (typeBtn === 'hide') {
                renderAmenityHotel(listAmenityHotel, 'show');
            }
        }
    });

    // Hàm renderAmenityHotel với cập nhật DOM
    const renderAmenityHotel = (data, newTypeBtn) => {
        let html = '';
        data.forEach(ame => {
            html += ` <li  class="d-flex align-items-center w-100" >
                                <input class=" m-2 custom-checkbox" type="checkbox" type-check="amenityHotel"  value="${ame.name}" >
                                <label style="font-size: 14px" > ${ame.name}</label>
                            </li>`;
        });

        // Thêm nút "Xem thêm" hoặc "Ẩn bớt" vào cuối danh sách
        if (newTypeBtn === 'show') {
            html += `<button type-btn="show" type="button" class="btn-amenityHotel">Xem thêm</button>`;
        } else {
            html += `<button type-btn="hide" type="button" class="btn-amenityHotel">Ẩn bớt</button>`;
        }

        // Cập nhật nội dung của .wrap-amenity-hotel
        document.querySelector(".wrap-amenity-hotel").innerHTML = html;
        checkBoxList = document.querySelectorAll('.custom-checkbox');
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
    };
});
document.addEventListener('DOMContentLoaded', function() {
    const showAmenityHotel = document.querySelector('.btn-amenityRoom');

    // Sử dụng event delegation để lắng nghe sự kiện click trên .wrap-amenity-room
    document.querySelector('.wrap-amenity-room').addEventListener('click', function(event) {
        if (event.target.classList.contains('btn-amenityRoom')) {
            event.preventDefault();
            const typeBtn = event.target.getAttribute('type-btn');

            if (typeBtn === 'show') {
                axios.get("/api/amenity/room/get-all")
                    .then((response) =>{
                        console.log(response);
                        const data = response.data;
                        renderAmenityRoom(data, 'hide');
                    })
                    .catch((err) =>{
                        console.log(err);
                    });
            } else if (typeBtn === 'hide') {
                renderAmenityRoom(listAmenityRoom, 'show');
            }
        }
    });

    // Hàm renderAmenityHotel với cập nhật DOM
    const renderAmenityRoom = (data, newTypeBtn) => {
        let html = '';
        data.forEach(ame => {
            html += `<li  class="d-flex align-items-center w-100"  >
                                <input class=" m-2 custom-checkbox" type="checkbox" type-check="amenityRoom"  value="${ame.name}" >
                                <label style="font-size: 14px" > ${ame.name}</label>
                            </li>`;
        });

        // Thêm nút "Xem thêm" hoặc "Ẩn bớt" vào cuối danh sách
        if (newTypeBtn === 'show') {
            html += `<button type-btn="show" type="button" class="btn-amenityRoom">Xem thêm</button>`;
        } else {
            html += `<button type-btn="hide" type="button" class="btn-amenityRoom ">Ẩn bớt</button>`;
        }

        // Cập nhật nội dung của .wrap-amenity-hotel
        document.querySelector(".wrap-amenity-room").innerHTML = html;
        checkBoxList = document.querySelectorAll('.custom-checkbox');
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
    };
});


getHotel(currentPage);
renderDataSearch(nameCity, valueNumberGuest, valueNumberRoom);




