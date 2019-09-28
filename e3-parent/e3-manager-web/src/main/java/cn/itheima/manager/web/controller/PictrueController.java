package cn.itheima.manager.web.controller;

import cn.itheima.commons.controller.BaseController;
import cn.itheima.manager.interfaces.IItemCatService;
import cn.itheima.manager.po.EasyUITreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(value="itemCatController")
@Controller
public class PictrueController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(getClass());


    @RequestMapping(value="selectItemInfoById")
    @ResponseBody
    public List<EasyUITreeNode> getItemCatList() {
        Map<String, Object> map = new HashMap<>();
        try {

        } catch (Exception e) {
            logger.error("图片上传失败异常", e);
            e.printStackTrace();
            map.put(_MESSAGE, "图片上传失败");
        }
        return map;
    }





}
