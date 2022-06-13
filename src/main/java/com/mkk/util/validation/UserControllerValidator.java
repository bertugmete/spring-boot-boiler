package com.mkk.util.validation;

import com.mkk.annotation.MKKValidationComponent;
import com.mkk.controller.user.UserController;
import com.mkk.enums.ValidationExceptionEnum;
import com.mkk.request.user.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.Validator;

import java.util.Map;

public class UserControllerValidator extends ControllerValidator{

    @MKKValidationComponent
    @RequiredArgsConstructor
    public static class CreateUserValidator implements Validator {

        @Override
        public boolean supports(Class<?> clazz) {
            return UserController.class.equals(clazz);
        }

        @Override
        public void validate(Object target, Errors errors) {
            MapBindingResult result = (MapBindingResult) errors;

            Map<?, ?> params = result.getTargetMap();
            UserRequest request = (UserRequest) params.get(REQ_NAME);

            if (request.getFirstName() == null) {
                reject(result, ValidationExceptionEnum.USER_NAME_CANNOT_BE_NULL);
            }
        }
    }
}
