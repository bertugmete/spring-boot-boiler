package com.mkk.exception;

import com.mkk.util.web.exception.ErrorInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.ws.rs.BadRequestException;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OkRequestValidationException extends BadRequestException {
    private List<ErrorInfo> errorInfos = new ArrayList();
}
