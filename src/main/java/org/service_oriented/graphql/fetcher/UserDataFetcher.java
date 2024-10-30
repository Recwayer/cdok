package org.service_oriented.graphql.fetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import org.service_oriented.rest_api.model.dtos.SaveUserDTO;
import org.service_oriented.rest_api.model.dtos.UpdateUserDTO;
import org.service_oriented.rest_api.model.dtos.UserDTO;
import org.service_oriented.rest_api.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@DgsComponent
public class UserDataFetcher {

    private final UserService userService;

    public UserDataFetcher(UserService userService) {
        this.userService = userService;
    }

    @DgsQuery
    public Page<UserDTO> getAllUsers(int page, int size) {
        return userService.getUsers(PageRequest.of(page, size));
    }

    @DgsQuery
    public UserDTO getUserById(Long id) {
        return userService.getUser(id);
    }

    @DgsMutation
    public UserDTO createUser(SaveUserDTO user) {
        return userService.saveUser(user);
    }

    @DgsMutation
    public UserDTO updateUser(Long id, UpdateUserDTO user) {
        return userService.updateUser(id, user);
    }

    @DgsMutation
    public Boolean deleteUser(Long id) {
        userService.deleteUser(id);
        return true;
    }
}


