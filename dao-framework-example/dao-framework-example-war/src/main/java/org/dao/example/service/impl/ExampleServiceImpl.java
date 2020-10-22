package org.dao.example.service.impl;


import org.dao.example.service.ExampleService;
import org.dao.framework.beans.annotation.Service;

@Service
public class ExampleServiceImpl implements ExampleService {
    @Override
    public String example(String parameter) {
        return parameter;
    }
}
