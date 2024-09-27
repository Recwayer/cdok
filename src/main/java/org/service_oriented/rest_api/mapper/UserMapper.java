package org.service_oriented.rest_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.service_oriented.rest_api.model.User;
import org.service_oriented.rest_api.model.dtos.SaveUserDTO;
import org.service_oriented.rest_api.model.dtos.UpdateUserDTO;
import org.service_oriented.rest_api.model.dtos.UserDTO;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class UserMapper {
    public abstract UserDTO toUserDto(User user);

    public abstract User toUser(UserDTO userDTO);

    public abstract User toUser(SaveUserDTO userDTO);

    public abstract User toUser(UpdateUserDTO userDTO);

}
