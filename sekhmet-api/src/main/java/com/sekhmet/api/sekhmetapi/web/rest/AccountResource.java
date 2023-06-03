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
                .getUserWithAuthorities()
                .map(UserDTO::new)
                .orElseThrow(() -> new AccountResourceException("User could not be found"));
    }

    /**
     * {@code POST  /account} : update the current user information.
     *
     * @param userDTO the current user information.
     */
    @PostMapping("/account")
    public void saveAccount(@Valid @RequestBody UserDTO userDTO) {
        String userid = SecurityUtils
                .getCurrentUserLogin()
                .orElseThrow(() -> new AccountResourceException("Current user login not found"));
        Optional<User> user = userService.findById(UUID.fromString(userid));
        if (user.isEmpty()) {
            throw new AccountResourceException("User could not be found");
        }
        userService.updateUser(
                userDTO.getFirstName(),
                userDTO.getLastName(),
                userDTO.getEmail(),
                userDTO.getLangKey(),
                userDTO.getImageUrl()
        );
    }

    /**
     * {@code POST  /account/user-picture} : add user profil picture.
     *
     * @param file the current user information.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the user login wasn't found.
     */
    @PostMapping("/" + ACCOUNT_USER_PROFIL_PICTURE)
    public UserDTO addUserProfil(@RequestParam MultipartFile file) {
        String userLogin = SecurityUtils
                .getCurrentUserLogin()
                .orElseThrow(() -> new AccountResource.AccountResourceException("Current user login not found"));
        Optional<User> user = userService.addProfilePicture(userLogin, file);
        return user.map(UserDTO::new).orElseThrow(() -> new AccountResourceException("User could not be found"));
    }

    /**
     * {@code GET  /messages/:messageId/:fileType/:fileId} : get the file.
     *
     * @param fileId the id of the message media to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the message, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/" + ACCOUNT_USER_PROFIL_PICTURE + "/{userId}/{fileId}")
    public ResponseEntity<byte[]> getProfilPicture(@PathVariable String fileId, @PathVariable String userId) throws IOException {
        log.debug("REST request to get Media : {} - {}", userId, fileId);

        S3Object media = userService.getProfiPic(userId, fileId);
        try (InputStream in = media.getObjectContent()) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            IOUtils.copy(in, baos);
            byte[] fileBytes = baos.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaTypes(media.getObjectMetadata().getContentType()).get(0));
            headers.setContentLength(fileBytes.length);
            return wrapOrNotFound(Optional.of(fileBytes), headers);
        }
    }

    static <X> ResponseEntity<X> wrapOrNotFound(Optional<X> maybeResponse, HttpHeaders header) {
        return maybeResponse.map(response -> ResponseEntity.ok().headers(header).body(response))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}
