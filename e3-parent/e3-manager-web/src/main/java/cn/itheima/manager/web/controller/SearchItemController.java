package cn.itheima.manager.web.controller;

import cn.itheima.commons.utils.E3Result;
import cn.itheima.search.interfaces.ISearchItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 导入商品数据到索引库
 * <p>Title: SearchItemController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0searchItemService
 */
@Controller
public class SearchItemController {
	
	@Autowired
	private ISearchItemService searchItemService;

	@RequestMapping("/index/item/import")
	@ResponseBody
	public E3Result importItemList() {
		E3Result e3Result = searchItemService.importAllItems();
		return e3Result;

	}
}
