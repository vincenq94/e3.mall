package cn.itheima.content.service.impl;

import cn.itheima.commons.example.SimpleExample;
import cn.itheima.commons.jedis.JedisClient;
import cn.itheima.commons.service.impl.BaseServiceImpl;
import cn.itheima.commons.utils.E3Result;
import cn.itheima.content.interfaces.IContentService;
import cn.itheima.manager.dao.IContentDao;
import cn.itheima.manager.entity.Content;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 内容管理Service
 * <p>Title: ContentServiceImpl</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
@Service
public class ContentServiceImpl extends BaseServiceImpl<Content> implements IContentService {

	@Autowired
	private IContentDao contentDao;
	@Autowired
	private JedisClient jedisClient;

	@Value("${CONTENT_LIST}")
	private String CONTENT_LIST;

	public E3Result addContent(Content content) {
		//将内容数据插入到内容表
		content.setCreated(new Date());
		content.setUpdated(new Date());
		//插入到数据库
		contentDao.insertSelective(content);
		//缓存同步，删除缓存中对应的数据
		jedisClient.hdel(CONTENT_LIST,content.getCategoryId().toString());
		return E3Result.ok();
	}

	/**
	 * 根据内容分类id查询内容列表
	 * <p>Title: getContentListByCid</p>
	 * <p>Description: </p>
	 * @param cid
	 * @return
	 */
	@Override
	public List<Content> getContentListByCid(long cid) {
		//查询缓存
		try {
			String json = jedisClient.hget(CONTENT_LIST, String.valueOf(cid));
			if (StringUtils.isNotBlank(json)) {
				List<Content> list = JSON.parseArray(json, Content.class);
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		SimpleExample simpleExample = new SimpleExample();
		//设置查询条件
		simpleExample.eq("categoryId",cid);
		//执行查询
		List<Content> list = contentDao.selectByExample(simpleExample);
		//把结果添加到缓存
		try {
			jedisClient.hset(CONTENT_LIST,String.valueOf(cid), JSON.toJSONString(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
