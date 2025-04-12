package longhoang.uet.mobile.closm.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    @JsonProperty("place_id")
    private long placeId;
    @JsonIgnore
    private String licence;

    @JsonProperty("osm_type")
    private String osmType;

    @JsonIgnore
    @JsonProperty("osm_id")
    private long osmId;

    private String lat;
    private String lon;

    @JsonProperty("class")
    private String placeClass;
    @JsonIgnore
    private String type;

    @JsonIgnore
    @JsonProperty("place_rank")
    private int placeRank;
    @JsonIgnore
    private double importance;

    private String addresstype;
    @JsonIgnore
    private String name;

    @JsonProperty("display_name")
    private String displayName;

    private Address address;
    @JsonIgnore
    private String[] boundingbox;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Address {

        @JsonProperty("house_number")
        private String houseNumber;

        private String road;
        private String quarter;
        private String suburb;
        private String city;

        @JsonProperty("ISO3166-2-lvl4")
        private String iso31662Lvl4;

        private String postcode;
        private String country;

        @JsonProperty("country_code")
        private String countryCode;
    }
}
