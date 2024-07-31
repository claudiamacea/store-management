package com.claudiamacea.store_management.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ExceptionResponse {

    private int errorCode;
    private String errorDescription;
    private String error;
    private Set<String> validationErrors;
}
