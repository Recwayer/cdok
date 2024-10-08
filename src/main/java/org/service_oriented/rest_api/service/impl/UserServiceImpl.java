package org.service_oriented.rest_api.service.impl;

import org.service_oriented.rest_api.mapper.UserMapper;
import org.service_oriented.rest_api.model.User;
import org.service_oriented.rest_api.model.dtos.SaveUserDTO;
import org.service_oriented.rest_api.model.dtos.UpdateUserDTO;
import org.service_oriented.rest_api.model.dtos.UserDTO;
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
        return userMapper.toUserDto(userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id)));
    }

    @Override
    @Transactional
    public UserDTO saveUser(SaveUserDTO dto) {
        return userMapper.toUserDto(userRepository.save(userMapper.toUser(dto)));
    }

    @Override
    @Transactional
    public UserDTO updateUser(Long id, UpdateUserDTO dto) {
        UserDTO existingUser = getUser(id);

        Optional.ofNullable(dto.getName()).ifPresent(existingUser::setName);
        Optional.ofNullable(dto.getEmail()).ifPresent(existingUser::setEmail);
        Optional.ofNullable(dto.getPhone()).ifPresent(existingUser::setPhone);
        Optional.ofNullable(dto.getAddress()).ifPresent(existingUser::setAddress);
        Optional.ofNullable(dto.getRole()).ifPresent(existingUser::setRole);

        return userMapper.toUserDto(userRepository.save(userMapper.toUser(existingUser)));
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("User not found with id: " + id);
        }
    }


}
