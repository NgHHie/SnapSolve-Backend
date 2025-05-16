package com.example.snapsolve.services;


import com.example.snapsolve.dto.user.PasswordChangeDTO;

import com.example.snapsolve.dto.user.UserDTO;



import java.util.List;

public interface UserService {
    List<UserDTO> findAllUsers();
    UserDTO findUserById(Long id);
    UserDTO findUserByUsername(String username);
    UserDTO findUserByEmail(String email);
    
    UserDTO createUser(UserDTO userCreateDTO);
    UserDTO updateUser(Long id, UserDTO userUpdateDTO);
    boolean changePassword(Long id, PasswordChangeDTO passwordChangeDTO);
    void deleteUser(Long id);
    
    boolean isEmailExists(String email);
    boolean isPhoneNumberExists(String phoneNumber);
    boolean isUsernameExists(String username);
    UserDTO updateUserAvatar(Long id, String avatarUrl);
}