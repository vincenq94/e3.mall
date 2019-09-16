import cn.itheima.commons.spring.SpringContext;
import cn.itheima.manager.entity.Item;
import cn.itheima.manager.interfaces.IItemService;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

public class tkmybatisTest {

    private static IItemService itemService;

    public static void main(String[] args) {
        itemService = ((IItemService) SpringContext.getBean(IItemService.class));
        Example example = new Example(Item.class);
        List<Item> items = itemService.selectByExample(example);
    }
}
