package cn.itheima.cart.service.impl;

import cn.itheima.commons.jedis.JedisClient;
import cn.itheima.commons.utils.E3Result;
import cn.itheima.commons.utils.JsonUtils;
import cn.itheima.manager.entity.User;
import cn.itheima.sso.interfaces.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 根据token取用户信息
 * <p>Title: TokenServiceImpl</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
@Service
public class TokenServiceImpl implements TokenService {

	@Autowired
	private JedisClient jedisClient;
	@Value("${SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;
	
	@Override
	public E3Result getUserByToken(String token) {
		//根据token到redis中取用户信息
		String json = jedisClient.get("SESSION:" + token);
		//取不到用户信息，登录已经过期，返回登录过期
		if (StringUtils.isBlank(json)) {
			return E3Result.build(201, "用户登录已经过期");
		}
		//取到用户信息更新token的过期时间
		jedisClient.expire("SESSION:" + token, SESSION_EXPIRE);
		//返回结果，E3Result其中包含TbUser对象
		User user = JsonUtils.jsonToPojo(json, User.class);
		return E3Result.ok(user);
	}

}
