package com.mkk.util.web;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ValidationInfo {
    private String type;
    private String message;
}
