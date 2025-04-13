package longhoang.uet.mobile.closm.utils;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Component;

@Component
public class EvnLoader {
    public static Dotenv dotenv = Dotenv.load();

    public static String getDistanceApiKey() {
        return dotenv.get("DISTANCE_API_KEY");
    }

    public static String getShopLocation() {
        return dotenv.get("SHOP_LOCATION");
    }
}
