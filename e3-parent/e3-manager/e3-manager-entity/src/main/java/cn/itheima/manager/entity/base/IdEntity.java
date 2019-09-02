package cn.itheima.manager.entity.base;


import cn.itheima.commons.generator.UUIdGenId;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public abstract class IdEntity implements Serializable {

	    private static final long serialVersionUID = 1L;
	
		@Id
	 	@KeySql(genId = UUIdGenId.class)
	 	@Column(name = "ID")
	    private String id;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}
	 	
	 	
}
