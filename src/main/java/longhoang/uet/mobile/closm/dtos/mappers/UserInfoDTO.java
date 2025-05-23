package longhoang.uet.mobile.closm.dtos.mappers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {
    private String fullName;
    private String email;
    private String phone;
    private String role;
}
