package com.example.snapsolve.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.snapsolve.dto.mapper.UserMapper;
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
      @Autowired
    private UserMapper userMapper;
    
   
    
   @Override
    public List<UserDTO> findAllUsers() {
        return userMapper.convertToDTOList(userRepository.findAll());
    }
    
    @Override
    public UserDTO findUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return userMapper.convertToDTO(user);
    }

      @Override
    public UserDTO findUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        return userMapper.convertToDTO(user);
    }
   @Override
    public UserDTO findUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        return userMapper.convertToDTO(user);
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
        
        User user = userMapper.convertToEntity(userCreateDTO);
        
        // Xử lý các logic đặc biệt
        if (user.getUserRank() == null) {
            user.setUserRank("normal");
        }
        
        if (user.getSuid() == null) {
            String SUID = java.util.UUID.randomUUID().toString();
            user.setSuid(SUID);
        }
        
        // Mã hóa mật khẩu
        if (userCreateDTO.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));
        }
        
        User savedUser = userRepository.save(user);
        return userMapper.convertToDTO(savedUser);
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
        
        // Cập nhật thông tin người dùng từ DTO
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
        
        if (userUpdateDTO.getStatusMessage() != null) {
            user.setStatusMessage(userUpdateDTO.getStatusMessage());
        }
        
        if (userUpdateDTO.getStudentInformation() != null) {
            user.setStudentInformation(userUpdateDTO.getStudentInformation());
        }
        
        // Không cập nhật avatarUrl ở đây, sẽ có phương thức riêng
        
        User updatedUser = userRepository.save(user);
        return userMapper.convertToDTO(updatedUser);
    }
    
    @Override
    public UserDTO updateUserAvatar(Long id, String avatarUrl) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        
        // Lưu avatarUrl trong database (đường dẫn tương đối)
        user.setAvatarUrl(avatarUrl);
        User updatedUser = userRepository.save(user);
        
        return userMapper.convertToDTO(updatedUser);
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