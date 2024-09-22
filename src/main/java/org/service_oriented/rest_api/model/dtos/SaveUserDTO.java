package org.service_oriented.rest_api.model.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.service_oriented.rest_api.model.enums.UserRole;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveUserDTO {
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    @NotBlank(message = "Phone is required")
    private String phone;
    private AddressDTO address;
    @NotNull(message = "Role is required")
    private UserRole role;
}
