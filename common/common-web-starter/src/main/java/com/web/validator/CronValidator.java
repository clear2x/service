package com.web.validator;


import com.web.annotation.IsCron;
import org.springframework.scheduling.support.CronSequenceGenerator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 校验是否为合法的 Cron表达式
 */
public class CronValidator implements ConstraintValidator<IsCron, String> {

    @Override
    public void initialize(IsCron isCron) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return CronSequenceGenerator.isValidExpression(value);
    }
}
