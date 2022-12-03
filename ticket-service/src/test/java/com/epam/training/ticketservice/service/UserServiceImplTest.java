package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.entity.UserEntity;
import com.epam.training.ticketservice.repository.UserRepository;
import com.epam.training.ticketservice.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl testUserService;

    private final UserEntity TEST_ADMIN = new UserEntity("admin", "admin", UserEntity.Role.ADMIN);

    @Test
    void testSignInPrivilegedShouldReturnTrueWhenGivenUsernameAndPasswordAreCorrect() {
        //Given
        Mockito.when(userRepository.findByUsernameAndPasswordAndRole(
                        TEST_ADMIN.getUsername(), TEST_ADMIN.getPassword(), TEST_ADMIN.getRole()
                )).thenReturn(Optional.of(TEST_ADMIN));

        //When
        final boolean actual = testUserService.signInPrivileged(TEST_ADMIN.getUsername(), TEST_ADMIN.getPassword());

        //Then
        Assertions.assertTrue(actual);
        Mockito.verify(userRepository).findByUsernameAndPasswordAndRole(
                TEST_ADMIN.getUsername(), TEST_ADMIN.getPassword(), TEST_ADMIN.getRole()
        );
    }

    @Test
    void testSignInPrivilegedShouldReturnFalseWhenGivenUsernameOrPasswordIsIncorrect() {
        //Given
        Mockito.when(userRepository.findByUsernameAndPasswordAndRole(
                TEST_ADMIN.getUsername(), TEST_ADMIN.getPassword(), TEST_ADMIN.getRole()
        )).thenReturn(Optional.empty());

        //When
        final boolean actual = testUserService.signInPrivileged(TEST_ADMIN.getUsername(), TEST_ADMIN.getPassword());

        //Then
        Assertions.assertFalse(actual);
        Mockito.verify(userRepository).findByUsernameAndPasswordAndRole(
                TEST_ADMIN.getUsername(), TEST_ADMIN.getPassword(), TEST_ADMIN.getRole()
        );
    }

    @Test
    void testSignOutShouldReturnTrueWhenSignedIn() {
        //Given
        Mockito.when(userRepository.findByUsernameAndPasswordAndRole(
                TEST_ADMIN.getUsername(), TEST_ADMIN.getPassword(), TEST_ADMIN.getRole()
        )).thenReturn(Optional.of(TEST_ADMIN));
        final boolean expected = testUserService.signInPrivileged(TEST_ADMIN.getUsername(), TEST_ADMIN.getPassword());

        //When
        final boolean actual = testUserService.signOut();

        //Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(userRepository).findByUsernameAndPasswordAndRole(
                TEST_ADMIN.getUsername(), TEST_ADMIN.getPassword(), TEST_ADMIN.getRole()
        );
    }

    @Test
    void testSignOutShouldReturnFalseWhenNotSignedIn() {
        //Given

        //When
        final boolean actual = testUserService.signOut();

        //Then
        Assertions.assertFalse(actual);
    }




}
