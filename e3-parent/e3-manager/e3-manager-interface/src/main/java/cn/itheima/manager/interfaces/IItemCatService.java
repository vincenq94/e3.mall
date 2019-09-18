package cn.itheima.manager.interfaces;

import cn.itheima.commons.service.IBaseService;
import cn.itheima.manager.entity.ItemCat;
import cn.itheima.manager.po.EasyUITreeNode;

import java.util.List;

public interface IItemCatService extends IBaseService<ItemCat> {

    List<EasyUITreeNode> getItemCatList(long parentId);
}
