package com.lucianobrito.youtube.domain.services;

import com.lucianobrito.youtube.commons.exceptions.BusinessException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseService {

    protected <T extends Throwable> T handlerLogError(T e) {
        log.error(">>> ERROR: {} <<<", e.getMessage());
        return e;
    }

    protected BusinessException handlerLogBusinessError(Throwable e) {
        log.error(">>> BUSINESS ERROR: {} <<<", e.getMessage());
        return new BusinessException("Ocorreu um orro inesperado ao processar esta solicitação! Tente novamente mais tarde.");
    }
}
