package cn.itheima.manager.interfaces;

import cn.itheima.commons.service.IBaseService;
import cn.itheima.manager.entity.Item;

public interface IItemService extends IBaseService<Item> {
    Item selectInfo(Item item);
}
