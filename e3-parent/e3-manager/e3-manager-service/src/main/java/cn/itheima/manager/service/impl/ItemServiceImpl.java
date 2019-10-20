package cn.itheima.manager.service.impl;

import cn.itheima.commons.example.SimpleExample;
import cn.itheima.commons.jedis.JedisClient;
import cn.itheima.commons.paging.Page;
import cn.itheima.commons.service.impl.BaseServiceImpl;
import cn.itheima.commons.utils.DateTimeUtil;
import cn.itheima.commons.utils.E3Result;
import cn.itheima.commons.utils.JsonUtils;
import cn.itheima.manager.dao.IItemDao;
import cn.itheima.manager.dao.IItemDescDao;
import cn.itheima.manager.entity.Item;
import cn.itheima.manager.entity.ItemDesc;
import cn.itheima.manager.interfaces.IItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.jms.*;
import java.util.List;

@Service
@Transactional
public class ItemServiceImpl extends BaseServiceImpl<Item> implements IItemService{
    @Autowired
    private IItemDao itemDao;
    @Autowired
    private IItemDescDao itemDescDao;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Resource
    private Destination topicDestination;
    @Autowired
    private JedisClient jedisClient;

    @Value("${REDIS_ITEM_PRE}")
    private String REDIS_ITEM_PRE;
    @Value("${ITEM_CACHE_EXPIRE}")
    private Integer ITEM_CACHE_EXPIRE;

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
        //发送商品添加消息
        jmsTemplate.send(topicDestination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage(item.getId() + "");
                return textMessage;
            }
        });
        //返回成功
        return E3Result.ok();
    }


    public Item getItemById(long itemId) {
        //查询缓存
        try {
            String json = jedisClient.get(REDIS_ITEM_PRE + ":" + itemId + ":BASE");
            if(StringUtils.isNotBlank(json)) {
                Item tbItem = JsonUtils.jsonToPojo(json, Item.class);
                return tbItem;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //缓存中没有，查询数据库
        //根据主键查询
        //TbItem tbItem = itemMapper.selectByPrimaryKey(itemId);
        SimpleExample simpleExample = new SimpleExample();
        simpleExample.eq("id",String.valueOf(itemId));
        //设置查询条件
        //执行查询
        List<Item> list = itemDao.selectByExample(simpleExample);
        if (list != null && list.size() > 0) {
            //把结果添加到缓存
            try {
                jedisClient.set(REDIS_ITEM_PRE + ":" + itemId + ":BASE", JsonUtils.objectToJson(list.get(0)));
                //设置过期时间
                jedisClient.expire(REDIS_ITEM_PRE + ":" + itemId + ":BASE", ITEM_CACHE_EXPIRE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return list.get(0);
        }
        return null;
    }


    public ItemDesc getItemDescById(long itemId) {
        //查询缓存
        try {
            String json = jedisClient.get(REDIS_ITEM_PRE + ":" + itemId + ":DESC");
            if(StringUtils.isNotBlank(json)) {
                ItemDesc tbItemDesc = JsonUtils.jsonToPojo(json, ItemDesc.class);
                return tbItemDesc;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ItemDesc itemDesc = itemDescDao.selectByPrimaryKey(itemId);
        //把结果添加到缓存
        try {
            jedisClient.set(REDIS_ITEM_PRE + ":" + itemId + ":DESC", JsonUtils.objectToJson(itemDesc));
            //设置过期时间
            jedisClient.expire(REDIS_ITEM_PRE + ":" + itemId + ":DESC", ITEM_CACHE_EXPIRE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemDesc;
    }

}
