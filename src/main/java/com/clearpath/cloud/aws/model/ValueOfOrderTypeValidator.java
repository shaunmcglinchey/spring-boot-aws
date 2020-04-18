package com.clearpath.cloud.aws.model;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ValueOfOrderTypeValidator implements ConstraintValidator<ValueOfOrderType, String> {
   private List<String> acceptedValues;

   @Override
   public void initialize(ValueOfOrderType annotation) {
      acceptedValues = Stream.of(annotation.enumClass().getEnumConstants())
              .map(val -> val.toString().toLowerCase())
              .collect(Collectors.toList());
   }

   @Override
   public boolean isValid(String value, ConstraintValidatorContext context) {
      if (value == null) {
         return false;
      }

      return acceptedValues.contains(value.toLowerCase());
   }
}
