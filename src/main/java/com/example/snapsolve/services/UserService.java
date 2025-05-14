package com.example.snapsolve.services;


import com.example.snapsolve.dto.user.PasswordChangeDTO;
import com.example.snapsolve.dto.user.UserCreateDTO;
import com.example.snapsolve.dto.user.UserDTO;
import com.example.snapsolve.dto.user.UserUpdateDTO;
import com.example.snapsolve.models.User;

import java.util.List;

public interface UserService {
    List<UserDTO> findAllUsers();
    UserDTO findUserById(Long id);
    UserDTO findUserByUsername(String username);
    UserDTO findUserByEmail(String email);
    
    UserDTO createUser(UserCreateDTO userCreateDTO);
    UserDTO updateUser(Long id, UserUpdateDTO userUpdateDTO);
    boolean changePassword(Long id, PasswordChangeDTO passwordChangeDTO);
    void deleteUser(Long id);
    
    boolean isEmailExists(String email);
    boolean isPhoneNumberExists(String phoneNumber);
    boolean isUsernameExists(String username);
}