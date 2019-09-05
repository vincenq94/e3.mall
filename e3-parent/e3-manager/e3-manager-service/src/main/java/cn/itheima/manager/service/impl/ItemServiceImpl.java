package cn.itheima.manager.service.impl;

import cn.itheima.commons.service.impl.BaseServiceImpl;
import cn.itheima.manager.dao.IItemDao;
import cn.itheima.manager.entity.Item;
import cn.itheima.manager.interfaces.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ItemServiceImpl extends BaseServiceImpl<Item> implements IItemService{
    @Autowired
    private IItemDao itemDao;

    @Override
    public Item selectInfo(Item item) {
        return itemDao.selectByPrimaryKey(item);
    }
}
