package com.tarkhan.blogapp.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseModel {
    @Schema(description = "Status code in the response")
    private String statusCode;
    @Schema(description = "Status message in the response")
    private String statusMsg;
}
