package org.taiji.example.contriller;

import org.taiji.framework.core.beans.annotation.Controller;
import org.taiji.framework.core.web.annotation.RequestMapping;
import org.taiji.framework.core.web.annotation.RequestParam;
import org.yin.networktools.response.ResponseCode;
import org.yin.networktools.response.ResponseData;

@Controller
@RequestMapping("/")
public class ExampleContriller {

    @RequestMapping(value = "/")
    public ResponseData index(@RequestParam("msg") String msg){
        return new ResponseData(ResponseCode.SUCCESS,"成功！",msg);
    }

}
