package methodsecuritynew.bookingapp.model.statics;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentMethod {
    CASH("Tiền mặt"),
    CREDIT_CARD("Thẻ tín dụng"),
    DEBIT_CARD("Thẻ ghi nợ"),
    BANK_TRANSFER("Chuyển khoản ngân hàng"),
    PAYPAL("PayPal"),
    APPLE_PAY("Apple Pay"),
    GOOGLE_PAY("Google Pay");

    private final String value;
}
