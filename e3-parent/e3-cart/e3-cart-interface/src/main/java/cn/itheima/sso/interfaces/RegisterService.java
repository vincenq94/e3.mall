package cn.itheima.sso.interfaces;

import cn.itheima.commons.utils.E3Result;
import cn.itheima.manager.entity.User;

public interface RegisterService {

	E3Result checkData(String param, int type);
	E3Result register(User user);
}
