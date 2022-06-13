package com.mkk.util.web.exception;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class ValidationException extends RuntimeException{
    private List<ErrorInfo> errorInfos = new ArrayList();

}
