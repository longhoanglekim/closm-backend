package longhoang.uet.mobile.closm.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import longhoang.uet.mobile.closm.dtos.response.Location;
import longhoang.uet.mobile.closm.dtos.response.RouteInfo;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class LocationUtil {
    private static final String SEARCH_API_URL = "https://nominatim.openstreetmap.org/search";
    private static final String FORMAT_PARAM = "&format=json&addressdetails=1&limit=1";
    private static final String DISTANCE_API_URL = "https://api.openrouteservice.org/v2/directions/driving-car/geojson";

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
        String fullUrl = SEARCH_API_URL + "?q=" + encodedAddress + FORMAT_PARAM;

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
    public static RouteInfo calculateDistanceAndDuration(Location src, Location dst) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(DISTANCE_API_URL).openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", EvnLoader.getDistanceApiKey()  );
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            double lonStartDouble = Double.parseDouble(src.getLon());
            double latStartDouble = Double.parseDouble(src.getLat());
            double lonEndDouble = Double.parseDouble(dst.getLon());
            double latEndDouble = Double.parseDouble(dst.getLat());

            // Sử dụng các giá trị kiểu double cho phép String.format() hoạt động đúng
            String jsonInputString = String.format(
                    "{\"coordinates\":[[%f,%f],[%f,%f]]}",
                    lonStartDouble, latStartDouble, lonEndDouble, latEndDouble
            );

            StringBuilder response = getStringBuilder(conn, jsonInputString);

            conn.disconnect();

            // Parse kết quả JSON
            ObjectMapper mapper = new ObjectMapper();
            var jsonNode = mapper.readTree(response.toString());
            var summary = jsonNode
                    .path("features").get(0)
                    .path("properties")
                    .path("summary");

            double distanceInKm = summary.path("distance").asDouble() / 1000.0;
            double durationInMinutes = summary.path("duration").asDouble() / 60.0;

            return new RouteInfo(distanceInKm, durationInMinutes);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static RouteInfo calculateDistanceFromShop (String dest) {
        Location src =  searchLocation(EvnLoader.getShopLocation());
        Location dst =  searchLocation(dest);
        return calculateDistanceAndDuration(src, dst);
    }


    private static StringBuilder getStringBuilder(HttpURLConnection conn, String jsonInputString) throws IOException {
        try (var os = conn.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8)
        );

        StringBuilder response = new StringBuilder();
        String output;
        while ((output = br.readLine()) != null) {
            response.append(output);
        }
        return response;
    }


}
