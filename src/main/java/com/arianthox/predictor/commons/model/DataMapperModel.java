package com.arianthox.predictor.commons.model;

import org.modelmapper.ModelMapper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


public class DataMapperModel<T> {

    private static final ModelMapper modelMapper = new ModelMapper();

    private transient Class<T> inferredClass;

    private Class<T> getGenericClass() {
        if (inferredClass == null) {
            Type mySuperclass = getClass().getGenericSuperclass();
            Type tType = ((ParameterizedType) mySuperclass).getActualTypeArguments()[0];
            String className = tType.toString().split(" ")[1];
            try {
                inferredClass = (Class<T>) Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return inferredClass;
    }

    public T toData() {
        return modelMapper.map(this, getGenericClass());
    }
}
