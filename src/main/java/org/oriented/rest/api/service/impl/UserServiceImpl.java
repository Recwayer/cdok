package org.oriented.rest.api.service.impl;

import org.oriented.rest.api.mapper.UserMapper;
import org.oriented.rest.api.model.User;
import org.oriented.rest.api.service.UserService;
import org.service_oriented.dto.SaveUserDTO;
import org.service_oriented.dto.UpdateUserDTO;
import org.service_oriented.dto.UserDTO;
import org.service_oriented.exceptions.CustomExceptions;
import org.oriented.rabbitmq.producer.MessageEmailProducer;
import org.oriented.rest.api.model.enums.UserRole;
import org.oriented.rest.api.repository.UserRepository;
import org.service_oriented.rabitmq.model.EmailAction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final MessageEmailProducer messageEmailProducer;
    private UserRepository userRepository;
    private UserMapper userMapper;


    public UserServiceImpl(MessageEmailProducer messageEmailProducer, UserRepository userRepository, UserMapper userMapper) {
        this.messageEmailProducer = messageEmailProducer;
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
        UserDTO userDTO = userMapper.toUserDto(userRepository.save(userMapper.toUser(dto)));
        messageEmailProducer.sendMessage(userDTO, EmailAction.NEW);
        return userDTO;
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

        UserDTO userDTO = userMapper.toUserDto(userRepository.save(existingUser));
        messageEmailProducer.sendMessage(userDTO, EmailAction.UPDATE);
        return userDTO;
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
