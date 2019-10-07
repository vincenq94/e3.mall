package cn.itheima.portal.web.controller;

import cn.itheima.content.interfaces.IContentService;
import cn.itheima.manager.entity.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 首页展示Controller
 * <p>Title: IndexController</p>
 * <p>Description: </p>
 *
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
@Controller
public class IndexController {
	
	@Value("${CONTENT_LUNBO_ID}")
	private Long CONTENT_LUNBO_ID;

	@Autowired
	private IContentService contentService;

	@RequestMapping("/index")
	public String showIndex(Model model) {
		//查询内容列表
		List<Content> ad1List = contentService.getContentListByCid(CONTENT_LUNBO_ID);
		// 把结果传递给页面
		model.addAttribute("ad1List", ad1List);
		return "index";
	}
}
