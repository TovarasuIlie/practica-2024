package com.PracticaVara.springJwt.interceptors;

import lombok.Data;

@Data
public class BearerTokenWrapper {
    private String token;
}
