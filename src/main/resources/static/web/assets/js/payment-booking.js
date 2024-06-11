const btnBooking = document.querySelector('.verify-booking');

const nameCustomer = document.querySelector('.name-customer-value');
console.log(nameCustomer)
const emailCustomer = document.querySelector('.email-customer-value');
const phoneCustomer = document.querySelector('.phone-customer-value');
const paymentHotel = document.getElementById('payment-hotel');




// gọi api để tạo 1 booking
btnBooking.addEventListener('click', () => {
   if (! $('#info-customer').valid()) return;
    const data = {
        idHotel: idHotel,
        idRoom: idRoom,
        nameCustomer: nameCustomer.value,
        emailCustomer: emailCustomer.value,
        phoneCustomer: phoneCustomer.value,
        guest: numGuest,
        numberRoom: numRoom,
        checkIn: startDate,
        checkOut: endDate,
        price : totalPrice,
        paymentMethod : paymentHotel.value
    }
    console.log(data)
    axios.post("/api/booking/add", data)
        .then((response) => {
            toastr.success("Đặt phòng thành công")
        })
        .catch((error) => {
            toastr.error("Khách sạn đã từ chối đơn đặt phòng của bạn")
        })
})

$('#info-customer').validate({
    rules: {
        nameCustomer: {
            required: true,
        },
        emailCustomer: {
            required: true,
            email : true
        },
        phoneCustomer: {
            required: true
        },
    },
    messages: {
        nameCustomer: {
            required: "Vui lòng nhập tên người lưu trú.",
        },
        emailCustomer: {
            required: "Vui lòng nhập email.",
            email : "Email không đúng định dạng."
        },
        phoneCustomer: {
            required: "Vui lòng số điện thoại của người lưu trú.",
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
