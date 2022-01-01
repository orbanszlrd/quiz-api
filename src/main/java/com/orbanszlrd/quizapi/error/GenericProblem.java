package com.orbanszlrd.quizapi.error;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class GenericProblem extends AbstractThrowableProblem {
    public GenericProblem(Exception e, Status status) {
        super(null, status.name(), status, e.getMessage());
    }
}
