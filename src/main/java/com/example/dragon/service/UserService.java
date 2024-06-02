package com.example.dragon.service;
import com.example.dragon.entity.UserEntity;
import com.example.dragon.dto.user.RequestUser;
import com.example.dragon.dto.user.ResponseUser;
import com.example.dragon.exception.UserAlreadyExistException;
import com.example.dragon.repository.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder encoder;

    public ResponseUser createUser(RequestUser user) throws UserAlreadyExistException {
        extracted(user);
        if(user.getRoles() == null) {
            user.setRoles("ROLE_USER");
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(encoder.encode(user.getPassword()));
        userEntity.setRoles(user.getRoles());
        return ResponseUser.toModel(userRepo.save(userEntity));
    }

    public List<ResponseUser> getUsers() {
        List<UserEntity> entities = userRepo.findAll();
        return entities.stream()
                .map(ResponseUser::toModel)
                .collect(Collectors.toList());
    }

    public ResponseUser getUser(Long id) {
        UserEntity entity = userRepo.findById(id).orElse(null);
        if (entity == null) {
            throw new EntityNotFoundException("User with id " + id + " not found");
        }
        return ResponseUser.toModel(entity);
    }

    public Long deleteUser(Long id) {
        try {
            userRepo.deleteById(id);
            return id;
        } catch (Exception e) {
            throw new InternalError("Something went wrong");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<UserEntity> userDetail = userRepo.findByUsername(username);
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new EntityNotFoundException("User not found " + username));
    }

    public ResponseUser createUserHasAuth(RequestUser user) throws UserAlreadyExistException {
        extracted(user);
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(encoder.encode(user.getPassword()));
        userEntity.setRoles("ROLE_USER");
        return ResponseUser.toModel(userRepo.save(userEntity));
    }

    private void extracted(RequestUser user) throws UserAlreadyExistException {
        Optional<UserEntity> userDB = userRepo.findByUsername(user.getUsername());
        if(userDB.isPresent()) {
            throw new UserAlreadyExistException("User with username " + user.getUsername() + " already exist");
        }
    }
}
