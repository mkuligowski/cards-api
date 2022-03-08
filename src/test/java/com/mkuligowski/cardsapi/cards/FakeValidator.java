package com.mkuligowski.cardsapi.cards;

import com.mkuligowski.cardsapi.cards.domainapi.NewCardRequest;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validator;
import javax.validation.executable.ExecutableValidator;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ConstraintDescriptor;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public class FakeValidator implements Validator {
    public static String FAILING_CARD_NUMBER = "1111111111111111";


    @Override
    public <T> Set<ConstraintViolation<T>> validate(T object, Class<?>... groups) {
        NewCardRequest dto = (NewCardRequest) object;

        if (FAILING_CARD_NUMBER.equals(dto.getCardNumber())){

            Set<ConstraintViolation<T>> violations = new HashSet<>();
            violations.add(new ConstraintViolation<T>() {
                @Override
                public String getMessage() {
                    return "You should not add " + FAILING_CARD_NUMBER;
                }

                @Override
                public String getMessageTemplate() {
                    return null;
                }

                @Override
                public T getRootBean() {
                    return null;
                }

                @Override
                public Class<T> getRootBeanClass() {
                    return null;
                }

                @Override
                public Object getLeafBean() {
                    return null;
                }

                @Override
                public Object[] getExecutableParameters() {
                    return new Object[0];
                }

                @Override
                public Object getExecutableReturnValue() {
                    return null;
                }

                @Override
                public Path getPropertyPath() {
                    return null;
                }

                @Override
                public Object getInvalidValue() {
                    return null;
                }

                @Override
                public ConstraintDescriptor<?> getConstraintDescriptor() {
                    return null;
                }

                @Override
                public <U> U unwrap(Class<U> type) {
                    return null;
                }
            });
            return violations;
        }
        else
            return Collections.emptySet();
    }

    @Override
    public <T> Set<ConstraintViolation<T>> validateProperty(T object, String propertyName, Class<?>... groups) {
        return null;
    }

    @Override
    public <T> Set<ConstraintViolation<T>> validateValue(Class<T> beanType, String propertyName, Object value, Class<?>... groups) {
        return null;
    }

    @Override
    public BeanDescriptor getConstraintsForClass(Class<?> clazz) {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> type) {
        return null;
    }

    @Override
    public ExecutableValidator forExecutables() {
        return null;
    }
}
