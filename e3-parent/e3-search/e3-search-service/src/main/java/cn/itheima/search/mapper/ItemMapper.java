package cn.itheima.search.mapper;

import cn.itheima.commons.pojo.SearchItem;
import tk.mybatis.spring.annotation.MapperScan;

import java.util.List;
@MapperScan
public interface ItemMapper {

	List<SearchItem> getItemList();
	SearchItem getItemById(String itemId);
}
