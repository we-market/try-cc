package com.wemarket.cc.common.utils;

import com.wemarket.cc.common.dto.BaseDTO;
import com.wemarket.cc.common.dto.BizErrors;

@FunctionalInterface
public interface ExecServiceTemplate<E extends BaseDTO, T> {
    public T apply(E requestDto, BizErrors bizErrors);
}