package cn.itheima.cart.service.impl;

import cn.itheima.cart.interfaces.CartService;
import cn.itheima.commons.jedis.JedisClient;
import cn.itheima.commons.utils.E3Result;
import cn.itheima.commons.utils.JsonUtils;
import cn.itheima.manager.dao.IItemDao;
import cn.itheima.manager.entity.Item;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车处理服务
 * <p>Title: CartServiceImpl</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private JedisClient jedisClient;
	@Value("${REDIS_CART_PRE}")
	private String REDIS_CART_PRE;
	@Autowired
	private IItemDao itemMapper;
	
	@Override
	public E3Result addCart(long userId, long itemId, int num) {
		//向redis中添加购物车。
		//数据类型是hash key：用户id field：商品id value：商品信息
		//判断商品是否存在
		Boolean hexists = jedisClient.hexists(REDIS_CART_PRE + ":" + userId, itemId + "");
		//如果存在数量相加
		if (hexists) {
			String json = jedisClient.hget(REDIS_CART_PRE + ":" + userId, itemId + "");
			//把json转换成TbItem
			Item item = JsonUtils.jsonToPojo(json, Item.class);
			item.setNum(item.getNum() + num);
			//写回redis
			jedisClient.hset(REDIS_CART_PRE + ":" + userId, itemId + "", JsonUtils.objectToJson(item));
			return E3Result.ok();
		}
		//如果不存在，根据商品id取商品信息
		Item item = itemMapper.selectByPrimaryKey(itemId);
		//设置购物车数据量
		item.setNum(num);
		//取一张图片
		String image = item.getImage();
		if (StringUtils.isNotBlank(image)) {
			item.setImage(image.split(",")[0]);
		}
		//添加到购物车列表
		jedisClient.hset(REDIS_CART_PRE + ":" + userId, itemId + "", JsonUtils.objectToJson(item));
		return E3Result.ok();
	}

	@Override
	public E3Result mergeCart(long userId, List<Item> itemList) {
		//遍历商品列表
		//把列表添加到购物车。
		//判断购物车中是否有此商品
		//如果有，数量相加
		//如果没有添加新的商品
		for (Item tbItem : itemList) {
			addCart(userId, Long.valueOf(tbItem.getId()), tbItem.getNum());
		}
		//返回成功
		return E3Result.ok();
	}

	@Override
	public List<Item> getCartList(long userId) {
		//根据用户id查询购车列表
		List<String> jsonList = jedisClient.hvals(REDIS_CART_PRE + ":" + userId);
		List<Item> itemList = new ArrayList<>();
		for (String string : jsonList) {
			//创建一个TbItem对象
			Item item = JsonUtils.jsonToPojo(string, Item.class);
			//添加到列表
			itemList.add(item);
		}
		return itemList;
	}

	@Override
	public E3Result updateCartNum(long userId, long itemId, int num) {
		//从redis中取商品信息
		String json = jedisClient.hget(REDIS_CART_PRE + ":" + userId, itemId + "");
		//更新商品数量
		Item tbItem = JsonUtils.jsonToPojo(json, Item.class);
		tbItem.setNum(num);
		//写入redis
		jedisClient.hset(REDIS_CART_PRE + ":" + userId, itemId + "", JsonUtils.objectToJson(tbItem));
		return E3Result.ok();
	}

	@Override
	public E3Result deleteCartItem(long userId, long itemId) {
		// 删除购物车商品
		jedisClient.hdel(REDIS_CART_PRE + ":" + userId, itemId + "");
		return E3Result.ok();
	}

	@Override
	public E3Result clearCartItem(long userId) {
		//删除购物车信息
		jedisClient.del(REDIS_CART_PRE + ":" + userId);
		return E3Result.ok();
	}
	
	

}
