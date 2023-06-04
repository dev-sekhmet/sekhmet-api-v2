package com.sekhmet.api.sekhmetapi.web.rest;


import com.amazonaws.services.s3.model.S3Object;
import com.sekhmet.api.sekhmetapi.domain.User;
import com.sekhmet.api.sekhmetapi.security.SecurityUtils;
import com.sekhmet.api.sekhmetapi.service.UserService;
import com.sekhmet.api.sekhmetapi.service.dto.UserDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

import static com.sekhmet.api.sekhmetapi.service.impl.UserServiceImpl.ACCOUNT_USER_PROFIL_PICTURE;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AccountResource {

    static class AccountResourceException extends RuntimeException {

        AccountResourceException(String message) {
            super(message);
        }
    }

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    private final UserService userService;

    /**
     * {@code GET  /account} : get the current user.
     *
     * @return the current user.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the user couldn't be returned.
     */
    @GetMapping("/account")
    public UserDTO getAccount() {
        return userService
                .getCurrentUser()
                .map(UserDTO::new)
                .orElseThrow(() -> new AccountResourceException("User could not be found"));
    }

    /**
     * {@code POST  /account} : update the current user information.
     *
     * @param userDTO the current user information.
     */
    @PatchMapping("/account")
    public Optional<User> saveAccount(@Valid @RequestBody UserDTO userDTO) {
        String userid = SecurityUtils
                .getCurrentUserLogin()
                .orElseThrow(() -> new AccountResourceException("Current user login not found"));
        Optional<User> user = userService.findById(UUID.fromString(userid));
        if (user.isEmpty()) {
            throw new AccountResourceException("User could not be found");
        }
        return userService.updateUser(
                userDTO.getFirstName(),
                userDTO.getLastName(),
                userDTO.getEmail(),
                userDTO.getLangKey(),
                userDTO.getImageUrl()
        );
    }

}
