package cn.itheima.manager.web.controller;

import cn.itheima.commons.controller.BaseController;
import cn.itheima.commons.paging.Page;
import cn.itheima.manager.entity.Item;
import cn.itheima.manager.interfaces.IItemCatService;
import cn.itheima.manager.interfaces.IItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@RequestMapping(value="itemCatController")
@Controller
public class ItemCatController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private IItemCatService itemCatService;


    @RequestMapping(value="selectItemInfoById")
    @ResponseBody
    public Map<String,Object> selectItemInfoById(Item item){
        Map<String, Object> map = new HashMap<>();
        try {
            Item itemInfo = itemService.selectByPrimaryKey(item);
            map.put("itemInfo",itemInfo);
        } catch (Exception e) {
            logger.error("获取服务档案信息异常", e);
            map.put(_MESSAGE, "查询失败");
        }
        return map;
    }



}
