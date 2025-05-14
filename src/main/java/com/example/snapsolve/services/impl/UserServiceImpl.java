package com.example.snapsolve.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.snapsolve.dto.user.PasswordChangeDTO;

import com.example.snapsolve.dto.user.UserDTO;

import com.example.snapsolve.exception.ResourceNotFoundException;
import com.example.snapsolve.models.User;
import com.example.snapsolve.repositories.UserRepository;
import com.example.snapsolve.services.UserService;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    // Chuyển đổi User sang UserDTO
    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setUserRank(user.getUserRank());
        userDTO.setStatusMessage(user.getStatusMessage());
        userDTO.setStudentInformation(user.getStudentInformation());
        userDTO.setSuid(user.getSuid());
        userDTO.setAvatarUrl(user.getAvatarUrl());
       
        
        return userDTO;
    }
    
    @Override
    public List<UserDTO> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public UserDTO findUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return convertToDTO(user);
    }
    
    @Override
    public UserDTO findUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        return convertToDTO(user);
    }
    
    @Override
    public UserDTO findUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        return convertToDTO(user);
    }
    
    @Override
    public UserDTO createUser(UserDTO userCreateDTO) {
        // Kiểm tra username, email và số điện thoại đã tồn tại chưa
        if (userRepository.existsByUsername(userCreateDTO.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        
        if (userRepository.existsByEmail(userCreateDTO.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        
        if (userRepository.existsByPhoneNumber(userCreateDTO.getPhoneNumber())) {
            throw new IllegalArgumentException("Phone number already exists");
        }
        
        User user = new User();
           
        if (userCreateDTO.getUserRank() == null) {
                user.setUserRank("normal");
        }
        if(userCreateDTO.getSuid() == null)
        {
            String SUID = UUID.randomUUID().toString();
            user.setSuid(SUID);
        }
        
        user.setUsername(userCreateDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));
        user.setEmail(userCreateDTO.getEmail());
        user.setPhoneNumber(userCreateDTO.getPhoneNumber());
        
        user.setStatusMessage(userCreateDTO.getStatusMessage());
        user.setStudentInformation(userCreateDTO.getStudentInformation());
        
        user.setAvatarUrl(userCreateDTO.getAvatarUrl());
        
     
        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }
    
    @Override
    public UserDTO updateUser(Long id, UserDTO userUpdateDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        
        // Kiểm tra nếu username hoặc email đã tồn tại với người dùng khác
        if (userUpdateDTO.getUsername() != null && 
            !user.getUsername().equals(userUpdateDTO.getUsername()) &&
            userRepository.existsByUsername(userUpdateDTO.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        
        if (userUpdateDTO.getEmail() != null && 
            !user.getEmail().equals(userUpdateDTO.getEmail()) &&
            userRepository.existsByEmail(userUpdateDTO.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        
        if (userUpdateDTO.getPhoneNumber() != null && 
            !user.getPhoneNumber().equals(userUpdateDTO.getPhoneNumber()) &&
            userRepository.existsByPhoneNumber(userUpdateDTO.getPhoneNumber())) {
            throw new IllegalArgumentException("Phone number already exists");
        }
        
        // Cập nhật thông tin người dùng
        if (userUpdateDTO.getUsername() != null) {
            user.setUsername(userUpdateDTO.getUsername());
        }
        
        if (userUpdateDTO.getEmail() != null) {
            user.setEmail(userUpdateDTO.getEmail());
        }
        
        if (userUpdateDTO.getPhoneNumber() != null) {
            user.setPhoneNumber(userUpdateDTO.getPhoneNumber());
        }
        
        if (userUpdateDTO.getUserRank() != null) {
            user.setUserRank(userUpdateDTO.getUserRank());
        }
        
    
        
        User updatedUser = userRepository.save(user);
        return convertToDTO(updatedUser);
    }
    
    @Override
    public boolean changePassword(Long id, PasswordChangeDTO passwordChangeDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        
        // Kiểm tra mật khẩu cũ
        if (!passwordEncoder.matches(passwordChangeDTO.getOldPassword(), user.getPassword())) {
            return false;
        }
        
        // Cập nhật mật khẩu mới
        user.setPassword(passwordEncoder.encode(passwordChangeDTO.getNewPassword()));
        userRepository.save(user);
        return true;
    }
    
    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
    
    @Override
    public boolean isEmailExists(String email) {
        return userRepository.existsByEmail(email);
    }
    
    @Override
    public boolean isPhoneNumberExists(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }
    
    @Override
    public boolean isUsernameExists(String username) {
        return userRepository.existsByUsername(username);
    }
}