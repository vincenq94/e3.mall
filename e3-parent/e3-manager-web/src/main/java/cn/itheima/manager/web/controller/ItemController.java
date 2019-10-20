package cn.itheima.manager.web.controller;

import cn.itheima.commons.controller.BaseController;
import cn.itheima.commons.paging.Page;
import cn.itheima.commons.utils.E3Result;
import cn.itheima.manager.entity.Item;
import cn.itheima.manager.interfaces.IItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
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

    /***
     * Description 查询商品列表信息分页
     * @author nq
     * @param page
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @CreateDate 2019/10/2 22:40
     */
    @RequestMapping(value="getItemListPage")
    @ResponseBody
    public Map<String,Object> getItemListPage(Page<Item> page){
        Map<String, Object> map = new HashMap<>();
        try {
            page = itemService.getItemListPage(page);
            map.putAll(page.getMap());
        } catch (Exception e) {
            logger.error("查询商品列表信息异常", e);
            e.printStackTrace();
            map.put(_MESSAGE, "查询失败");
        }
        return map;
    }


    /***
     * Description 商品添加功能
     * @author nq
     * @param item
 * @param desc
     * @return cn.itheima.commons.utils.E3Result
     * @CreateDate 2019/10/2 22:38
     */
    @RequestMapping(value="/addItem", method= RequestMethod.POST)
    @ResponseBody
    public E3Result addItem(Item item, String desc) {
        E3Result result = itemService.addItem(item, desc);
        return result;
    }


}
