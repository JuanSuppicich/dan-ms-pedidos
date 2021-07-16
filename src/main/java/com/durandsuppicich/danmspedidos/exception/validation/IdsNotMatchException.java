package com.durandsuppicich.danmspedidos.exception.validation;

import com.durandsuppicich.danmspedidos.exception.http.BadRequestException;

public class IdsNotMatchException extends BadRequestException {

    private static final String DEFAULT_MESSAGE = "Illegal call. Ids do not match.";

    public IdsNotMatchException() {
        super(DEFAULT_MESSAGE);
    }

    public IdsNotMatchException(Integer bodyId, Integer pathId) {
        super(DEFAULT_MESSAGE + " The given id in the body is" + "{" + bodyId + "}"
                + "while the given id in the path is " + "{" + pathId +"}");
    }
}
