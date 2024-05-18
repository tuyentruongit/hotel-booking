$("#date-range").flatpickr({
    mode: "range",
    showMonths: 2,
    dateFormat: "Y-m-d",
    minuteIncrement: 1,
    defaultDate: [checkIn, checkOut],
    minDate: "today",
});

// slider
$('.owl-carousel').owlCarousel({
    loop:true,
    margin:10,
    nav:true,
    navText:['<i class="fa-solid fa-angle-left"></i>','<i class="fa-solid fa-angle-right"></i>'],
    autoHeight: false,
    dots: false,
    responsive:{
        0:{
            items:1
        },
        600:{
            items:1
        },
        1000:{
            items:1
        }
    }
})
const stars = document.querySelectorAll(".star");
const ratingValue = document.getElementById("rating-value");

let currentRating = 0;

stars.forEach((star) => {
    star.addEventListener("mouseover", () => {
        resetStars();
        const rating = parseInt(star.getAttribute("data-rating"));
        highlightStars(rating);
    });

    star.addEventListener("mouseout", () => {
        resetStars();
        highlightStars(currentRating);
    });

    star.addEventListener("click", () => {
        currentRating = parseInt(star.getAttribute("data-rating"));
        ratingValue.textContent = `Bạn đã đánh giá ${currentRating} sao.`;
        highlightStars(currentRating);
    });
});

function resetStars() {
    stars.forEach((star) => {
        star.classList.remove("active");
    });
}

function highlightStars(rating) {
    stars.forEach((star) => {
        const starRating = parseInt(star.getAttribute("data-rating"));
        if (starRating <= rating) {
            star.classList.add("active");
        }
    });
}

// render thông tin tìm kiếm trước đó của người dùng
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
    renderDataSearch(nameCity, valueNumberGuest, valueNumberRoom);
})

//dóng mở theo bootstrap
const modalReviewConfig = new bootstrap.Modal('#myModal', {
    keyboard: false
})

let idReviewUpdate = null;
let idHotel = null;
// nội dung
const reviewContent = document.getElementById("reviewContent");
const btnSubmitReview = document.getElementById("submitReview");
btnSubmitReview.addEventListener('click', (e) => {
    e.preventDefault();

    if (currentRating === 0) {
        alert("Vui lòng chọn số sao")
        return;
    }
    const dataReview = {
        comment: reviewContent.value,
        rating: currentRating,
        idHotel: idHotel
    }
    console.log(dataReview);
    axios.put("/api/reviews/update/" + idReviewUpdate, dataReview)
        .then((res) => {
            renderReview(reviewLists);
            console.log("Thành công")
            modalReviewConfig.hide();
        })
        .catch((err) => {
            console.log("Thất công")
        })
})

const renderDataReview = (id) => {
    // Tìm kiếm review theo id
    const review = reviewLists.find(review => review.id === id);
    console.log(review.comment)

    // Cập nhật dữ liệu cho modal
    reviewContent.value = review.comment;

    currentRating = review.rating;
    ratingValue.textContent = `Bạn đã đánh giá ${currentRating} sao.`;
    highlightStars(currentRating);

    // Lưu lại id review cần cập nhật
    idReviewUpdate = id;

    // lưu lại id hotel
    idHotel = review.hotel.id;
    console.log(idHotel)
}


// Xóa Review
const deleteReview = id => {
    const isConfirm = confirm("Bạn có chắc mình muốn xóa review này không?");
    if (!isConfirm) return;
    // Gọi Api để xóa

    axios.delete("/api/reviews/delete/" + id)
        .then((res) => {
            renderReview(reviewLists);
            console.log("Thành công")
        })
        .catch((err) => {
            console.log("Thất công")
        })
}


const formatDate = (dateString) => {
    const date = new Date(dateString);

    const day = `0${date.getDate()}`.slice(-2); // `05` -> 05 , '015' -> 15
    const month = `0${date.getMonth() + 1}`.slice(-2);
    const year = date.getFullYear();

    return `${day}/${month}/${year}`;
};



const reviewListEL = document.querySelector('.container-review-detail')


const renderReview = (reviews) => {
    let html = "";
    reviews.forEach(review => {
        html += `<div class="review-detail my-3 ">
                                        <div class="d-flex align-items-end">
                                        
                                            <h4 class="age-user-review p-0 m-0" >${review.rating}/10 - ${review.ratingText} </h4>
                                            <div class="creatAt-review">
                                                ${formatDate(review.createAt)}
                                            </div>
                                        </div>
                         
                                        <h6 class="name-user-review">
                                            ${review.user.name}
                                        </h6>
                                       
                                        <span class="content-review">${review.comment}</span>
                                        
                                          ${inforUser && inforUser.id === review.user.id ? `
                                             <div class="warp-button-edit" >
                                            <button class=" btn-edit-review btn " type="button"
                                                    data-bs-toggle="modal" data-bs-target="#myModal" onclick="renderDataReview(${review.id})">Chỉnh sửa
                                            </button>
                                            <button class=" btn-delete-review btn " onclick="deleteReview(${review.id})" >Xóa</button>
                                               </div>
                                            ` : ''}
                                        
                                     


                                    </div>`
    });
    reviewListEL.innerHTML = html;
};


renderReview(reviewLists);
