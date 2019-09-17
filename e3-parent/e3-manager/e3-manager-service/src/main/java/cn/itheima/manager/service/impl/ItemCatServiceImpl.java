package cn.itheima.manager.service.impl;

import cn.itheima.commons.service.impl.BaseServiceImpl;
import cn.itheima.manager.dao.IItemCatDao;
import cn.itheima.manager.entity.ItemCat;
import cn.itheima.manager.interfaces.IItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ItemCatServiceImpl extends BaseServiceImpl<ItemCat> implements IItemCatService {
    @Autowired
    private IItemCatDao itemCatDao;

}
