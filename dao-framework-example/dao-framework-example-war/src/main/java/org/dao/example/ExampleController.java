package org.dao.example;


import org.dao.example.service.ExampleService;
import org.dao.framework.beans.annotation.Controller;
import org.dao.framework.beans.annotation.Inject;
import org.dao.framework.beans.annotation.RequestParam;
import org.dao.framework.request.annotation.RequestMapping;
import org.dao.framework.request.annotation.RequestMethod;

@Controller
@RequestMapping
public class ExampleController {

    @Inject
    private ExampleService exampleService;

    @RequestMapping(method = RequestMethod.GET)
    public String example(@RequestParam("name") String name){
        return exampleService.example(name);
    }

}
