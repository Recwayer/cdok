package org.oriented.rest.api.service;


import org.service_oriented.dto.SaveUserDTO;
import org.service_oriented.dto.UpdateUserDTO;
import org.service_oriented.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<UserDTO> getUsers(Pageable pageable);

    UserDTO getUser(Long id);

    UserDTO saveUser(SaveUserDTO dto);

    UserDTO updateUser(Long id, UpdateUserDTO dto);

    void deleteUser(Long id);

}
