package cn.itheima.cart.interfaces;

import cn.itheima.commons.utils.E3Result;
import cn.itheima.manager.entity.Item;

import java.util.List;

public interface CartService {

	E3Result addCart(long userId, long itemId, int num);
	E3Result mergeCart(long userId, List<Item> itemList);
	List<Item> getCartList(long userId);
	E3Result updateCartNum(long userId, long itemId, int num);
	E3Result deleteCartItem(long userId, long itemId);
	E3Result clearCartItem(long userId);
}
