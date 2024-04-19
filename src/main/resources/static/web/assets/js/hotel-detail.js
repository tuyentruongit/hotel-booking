flatpickr("#date-range", {
    mode: "range",
    altInput: true,
    altFormat: "j \\t\\h\\á\\n\\g n", // Custom altFormat
    dateFormat: "Y-m-d",
    minDate: "today", // Chỉ hiển thị ngày từ ngày hiện tại
    defaultDate: [new Date().fp_incr(4), new Date().fp_incr(8)],
    showMonths: 2,
    separator: "",
    locale: "vi",

});