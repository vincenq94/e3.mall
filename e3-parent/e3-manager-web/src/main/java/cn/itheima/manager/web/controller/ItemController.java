package cn.itheima.manager.web.controller;

import cn.itheima.commons.controller.BaseController;
import cn.itheima.commons.example.SimpleExample;
import cn.itheima.commons.paging.Page;
import cn.itheima.manager.entity.Item;
import cn.itheima.manager.interfaces.IItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(value="itemController")
@Controller
public class ItemController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private IItemService itemService;


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

    @RequestMapping(value="getItemListPage")
    @ResponseBody
    public Map<String,Object> getItemListPage(Page<Item> page){
        Map<String, Object> map = new HashMap<>();
        try {
            List<Item> itemList = itemService.selectAll();
            Example example = new Example(Item.class);
            List<Item> items = itemService.selectByExample(example);
            page = itemService.selectByExamplePage(new SimpleExample(),page);
            map.putAll(page.getMap());
        } catch (Exception e) {
            logger.error("获取服务档案信息异常", e);
            e.printStackTrace();
            map.put(_MESSAGE, "查询失败");
        }
        return map;
    }

}
