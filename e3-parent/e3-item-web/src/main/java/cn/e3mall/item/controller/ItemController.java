package cn.e3mall.item.controller;

import cn.e3mall.item.pojo.ItemVo;
import cn.itheima.manager.entity.Item;
import cn.itheima.manager.entity.ItemDesc;
import cn.itheima.manager.interfaces.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 商品详情页面展示Controller
 * <p>Title: ItemController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
@Controller
public class ItemController {

	@Autowired
	private IItemService itemService;
	
	
	@RequestMapping("/item/{itemId}")
	public String showItemInfo(@PathVariable Long itemId, Model model) {
		//调用服务取商品基本信息
		Item tbItem = itemService.getItemById(itemId);
		ItemVo item = new ItemVo(tbItem);
		//取商品描述信息
		ItemDesc itemDesc = itemService.getItemDescById(itemId);
		//把信息传递给页面
		model.addAttribute("item", item);
		model.addAttribute("itemDesc", itemDesc);
		//返回逻辑视图
		return "item";
	}
}
