package org.dao.example.controller;


import org.dao.example.service.ExampleService;
import org.dao.framework.beans.annotation.Controller;
import org.dao.framework.beans.annotation.Inject;
import org.dao.framework.request.annotation.RequestMapping;
import org.dao.framework.request.annotation.RequestMethod;
import org.dao.framework.request.annotation.RequestParam;

@Controller
@RequestMapping()
public class ExampleController {

    @Inject
    private ExampleService exampleService;

    @RequestMapping(method = RequestMethod.GET)
    public String example(@RequestParam("name ") String name){

        return exampleService.example(name);
    }

}
