package longhoang.uet.mobile.closm.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterInput {
    private String fullName;
    private String email;
    private String password;
    private String phone;
}
