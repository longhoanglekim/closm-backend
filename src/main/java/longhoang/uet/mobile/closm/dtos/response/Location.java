package longhoang.uet.mobile.closm.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    private long placeId;
    private String licence;
    private String osmType;
    private long osmId;
    private String lat;
    private String lon;
    private String placeClass;
    private String type;
    private int placeRank;
    private double importance;
    private String addresstype;
    private String name;
    private String displayName;
    private Address address;
    private String[] boundingbox;



    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Address {
        private String houseNumber;
        private String road;
        private String quarter;
        private String suburb;
        private String city;
        private String iso31662Lvl4;
        private String postcode;
        private String country;
        private String countryCode;
    }
}
