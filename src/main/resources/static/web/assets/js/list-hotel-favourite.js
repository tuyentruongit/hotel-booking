const getSlider = ()=>{
    $('.owl-carousel').owlCarousel({
        loop:true,
        margin:10,
        nav:true,
        navText:['<i class="fa-solid fa-angle-left"></i>','<i class="fa-solid fa-angle-right"></i>'],
        autoHeight: false,
        dots: false,
        responsive:{
            0:{
                items:5.3
            },
            600:{
                items:5.3
            },
            1000:{
                items:5.3
            }
        }
    })
}

const sliderMain = document.querySelector('.owl-carousel');
const renderData = (list) =>{
    let html = '';
    list.forEach(hotelFavourite =>{
        let htmlStar = '';
        for (let i = 0; i < hotelFavourite.star; i++) {
            htmlStar += `<i class="fa-solid fa-star" style="color: #CF2061;"></i>`
        }
        html+=`
                     <div class="card-hotel">
                            <a class="text-reset text-decoration-none" href="/chi-tiet-khach-san/${hotelFavourite.id}?nameCity=${hotelFavourite.city.name}">
                                <img  src="/web/assets/image/amanoi-resort-beach-club-1400x600.jpg" alt="">
                                <div class="infor-hotel">
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
                                <button value="${hotelFavourite.id}" class="btn-delete" type="button">
                                    <i class="fa-solid fa-xmark"></i>
                                </button>
                            </div>
                        </div>
            `;
    })
    sliderMain.innerHTML=html;
    getSlider()

}
renderData(hotelList);

const btnDeleteFavourite = document.querySelectorAll('.btn-delete');
const container  = document.querySelector('.container');



btnDeleteFavourite.forEach(heartDelete =>{
    heartDelete.addEventListener('click',()=>{
        axios.delete("/api/hotel/favourite/"+heartDelete.value)
            .then((response)=>{
                console.log(hotelList.filter(hotel => hotel.id !== parseInt(btnDeleteFavourite.value)))
                console.log("Thành công")
                getSlider()
                $('.owl-carousel').trigger('refresh.owl.carousel');
                if (hotelList.filter(hotel => hotel.id !== parseInt(btnDeleteFavourite.value)).length>0){
                    renderData(hotelList.filter(hotel => hotel.id !== parseInt(btnDeleteFavourite.value)));
                }
                else{
                    container.innerHTML=`
                      <div class="row" >
                            <h1>Chưa có khách sạn nào được thêm vào mục yêu thích</h1>
                        </div>
                    `
                }

            })
            .catch((err)=>{
                console.log("Đăng nhập đi")
            })
    })
})

