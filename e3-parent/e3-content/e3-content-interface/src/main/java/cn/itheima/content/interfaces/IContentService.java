package cn.itheima.content.interfaces;

import cn.itheima.commons.service.IBaseService;
import cn.itheima.commons.utils.E3Result;
import cn.itheima.manager.entity.Content;

import java.util.List;

public interface IContentService extends IBaseService<Content> {

	E3Result addContent(Content content);
	List<Content> getContentListByCid(long cid);
}
