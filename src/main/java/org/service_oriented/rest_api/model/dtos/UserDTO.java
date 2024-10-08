package org.service_oriented.rest_api.model.dtos;

import lombok.*;
import org.service_oriented.rest_api.model.enums.UserRole;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private AddressDTO address;
    private UserRole role;
}
