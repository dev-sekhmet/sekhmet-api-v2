package com.sekhmet.api.sekhmetapi.service.dto;

import com.sekhmet.api.sekhmetapi.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

/**
 * A DTO representing a user, with only the public attributes.
 */
@Getter
@Setter
@ToString
public class UserDTO {

    private UUID id;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Email
    @Size(min = 5, max = 254)
    private String email;

    @Size(max = 256)
    private String imageUrl;

    private boolean activated = false;

    @Size(min = 2, max = 10)
    private String langKey;

    private String phoneNumber;

    public UserDTO() {
        // Empty constructor needed for Jackson.
    }

    public UserDTO(User user) {
        this.id = user.getId();
        // Customize it here if you need, or not, firstName/lastName/etc
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.activated = user.isActivated();
        this.email = user.getEmail();
        this.imageUrl = user.getImageUrl();
        this.phoneNumber = user.getPhoneNumber();
    }
}
