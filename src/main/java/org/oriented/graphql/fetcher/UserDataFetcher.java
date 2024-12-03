package org.oriented.graphql.fetcher;

import com.netflix.graphql.dgs.DgsComponent;
import org.oriented.rest.api.service.UserService;
import org.service_oriented.dto.SaveUserDTO;
import org.service_oriented.dto.UpdateUserDTO;
import org.service_oriented.dto.UserDTO;
import org.service_oriented.fetchers.UserDataFetcherApi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@DgsComponent
public class UserDataFetcher implements UserDataFetcherApi {

    private final UserService userService;

    public UserDataFetcher(UserService userService) {
        this.userService = userService;
    }


    public Page<UserDTO> getAllUsers(int page, int size) {
        return userService.getUsers(PageRequest.of(page, size));
    }


    public UserDTO getUserById(Long id) {
        return userService.getUser(id);
    }


    public UserDTO createUser(SaveUserDTO user) {
        return userService.saveUser(user);
    }


    public UserDTO updateUser(Long id, UpdateUserDTO user) {
        return userService.updateUser(id, user);
    }


    public Boolean deleteUser(Long id) {
        userService.deleteUser(id);
        return true;
    }
}


