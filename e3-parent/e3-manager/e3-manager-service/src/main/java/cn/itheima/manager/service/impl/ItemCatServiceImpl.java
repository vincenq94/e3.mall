package cn.itheima.manager.service.impl;

import cn.itheima.commons.example.SimpleExample;
import cn.itheima.commons.service.impl.BaseServiceImpl;
import cn.itheima.manager.dao.IItemCatDao;
import cn.itheima.manager.entity.ItemCat;
import cn.itheima.manager.interfaces.IItemCatService;
import cn.itheima.manager.po.EasyUITreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ItemCatServiceImpl extends BaseServiceImpl<ItemCat> implements IItemCatService {
    @Autowired
    private IItemCatDao itemCatDao;

    @Override
    public List<EasyUITreeNode> getItemCatList(long parentId) {
        //根据parentId查询子节点列表
        //设置查询条件
        SimpleExample simpleExample = new SimpleExample();
        simpleExample.eq("parentId",parentId);
        //执行查询
        List<ItemCat> list = itemCatDao.selectByExample(simpleExample);
        //创建返回结果List
        List<EasyUITreeNode> resultList = new ArrayList<>();
        //把列表转换成EasyUITreeNode列表
        for (ItemCat itemCat : list) {
            EasyUITreeNode node = new EasyUITreeNode();
            //设置属性
            node.setId(Long.parseLong(itemCat.getId()));
            node.setText(itemCat.getName());
            node.setState(itemCat.getIsParent()?"closed":"open");
            //添加到结果列表
            resultList.add(node);
        }
        //返回结果
        return resultList;
    }
}
