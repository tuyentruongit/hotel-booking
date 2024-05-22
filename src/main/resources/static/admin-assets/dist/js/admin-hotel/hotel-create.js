const nameHotel = document.getElementById('nameHotel');
const descriptionEl = document.getElementById('description');
const cityEl = document.getElementById("city");
const  statusEl = document.getElementById("status")
const btnSave = document.getElementById("btn-create-hotel");
const  phoneHotelEl = document.getElementById("phoneHotel");
const emailHotelEl = document.getElementById("emailHotel");
const rentalHotelEl = document.getElementById("rentalType");
const addressHotelEl = document.getElementById("addressHotel");




btnSave.addEventListener('click' ,(e)=>{
    e.preventDefault();
    if (!$('#form-create-hotel').valid()) return;
    let status = false;
    if (statusEl.getAttribute("value")==="true"){
        status=true;
    }
    const data = {
        name : nameHotel.value,
        description : descriptionEl.value,
        rentalType : rentalHotelEl.value,
        phoneHotel : phoneHotelEl.value,
        status : status,
        email : emailHotelEl.value,
        addressHotel : addressHotelEl.value,
        idCity :  parseInt(cityEl.value),
    }
    console.log(data);
    axios.post("/api/hotel/admin/create",data)
        .then((res)=>{
            toastr.success("Tạo khách sạn mới thành công");
            setTimeout(() => {
                window.location.href = "/admin/hotels";
            }, 1500);
        })
        .catch((err)=>{
           console.log(err)
        })
})


// $('#form-create-hotel').validate({
//     rules: {
//         title: {
//             required: true,
//         },
//         director: {
//             required: true,
//         },
//         description: {
//             required: true
//         },
//         actor: {
//             required: true
//         },
//         genre: {
//             required: true
//         },
//         relishedAt: {
//             required: true
//         },
//         thumbnail: {
//             required: true
//         }
//     }, messages: {
//         title: {
//             required: "Vui lòng nhập tiêu đề phim",
//
//         }, director: {
//             required: "Vui lòng nhập đạo diễn bộ phim",
//
//         }, description: {
//             required: "Vui lòng nhập mô tả cho phim"
//         }
//         , actor: {
//             required: "Vui lòng nhập diễn viên"
//         }
//         , genre: {
//             required: "Vui lòng nhập thể loại "
//         }
//         , relishedAt: {
//             required: "Vui lòng nhập năm sản xuất"
//         }
//         , thumbnail: {
//             required: "Vui lòng chọn hình ảnh"
//         }
//     }, errorElement: 'span', errorPlacement: function (error, element) {
//         error.addClass('invalid-feedback');
//         element.closest('.form-group').append(error);
//     }, highlight: function (element, errorClass, validClass) {
//         $(element).addClass('is-invalid');
//     }, unhighlight: function (element, errorClass, validClass) {
//         $(element).removeClass('is-invalid');
//     }
// });




































