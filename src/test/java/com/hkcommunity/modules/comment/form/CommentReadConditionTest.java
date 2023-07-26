package com.hkcommunity.modules.comment.form;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static com.hkcommunity.factory.CommentReadConditionFactory.createCommentReadCondition;
import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;

class CommentReadConditionTest {
    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void validateTest() {
        // given
        CommentReadCondition cond = createCommentReadCondition();

        // when
        Set<ConstraintViolation<CommentReadCondition>> validate = validator.validate(cond);

        // then
        assertThat(validate).isEmpty();
    }

    @Test
    void invalidateByNegativePostIdTest() {
        // given
        Long invalidValue = -1L;
        CommentReadCondition cond = createCommentReadCondition(invalidValue);

        // when
        Set<ConstraintViolation<CommentReadCondition>> validate = validator.validate(cond);

        // then
        assertThat(validate).isNotEmpty();
        assertThat(validate.stream().map(v -> v.getInvalidValue()).collect(toSet())).contains(invalidValue);
    }
}