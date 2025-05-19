package longhoang.uet.mobile.closm.dtos.request;

import longhoang.uet.mobile.closm.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePaymentStatusRequest {
    private PaymentStatus status;
}
