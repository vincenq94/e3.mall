package service;

import com.jhsafe.school.commons.example.SimpleExample;
import com.jhsafe.school.commons.paging.Page;

import java.util.List;

public interface IBaseService<T> {

	public T selectOne(T record);

	public List<T> select(T record);

	public List<T> selectAll();

	public int selectCount(T record);
	
	public T selectByPrimaryKey(Object key);

	public int insert(T record);

	public int insertSelective(T record);

	public int updateByPrimaryKey(T record);

	public int updateByPrimaryKeySelective(T record);

	public int delete(T record);

	public int deleteByPrimaryKey(Object key);

	public List<T> selectByExample(Object example) ;

	public T selectOneByExample(Object example) ;

	public int selectCountByExample(Object example) ;

	public int deleteByExample(Object example) ;

	public int updateByExample(T record, Object example);

	public int updateByExampleSelective(T record, Object example);
	
	
	public Page<T> selectByPage(T record, Page<T> page);
	
	public Page<T> selectByExamplePage(SimpleExample example, Page<T> page);
	
}
