package org.service_oriented.rest_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.service_oriented.dto.AddressDTO;
import org.service_oriented.dto.SaveUserDTO;
import org.service_oriented.dto.UpdateUserDTO;
import org.service_oriented.dto.UserDTO;
import org.service_oriented.rest_api.model.Address;
import org.service_oriented.rest_api.model.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class UserMapper {
    public abstract UserDTO toUserDto(User user);

    public abstract User toUser(UserDTO userDTO);

    public abstract User toUser(SaveUserDTO userDTO);

    public abstract User toUser(UpdateUserDTO userDTO);

    public abstract Address mapAddressDTOToAddress(AddressDTO addressDTO);

}
