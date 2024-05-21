
const wrapList = document.querySelector('.wrap-list');
const renderData = (list) =>{
    let html = '';
    list.forEach(hotelFavourite =>{
        let htmlStar = '';
        for (let i = 0; i < hotelFavourite.star ; i++) {
            htmlStar += `<i class="fa-solid fa-star" style="color: #CF2061;"></i>`
        }
        html+=`

                     <div class="col-3 h-100 conatiner-card">
                            <a class="card-hotel text-reset text-decoration-none w-100" href="/chi-tiet-khach-san/${hotelFavourite.id}?nameCity=${hotelFavourite.city.name}">
                                <img class="image-hotel w-100"  src="/web/assets/image/amanoi-resort-beach-club-1400x600.jpg" alt="">
                                <div class="infor-hotel h-100">
                                    <p class="name-hotel w-100">${hotelFavourite.name}</p>
                                    <span class="city-hotel" >${hotelFavourite.city.name}</span>
                                    <div class="star">
                                        ${htmlStar}
                                    </div>
                                    <hr>
                                    <div class="ratting-hotel">
                                        <div>
                                            <span class="age-rating" >${hotelFavourite.rating.toFixed(1)}</span>
                                            <span class="rating-text"  > ${hotelFavourite.ratingText}</span>
                                        </div>
                                    </div>
                                    <hr>
                                    <div class="description-price">
                                        <span class="description-price">Mỗi đêm chỉ từ </span>
                                        <span> <del> 123456 </del> đ</span>
                                    </div>
                                    <div class="price-hotel">
                                        <span class="price">506.000 </span>
                                        <span class="unit">đ</span>
                                    </div>


                                </div>
                            </a>
                            <div class="delete">
                                <button value="${hotelFavourite.id}" class="btn-delete" type="button"  onclick="deleteHotelFavourite(${hotelFavourite.id})">
                                    <i class="fa-solid fa-xmark"></i>
                                </button>
                            </div>
                        </div>
            `;
    })
    wrapList.innerHTML=html;

}

const deleteHotelFavourite = (id) =>{
    axios.delete("/api/hotel/favourite/"+id)
        .then((response)=>{
            console.log(response);
            toastr.success("Đã xóa khách sạn ra khỏi danh sách yêu thích")
            hotelList = hotelList.filter(review => review.id !== id)
            renderData(hotelList);
        })
        .catch((err)=>{
            toastr.error("Đã xóa khách sạn ra khỏi danh sách yêu thích");
        })
}






renderData(hotelList);
