const btnDeleteFavourite = document.querySelectorAll('.btn-delete');


btnDeleteFavourite.forEach(heartDelete =>{
    heartDelete.addEventListener('click',()=>{
        axios.delete("/api/hotel/favourite/"+heartDelete.value)
            .then(()=>{
                console.log("Thành công")
                renderData(listHotel);
            })
            .catch((err)=>{
                console.log("Đăng nhập đi")
            })
    })
})

// const renderData = (list) =>{
//
//
// }
