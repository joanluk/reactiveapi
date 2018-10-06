package org.emaginalabs.reactiveapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * User: jose
 * Date: 06/10/2018
 * Time: 18:53
 */
@Data
@AllArgsConstructor
public class ErrorResponse {

    private String message;
}
