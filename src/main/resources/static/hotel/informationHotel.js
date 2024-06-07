
let descriptionEl = document.getElementById("description");
let btnSave = document.getElementById("btn-save");
// quản lý ảnh
const imageContainer = document.querySelector(".image-container");
const btnChoseImage = document.getElementById("btn-chose-image");
const btnDeleteImage = document.getElementById("btn-delete-image");
const inputImage = document.getElementById("avatar");
const thumbnailPreview = document.getElementById("thumbnail");


btnSave.addEventListener('click', (e) => {
    e.preventDefault();
    if (!$('#myForm').valid()) return;
     let statusHotel = false
    if (document.getElementById('status').value === '1'){
        statusHotel=true;
    }

    const data ={
        description : descriptionEl.value,
        status :statusHotel
    }


    axios.put("/api/hotel/update/" + hotel.id, data)
        .then((res) => {
            toastr.success("Lưu thành công ");
        })
        .catch((err) => {
            console.log(err)
            toastr.error(err.response.data.message);
        })

})

$('#myForm').validate({
    rules: {
        description: {
            required: true,
        },
    }, messages: {
        description: {
            required: "Vui lòng nhập mô tả về khách sạn",
        },

    }, errorElement: 'span', errorPlacement: function (error, element) {
        error.addClass('invalid-feedback');
        element.closest('.form-group').append(error);
    }, highlight: function (element, errorClass, validClass) {
        $(element).addClass('is-invalid');
    }, unhighlight: function (element, errorClass, validClass) {
        $(element).removeClass('is-invalid');
    }
});



// hiển thị danh sách ảnh
const renderImage = images => {
    let htmlImage = "";
    images.forEach(image => {
        htmlImage += `<div class="image-item" onclick="choseImage(this)"  data-id="${image.id}">
                                <img src="${image.url}"
                                     alt="">
                       </div>`;
    });
    imageContainer.innerHTML = htmlImage;
}
inputImage.addEventListener('change' ,(e)=>{
    // lấy ra thông tin file mà người ta upload
    const file = e.target.files[0];
    // tạo đối tượng
    const formData = new FormData();
    formData.append("file",file);
    axios.post("/api/images/upload-hotel/"+2,formData)
        .then((res)=>{
            imageList.unshift(res.data);
            renderPagination(imageList);
            toastr.success("Upload ảnh thành công ");
            console.log("Thanhf coong")

        })
        .catch((err)=>{
            console.log(err);
        })



})
const choseImage = (el) =>{
    const selectedEl = document.querySelector(".image-item.selected");
    if (selectedEl){
        selectedEl.classList.remove("selected");
    }
    el.classList.add("selected")
    btnChoseImage.disabled=false;
    btnDeleteImage.disabled = false;

}
// chọn ảnh
btnChoseImage.addEventListener('click',()=>{
    const selectedEl = document.querySelector(".image-item.selected");
    if (!selectedEl){
        toastr.error("Vui lòng chọn ảnh ");
    }
    const urlImage= selectedEl.querySelector("img").getAttribute("src");
    thumbnailPreview.setAttribute("src",urlImage);
    thumbnailPreview.src = urlImage;
    $('#modal-image').modal('hide');

})
// xóa ảnh
btnDeleteImage.addEventListener('click',()=>{
    const selectedEl = document.querySelector(".image-item.selected");
    if (!selectedEl){
        toastr.error("Vui lòng chọn ảnh cần xóa ");
    }
    const imageId= selectedEl.getAttribute("data-id");
    axios.delete(`/api/images/delete/hotel/${imageId}`)
        .then((res)=>{
            imageList = imageList.filter(image => image.id!==imageId);
            renderPagination(imageList);
            btnChoseImage.disabled=true;
            btnDeleteImage.disabled = true;
            toastr.success("Xóa ảnh thành công ");

        })
        .catch((err)=>{
            console.log(err);
        })

})
const  renderPagination = (images) =>{
    $('#image-pagination').pagination({
        dataSource:images,
        pageSize: 12,
        pageRange: null,
        showPageNumbers: true,
        callback: function(data, pagination) {
            renderImage(data);
        }
    })
}


renderImage(imageList);
renderPagination(imageList);