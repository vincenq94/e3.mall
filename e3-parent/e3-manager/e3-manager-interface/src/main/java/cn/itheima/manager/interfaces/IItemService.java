package cn.itheima.manager.interfaces;

import cn.itheima.commons.paging.Page;
import cn.itheima.commons.service.IBaseService;
import cn.itheima.commons.utils.E3Result;
import cn.itheima.manager.entity.Item;

public interface IItemService extends IBaseService<Item> {
    Item selectInfo(Item item);
    Page<Item> getItemListPage(Page<Item> page);
    E3Result addItem(Item item, String desc);
}
