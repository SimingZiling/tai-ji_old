package org.taiji.example.service.impl;

import org.taiji.example.dao.ExampleDao;
import org.taiji.example.model.Example;
import org.taiji.example.service.ExampleService;
import org.taiji.framework.beans.annotation.Inject;
import org.taiji.framework.beans.annotation.Service;

import java.util.Date;

@Service
public class ExampleServiceImpl implements ExampleService {

    @Inject
    private ExampleDao exampleDao;

    @Override
    public Example save(String name) {

        Example example = new Example();
        example.setAddTime(new Date());
        example.setName(name);
        exampleDao.save(example);
        return example;
    }
}
