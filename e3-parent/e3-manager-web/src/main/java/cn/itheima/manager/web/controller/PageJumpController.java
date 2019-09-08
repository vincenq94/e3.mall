package cn.itheima.manager.web.controller;

import cn.itheima.commons.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value="pageJumpController")
@Controller
public class PageJumpController extends BaseController {

    @RequestMapping("index")
    public String showIndex(){
        return "index";
    }



}
