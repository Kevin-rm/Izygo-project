package mg.motus.izygo.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class MalagasyPhoneNumberValidator implements ConstraintValidator<MalagasyPhoneNumber, String> {
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^(034|032|038|033)\\d{7}$");

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null || s.isBlank()) return false;
        if (!s.startsWith("0")) s = "0" + s;

        return PHONE_NUMBER_PATTERN.matcher(s).matches();
    }

    @Override
    public void initialize(MalagasyPhoneNumber constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
