package com.sekhmet.api.sekhmetapi.domain;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * An authority (a security role) used by Spring Security.
 */
@Data
public class Authority implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    private String name;

}
