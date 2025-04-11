package longhoang.uet.mobile.closm.mappers;

import longhoang.uet.mobile.closm.dtos.response.UserDTO;
import longhoang.uet.mobile.closm.models.User;

public class UserMapper {
    public static UserDTO userToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setPhone(user.getPhone());
        userDTO.setFullName(user.getFullName());
        return userDTO;
    }
}
