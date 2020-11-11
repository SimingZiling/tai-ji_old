package org.taiji.example.controller;

import org.taiji.example.model.Example;
import org.taiji.example.service.ExampleService;
import org.taiji.framework.beans.annotation.Controller;
import org.taiji.framework.beans.annotation.Inject;
import org.taiji.framework.core.web.annotation.RequestMapping;
import org.taiji.framework.core.web.annotation.RequestParam;
import org.yin.networktools.response.ResponseCode;
import org.yin.networktools.response.ResponseData;


@Controller
@RequestMapping("/")
public class ExampleContriller {

    @Inject
    private ExampleService exampleService;

    @RequestMapping(value = "/")
    public ResponseData index(@RequestParam("msg") String msg){
        return new ResponseData(ResponseCode.SUCCESS,"成功！",msg);
    }

    @RequestMapping(value = "/save")
    public ResponseData<Example> save(@RequestParam("name") String name){
        return new ResponseData<>(ResponseCode.SUCCESS,"成功！",exampleService.save(name));
    }

}
