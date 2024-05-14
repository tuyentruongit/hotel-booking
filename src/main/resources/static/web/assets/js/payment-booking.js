const btnBooking = document.querySelector('.verify-booking');

const nameCustomer = document.querySelector('.name-customer-value');
console.log(nameCustomer)
const emailCustomer = document.querySelector('.email-customer-value');
const phoneCustomer = document.querySelector('.phone-customer-value');
const paymentHotel = document.getElementById('payment-hotel');



// gọi api để tạo 1 booking
btnBooking.addEventListener('click', () => {
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
        price : 123456,
        paymentMethod : paymentHotel.value
    }
    console.log(data)
    axios.post("/api/booking/add", data)
        .then((response) => {
            console.log("Thành công");
        })
        .catch((error) => {
            console.log("Thất bại")
        })
})