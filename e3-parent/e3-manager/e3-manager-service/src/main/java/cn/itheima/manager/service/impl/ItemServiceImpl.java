package cn.itheima.manager.service.impl;

import cn.itheima.commons.service.impl.BaseServiceImpl;
import cn.itheima.manager.entity.Item;
import cn.itheima.manager.service.IItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ItemServiceImpl extends BaseServiceImpl<Item> implements IItemService{

}
