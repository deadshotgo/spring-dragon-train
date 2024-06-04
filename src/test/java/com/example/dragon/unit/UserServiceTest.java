package com.example.dragon.unit;

import com.example.dragon.dto.user.RequestUser;
import com.example.dragon.dto.user.ResponseUser;
import com.example.dragon.entity.UserEntity;
import com.example.dragon.exception.UserAlreadyExistException;
import com.example.dragon.repository.UserRepo;
import com.example.dragon.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private UserService userService;

    @Test
    void testGetCompetitions() {
        // Arrange
        UserEntity user = createUSerEntity(1L, "admin 1");
        UserEntity user2 = createUSerEntity(2L, "admin 2");
        when(userRepo.findAll()).thenReturn(Arrays.asList(user, user2));

        // Act
        List<ResponseUser> result = userService.getUsers();

        // Assert
        assertEquals(2, result.size());
        assertEquals("admin 1", result.get(0).getUsername());
        assertEquals("admin 2", result.get(1).getUsername());
    }

    @Test
    void testGetUser_WhenIdExists() {
        // Arrange
        Long id = 1L;
        String username = "admin 1";
        UserEntity user = createUSerEntity(1L, username);
        when(userRepo.findById(id)).thenReturn(Optional.of(user));

        // Act
        ResponseUser response = userService.getUser(id);

        // Assert
        assertEquals(username, response.getUsername());
    }

    @Test
    public void testGetUser_WhenIdNotExists() {
        // Arrange
        Long id = 1L;
        when(userRepo.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> userService.getUser(id));
    }

    @Test
    public void testCreateUSer_WhenUsernameIsValid() throws UserAlreadyExistException {
        // Arrange
        Long id = 1L;
        String username = "admin 1";
        String password = "password";
        RequestUser requestUser = new RequestUser(username, password, "ROLE_USER");
        UserEntity user = createUSerEntity(id, username);
        when(encoder.encode(password)).thenReturn("aklshdjkasjkdasjkdjkashdkjaklmcklnkasjndkl");
        when(userRepo.save(any(UserEntity.class))).thenReturn(user);

        // Act
        ResponseUser result = userService.createUser(requestUser);

        // Assert
        assertEquals(username, result.getUsername());
    }

    @Test
    public void testDeleteUser_WhenIdExists() {
        // Arrange
        Long id = 1L;
        // Act
        Long result = userService.deleteUser(id);

        // Assert
        assertEquals(id, result);
        verify(userRepo, times(1)).deleteById(id);
    }

    private UserEntity createUSerEntity(Long id, String username) {
        return new UserEntity(id, username, "password", "USER_ROLE");
    }
}
