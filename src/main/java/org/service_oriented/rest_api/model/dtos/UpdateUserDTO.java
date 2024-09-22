package org.service_oriented.rest_api.model.dtos;

import jakarta.validation.constraints.Email;
import lombok.*;
import org.service_oriented.rest_api.model.enums.UserRole;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDTO {
    private String name;
    @Email(message = "Email should be valid")
    private String email;
    private String phone;
    private AddressDTO address;
    private UserRole role;
}
