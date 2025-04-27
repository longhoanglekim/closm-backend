package longhoang.uet.mobile.closm.configs.payment;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.Getter;
import longhoang.uet.mobile.closm.utils.payment.VNPayUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

@Configuration
public class VNPAYConfig {
    public static Dotenv dotenv = Dotenv.load();

    @Getter
    private String vnp_PayUrl = dotenv.get("PAYMENT_VNPAY_URL");

    @Getter
    private String vnp_ReturnUrl = dotenv.get("PAYMENT_VNPAY_RETURN_URL");

    @Getter
    private String vnp_TmnCode = dotenv.get("PAYMENT_VNPAY_TMN_CODE");

    @Getter
    private String secretKey = dotenv.get("PAYMENT_VNPAY_SECRET_KEY");

    @Getter
    private String vnp_Version = dotenv.get("PAYMENT_VNPAY_VERSION");

    @Getter
    private String vnp_Command = dotenv.get("PAYMENT_VNPAY_COMMAND");

    @Getter
    private String orderType = dotenv.get("PAYMENT_VNPAY_ORDER_TYPE");

    public Map<String, String> getVNPayConfig() {
        Map<String, String> vnpParamsMap = new HashMap<>();
        vnpParamsMap.put("vnp_Version", this.vnp_Version);
        vnpParamsMap.put("vnp_Command", this.vnp_Command);
        vnpParamsMap.put("vnp_TmnCode", this.vnp_TmnCode);
        vnpParamsMap.put("vnp_CurrCode", "VND");
        vnpParamsMap.put("vnp_TxnRef",  VNPayUtil.getRandomNumber(8));
        vnpParamsMap.put("vnp_OrderInfo", "Thanh toan don hang:" +  VNPayUtil.getRandomNumber(8));
        vnpParamsMap.put("vnp_OrderType", this.orderType);
        vnpParamsMap.put("vnp_Locale", "vn");
        vnpParamsMap.put("vnp_ReturnUrl", this.vnp_ReturnUrl);
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnpCreateDate = formatter.format(calendar.getTime());
        vnpParamsMap.put("vnp_CreateDate", vnpCreateDate);
        calendar.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(calendar.getTime());
        vnpParamsMap.put("vnp_ExpireDate", vnp_ExpireDate);
        return vnpParamsMap;
    }
}