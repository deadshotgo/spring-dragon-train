package com.example.dragon.dto.user;

import com.example.dragon.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseUser {
    private Long id;
    private String username;
    private String role;

    public static ResponseUser toModel(UserEntity entity) {
        ResponseUser model = new ResponseUser();
        model.setId(entity.getId());
        model.setUsername(entity.getUsername());
        model.setRole(entity.getRoles());
        return model;
    }
}
