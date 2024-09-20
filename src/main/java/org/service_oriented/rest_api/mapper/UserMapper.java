package org.service_oriented.rest_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.service_oriented.rest_api.model.User;
import org.service_oriented.rest_api.model.dtos.SaveUserDTO;
import org.service_oriented.rest_api.model.dtos.UpdateUserDTO;
import org.service_oriented.rest_api.model.dtos.UserDTO;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    public abstract UserDTO toUserDto(User user);

    public abstract User toUser(UserDTO userDTO);

    public abstract User toUser(SaveUserDTO userDTO);

    public abstract User toUser(UpdateUserDTO userDTO);

}
