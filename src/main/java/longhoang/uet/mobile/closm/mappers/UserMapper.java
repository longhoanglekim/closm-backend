package longhoang.uet.mobile.closm.mappers;

import longhoang.uet.mobile.closm.dtos.mappers.UserInfoDTO;
import longhoang.uet.mobile.closm.models.User;

public class UserMapper {
    public static UserInfoDTO userToUserDTO(User user) {
        UserInfoDTO userDTO = new UserInfoDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setPhone(user.getPhone());
        userDTO.setFullName(user.getFullName());
        return userDTO;
    }
}
