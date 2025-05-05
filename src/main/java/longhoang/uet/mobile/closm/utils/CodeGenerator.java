package longhoang.uet.mobile.closm.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class CodeGenerator {
    public static String generateOrderCode() {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        int randomPart = 100 + new Random().nextInt(900); // random 3 số
        return timestamp + randomPart;
    }
}
