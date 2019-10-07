package cn.itheima.manager.service.impl;

import cn.itheima.commons.example.SimpleExample;
import cn.itheima.commons.paging.Page;
import cn.itheima.commons.service.impl.BaseServiceImpl;
import cn.itheima.commons.utils.DateTimeUtil;
import cn.itheima.commons.utils.E3Result;
import cn.itheima.manager.dao.IItemDao;
import cn.itheima.manager.dao.IItemDescDao;
import cn.itheima.manager.entity.Item;
import cn.itheima.manager.entity.ItemDesc;
import cn.itheima.manager.interfaces.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ItemServiceImpl extends BaseServiceImpl<Item> implements IItemService{
    @Autowired
    private IItemDao itemDao;
    @Autowired
    private IItemDescDao itemDescDao;

    @Override
    public Item selectInfo(Item item) {
        return itemDao.selectByPrimaryKey(item);
    }

    @Override
    public Page<Item> getItemListPage(Page<Item> page) {
        SimpleExample simpleExample = new SimpleExample();
        page = this.selectByExamplePage(simpleExample,page);
        return page;
    }

    @Override
    public E3Result addItem(Item item, String desc) {
        //补全item的属性
        //1-正常，2-下架，3-删除
        item.setStatus((byte) 1);
        item.setCreated(DateTimeUtil.getDateTime_YYYY_MM_DD_HH_MM_SS());
        item.setUpdated(DateTimeUtil.getDateTime_YYYY_MM_DD_HH_MM_SS());
        //向商品表插入数据
        itemDao.insertSelective(item);
        //创建一个商品描述表对应的pojo对象。
        ItemDesc itemDesc = new ItemDesc();
        //补全属性
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(DateTimeUtil.getDateTime_YYYY_MM_DD_HH_MM_SS());
        itemDesc.setUpdated(DateTimeUtil.getDateTime_YYYY_MM_DD_HH_MM_SS());
        //向商品描述表插入数据
        itemDescDao.insert(itemDesc);
        //返回成功
        return E3Result.ok();
    }

}
