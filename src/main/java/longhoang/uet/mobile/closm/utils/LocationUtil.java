package longhoang.uet.mobile.closm.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import longhoang.uet.mobile.closm.dtos.response.Location;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.net.URL;
import java.nio.charset.StandardCharsets;
@Slf4j
public class LocationUtil {

    private static final String API_URL = "https://nominatim.openstreetmap.org/search";
    private static final String FORMAT_PARAM = "&format=json&addressdetails=1&limit=1";

    // Tìm kiếm địa điểm từ địa chỉ
    public static Location searchLocation(String address) {
        try {
            String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8);
            BufferedReader reader = getBufferedReader(encodedAddress);
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            return parseLocationFromJson(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static BufferedReader getBufferedReader(String encodedAddress) throws IOException {
        String fullUrl = API_URL + "?q=" + encodedAddress + FORMAT_PARAM;

        HttpURLConnection conn = (HttpURLConnection) new URL(fullUrl).openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");

        // Kiểm tra phản hồi HTTP
        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("HTTP GET Request Failed. Response Code: " + conn.getResponseCode());
        }

        // Đọc dữ liệu từ phản hồi
        return new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
    }

    // Chuyển đổi chuỗi JSON thành đối tượng Location
    private static Location parseLocationFromJson(String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // Trả về đối tượng Location đầu tiên từ JSON (nếu có)
            Location[] locations = objectMapper.readValue(jsonString, Location[].class);
            return locations.length > 0 ? locations[0] : null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
