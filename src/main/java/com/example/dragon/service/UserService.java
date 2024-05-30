package com.example.dragon.service;

import com.example.dragon.entity.UserEntity;
import com.example.dragon.exeption.UserAlreadyExistException;
import com.example.dragon.exeption.UserNotFoundException;
import com.example.dragon.model.User;
import com.example.dragon.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public UserEntity createUser(UserEntity user) throws UserAlreadyExistException {
        if(userRepo.findByUsername(user.getUsername()) != null) {
            throw new UserAlreadyExistException("User already exist");
        }
        return userRepo.save(user);
    }

    public Iterable<UserEntity> getUsers() {
        return userRepo.findAll();
    }

    public User getUser(Long id) throws UserNotFoundException {
        UserEntity user = userRepo.findById(id).get();
        if(user == null) {
           throw new UserNotFoundException("User not found");
        }
        return User.toModel(user);
    }

    public User updateUser(Long id, UserEntity data) throws UserNotFoundException {
        UserEntity user = userRepo.findById(id).get();
        if(user == null) {
            throw new UserNotFoundException("User not found");
        }
        user.setUsername(data.getUsername());
        user.setPassword(data.getPassword());

        return User.toModel(userRepo.save(data));
    }

    public Long deleteUser(Long id) {
        userRepo.deleteById(id);
        return id;
    }
}
