package generator;

import tk.mybatis.mapper.genid.GenId;

import java.util.UUID;

public class UUIdGenId implements GenId<String> {

	public String genId(String table, String column) {
		 return UUID.randomUUID().toString().replaceAll("-", "");
	}

}
