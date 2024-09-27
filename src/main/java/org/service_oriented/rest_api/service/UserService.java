package org.service_oriented.rest_api.service;

import org.service_oriented.rest_api.model.dtos.SaveUserDTO;
import org.service_oriented.rest_api.model.dtos.UpdateUserDTO;
import org.service_oriented.rest_api.model.dtos.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    Page<UserDTO> getUsers(Pageable pageable);

    UserDTO getUser(Long id);

    UserDTO saveUser(SaveUserDTO dto);

    UserDTO updateUser(Long id, UpdateUserDTO dto);

    void deleteUser(Long id);

}
