package cn.itheima.manager.dao;

import cn.itheima.commons.dao.IBaseDao;
import cn.itheima.manager.entity.Item;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface IItemDao extends IBaseDao<Item> {

}