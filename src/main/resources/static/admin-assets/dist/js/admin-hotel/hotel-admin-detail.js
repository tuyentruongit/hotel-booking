const nameHotel = document.getElementById('nameHotel');
const descriptionEl = document.getElementById('description');
const cityEl = document.getElementById("city");
const  statusEl = document.getElementById("status")
const btnSave = document.getElementById("btn-Hotel");
const  phoneHotelEl = document.getElementById("phoneHotel");
const emailHotelEl = document.getElementById("emailHotel");
const rentalHotelEl = document.getElementById("rentalType");
const addressHotelEl = document.getElementById("addressHotel");
let idHotel = hotelCurrent.id;
const renderDataHotel = ()=>{
    nameHotel.value=hotelCurrent.name;
    descriptionEl.value=hotelCurrent.description;
    cityEl.value = hotelCurrent.city.id;
    statusEl.value=hotelCurrent.status ? '1' : '0';
    phoneHotelEl.value = hotelCurrent.hotline;
    emailHotelEl.value =hotelCurrent.email;
    rentalHotelEl.value = hotelCurrent.rentalType;
    addressHotelEl.value = hotelCurrent.address;
}
btnSave.addEventListener('click' ,(e)=>{
    e.preventDefault();
    if (!$('#form-update-hotel').valid()) return;
    let status = false;
    if (statusEl.value==='1'){
        status=true;
    }
    console.log(parseInt(cityEl.value))
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
    console.log(data)
    axios.put(`/api/hotel/admin/update/${idHotel}`,data)
        .then((res)=>{
            toastr.success("Lưu thông tin thành công ");
            setTimeout(() => {
                window.location.href = "/admin/hotels";
            }, 1500);
        })
        .catch((err)=>{
            console.log(err)
            toastr.error(err.response.data.message);
        })
})
$('#form-update-hotel').validate({
    rules: {
        title: {
            required: true,
        },
        director: {
            required: true,
        },
        description: {
            required: true
        },
        actor: {
            required: true
        },
        genre: {
            required: true
        },
        relishedAt: {
            required: true
        },
        thumbnail: {
            required: true
        }
    }, messages: {
        title: {
            required: "Vui lòng nhập tiêu đề phim",

        }, director: {
            required: "Vui lòng nhập đạo diễn bộ phim",

        }, description: {
            required: "Vui lòng nhập mô tả cho phim"
        }
        , actor: {
            required: "Vui lòng nhập diễn viên"
        }
        , genre: {
            required: "Vui lòng nhập thể loại "
        }
        , relishedAt: {
            required: "Vui lòng nhập năm sản xuất"
        }
        , thumbnail: {
            required: "Vui lòng chọn hình ảnh"
        }
    }, errorElement: 'span', errorPlacement: function (error, element) {
        error.addClass('invalid-feedback');
        element.closest('.form-group').append(error);
    }, highlight: function (element, errorClass, validClass) {
        $(element).addClass('is-invalid');
    }, unhighlight: function (element, errorClass, validClass) {
        $(element).removeClass('is-invalid');
    }
});

const deleteMovie = document.getElementById("deleteHotel");
deleteMovie.addEventListener('click', () => {
    const isConfirm = confirm("Bạn có chắc mình muốn xóa bộ phim này không?");
    if (!isConfirm) return;
    console.log(idHotel)
    axios.delete("/api/hotel/admin/delete/" + idHotel)
        .then((res) => {
            toastr.success("Xóa Thành Công ");
            setTimeout(() => {
                window.location.href = "/admin/hotels";
            }, 1500);
        })
        .catch((err) => {
            console.log(err)
            // toastr.error(err.response.data.message);
        })

});
// const previewVideo = (episodeId) => {
//     // Tìm kiếm tập phim theo id
//     const episode = episodeList.find(e => e.id === episodeId)
//
//     // Lấy ra thẻ video và gán src cho nó
//     const videoEl = document.querySelector('#modal-preview-video video')
//     videoEl.src = episode.videoUrl;
//     // Hiển thị modal
//     $('#modal-preview-video').modal('show')
// }
//
// // Nếu đóng modal preview video thì reset lại src của video
// $('#modal-preview-video').on('hidden.bs.modal', function (event) {
//     const videoEl = document.querySelector('#modal-preview-video video')
//     videoEl.src = "";
// })

// const uploadVideo = (event, episodeId) => {
//     const file = event.target.files[0];
//
//     // Tạo đối tượng form data để đưa dữ liệu vào gửi đi
//     const formData = new FormData()
//     formData.append('file', file)
//
//     // Gọi api sử dung axios
//     axios.post(`/api/admin/episodes/${episodeId}/upload-video`, formData)
//         .then(res => {
//             toastr.success('Upload video thành công.')
//
//             // Reload lại trang sau 1.5s
//             setTimeout(() => {
//                 location.reload()
//             }, 1500)
//         })
//         .catch(err => {
//             console.log(err)
//             toastr.error(err.response.data.message)
//         })
// }
// const titleEpisode = document.getElementById("title-episode");
// const statusEpisode = document.getElementById("status-episode");
// const saveEpisode = document.getElementById('save-episode');
// saveEpisode.addEventListener('click', (e) => {
//     e.preventDefault();
//     if (!$('#form-create-episode').valid()) return;
//     let status = false;
//     if (statusEpisode.getAttribute("value") === "true") {
//         status = true;
//     }
//     const data = {
//         idHotel: hotelCurrent.id,
//         title: titleEpisode.value,
//         status: status,
//         displayOder: episodeList.length + 1
//     }
//     console.log(data);
//     axios.post(`/api/admin/episodes`, data)
//         .then(res => {
//             toastr.success('Thêm mới thành công.')
//            setTimeout(()=>{
//                window.location.reload()
//            },1500)
//         })
//         .catch(err => {
//             console.log(err)
//             toastr.error(err.response.data.message)
//         })
//
// })
// $('#form-create-episode').validate({
//     rules: {
//         titleEpisode: {
//             required: true,
//         },
//     }, messages: {
//         title: {
//             required: "Vui lòng nhập title",
//
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
//
// const episodeListEL = document.getElementById('episode-list');
// const renderEpisode = (episodeList) => {
//     let htmlEpisode = "";
//     episodeList.forEach(episode => {
//         htmlEpisode += `<tr>
//                                     <td>${episode.displayOder}</td>
//                                     <td >${episode.title}</td>
//                                     <td >${episode.videoUrl}</td>
//                                     <td >${episode.duration}</td>
//                                      <td >${episode.status===true ? 'Công Khai' : 'Nháp' }</td>
//
//                                     <td>
//                                         <label for="|episode-input-${episode.id}|"
//                                                class="btn btn-primary btn-sm mb-0"><i
//                                                 class="fas fa-upload"></i></label>
//                                         <input type="file" class="d-none" id="|episode-input-${episode.id}|"
//                                                onclick="uploadVideo(${episode.id})">
//                                         <button class="btn btn-warning btn-sm text-white"
//                                                 onclick="previewVideo(e,${episode.id})">
//                                             <i class="fas fa-play"></i>
//                                         </button>
//                                         <button class="btn btn-success btn-sm"><i class="fas fa-pencil-alt"></i>
//                                         </button>
//                                         <button class="btn btn-danger btn-sm"><i class="fas fa-trash-alt"></i></button>
//                                     </td>
//                                   </tr>`
//     });
//     episodeListEL.innerHTML = htmlEpisode;
// };
// console.log(episodeList);
// renderEpisode(episodeList);
renderDataHotel();

































