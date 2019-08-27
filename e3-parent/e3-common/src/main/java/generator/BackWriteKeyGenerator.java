package generator;

import com.jhsafe.school.commons.entity.BaseEntity;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;

import java.sql.Statement;
import java.util.UUID;

public class BackWriteKeyGenerator implements KeyGenerator {

	public void processBefore(Executor executor, MappedStatement ms, Statement stmt, Object parameter) {
		if (parameter instanceof BaseEntity) {
			((BaseEntity) parameter).setId(UUID.randomUUID().toString().replaceAll("-", ""));
		}
		
	}

	public void processAfter(Executor executor, MappedStatement ms, Statement stmt, Object parameter) {
	}

}
