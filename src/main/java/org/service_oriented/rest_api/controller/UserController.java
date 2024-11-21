package org.service_oriented.rest_api.controller;

import jakarta.validation.Valid;
import org.service_oriented.controllers.UserApi;
import org.service_oriented.dto.SaveUserDTO;
import org.service_oriented.dto.UpdateUserDTO;
import org.service_oriented.dto.UserDTO;
import org.service_oriented.rest_api.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/user")
public class UserController implements UserApi {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public PagedModel<EntityModel<UserDTO>> getAllUsers(
            @SortDefault(sort = "id", direction = Sort.Direction.ASC)
            @PageableDefault(size = 10) Pageable pageable) {

        Page<UserDTO> users = userService.getUsers(pageable);

        List<EntityModel<UserDTO>> userModels = users.stream()
                .map(user -> EntityModel.of(user,
                        linkTo(methodOn(UserController.class).getUserById(user.id())).withSelfRel()))
                .collect(Collectors.toList());

        PagedModel<EntityModel<UserDTO>> pagedModel = PagedModel.of(userModels,
                new PagedModel.PageMetadata(users.getSize(), users.getNumber(), users.getTotalElements()));

        String selfUrl = String.format("%s?page=%d&size=%d",
                linkTo(methodOn(UserController.class).getAllUsers(pageable)).toUri().toString(),
                pageable.getPageNumber(), pageable.getPageSize());
        pagedModel.add(Link.of(selfUrl, "self"));

        if (users.hasNext()) {
            String nextUrl = String.format("%s?page=%d&size=%d",
                    linkTo(methodOn(UserController.class).getAllUsers(pageable)).toUri().toString(),
                    pageable.getPageNumber() + 1, pageable.getPageSize());
            pagedModel.add(Link.of(nextUrl, "next"));
        }
        if (users.hasPrevious()) {
            String prevUrl = String.format("%s?page=%d&size=%d",
                    linkTo(methodOn(UserController.class).getAllUsers(pageable)).toUri().toString(),
                    pageable.getPageNumber() - 1, pageable.getPageSize());
            pagedModel.add(Link.of(prevUrl, "prev"));
        }

        return pagedModel;
    }

    @GetMapping("/{id}")
    public EntityModel<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO user = userService.getUser(id);
        return EntityModel.of(user,
                linkTo(methodOn(UserController.class).getUserById(id)).withSelfRel());
    }

    @PostMapping
    public EntityModel<UserDTO> createUser(@Valid @RequestBody SaveUserDTO userDTO) {
        UserDTO createdUser = userService.saveUser(userDTO);
        return EntityModel.of(createdUser,
                linkTo(methodOn(UserController.class).getUserById(createdUser.id())).withSelfRel());
    }

    @PatchMapping("/{id}")
    public EntityModel<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UpdateUserDTO userDTO) {
        UserDTO updatedUser = userService.updateUser(id, userDTO);
        return EntityModel.of(updatedUser,
                linkTo(methodOn(UserController.class).getUserById(updatedUser.id())).withSelfRel());
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}

