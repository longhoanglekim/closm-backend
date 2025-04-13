package longhoang.uet.mobile.closm.dtos.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteInfo {
    private double distanceInKm;
    private double durationInMinutes;
}
