package cn.itheima.content.interfaces;

import cn.itheima.commons.utils.E3Result;
import cn.itheima.manager.po.EasyUITreeNode;

import java.util.List;

public interface IContentCategoryService {

	List<EasyUITreeNode> getContentCatList(long parentId);
	E3Result addContentCategory(long parentId, String name);
}
