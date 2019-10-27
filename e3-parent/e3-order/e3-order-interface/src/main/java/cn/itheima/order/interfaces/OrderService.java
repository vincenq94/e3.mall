package cn.itheima.order.interfaces;

import cn.itheima.commons.utils.E3Result;
import cn.itheima.order.pojo.OrderInfo;

public interface OrderService {

	E3Result createOrder(OrderInfo orderInfo);
}
