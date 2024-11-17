package org.service_oriented.rest_api.service.impl;

import org.service_oriented.dto.SaveUserDTO;
import org.service_oriented.dto.UpdateUserDTO;
import org.service_oriented.dto.UserDTO;
import org.service_oriented.exceptions.CustomExceptions;
import org.service_oriented.rest_api.mapper.UserMapper;
import org.service_oriented.rest_api.model.User;
import org.service_oriented.rest_api.model.enums.UserRole;
import org.service_oriented.rest_api.repository.UserRepository;
import org.service_oriented.rest_api.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private UserMapper userMapper;


    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public Page<UserDTO> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toUserDto);
    }

    @Override
    public UserDTO getUser(Long id) {
        return userMapper.toUserDto(findUserById(id));
    }

    @Override
    @Transactional
    public UserDTO saveUser(SaveUserDTO dto) {
        return userMapper.toUserDto(userRepository.save(userMapper.toUser(dto)));
    }

    @Override
    @Transactional
    public UserDTO updateUser(Long id, UpdateUserDTO dto) {
        User existingUser = findUserById(id);

        Optional.ofNullable(dto.name()).ifPresent(existingUser::setName);
        Optional.ofNullable(dto.email()).ifPresent(existingUser::setEmail);
        Optional.ofNullable(dto.phone()).ifPresent(existingUser::setPhone);
        Optional.ofNullable(dto.address()).ifPresent(addressDto -> existingUser.setAddress(userMapper.mapAddressDTOToAddress(addressDto)));
        Optional.ofNullable(dto.role()).ifPresent(userRole -> existingUser.setRole(UserRole.valueOf(userRole.name())));

        return userMapper.toUserDto(userRepository.save(existingUser));
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new CustomExceptions.UserNotFoundException("User not found with id: " + id);
        }
    }

    private User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomExceptions.UserNotFoundException("User not found with id: " + id));
    }


}
