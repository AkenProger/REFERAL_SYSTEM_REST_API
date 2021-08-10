package com.example.referal_system.mappers;

import java.util.List;

public interface BaseMapper<S, T> {
    S toDto(T t);

    T toEntity(S s);

    List<S> toDtoList(List<T> entities);

    List<T> toEntities(List<S> dtoList);

}
