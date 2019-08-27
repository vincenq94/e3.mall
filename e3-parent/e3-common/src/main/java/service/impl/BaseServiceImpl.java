package service.impl;

import com.github.pagehelper.PageHelper;
import com.jhsafe.school.commons.dao.IBaseDao;
import com.jhsafe.school.commons.example.SimpleExample;
import com.jhsafe.school.commons.paging.Page;
import com.jhsafe.school.commons.service.IBaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BaseServiceImpl<T> implements IBaseService<T> {

	@Autowired
	protected IBaseDao<T> baseDao;
	
//	@Autowired
//	protected SqlSessionFactory sqlSessionFactory;
//	
//	public T selectOne1(T record) {
//		sqlSessionFactory.openSession().selectOne(statement, record);
//		return baseDao.selectOne(record);
//	}
	
	public void setBaseDao(IBaseDao<T> baseDao) {
		this.baseDao = baseDao;
	}

	public T selectOne(T record) {
		return baseDao.selectOne(record);
	}
	
	public List<T> select(T record) {
		return baseDao.select(record);
	}

	public List<T> selectAll() {
		return baseDao.selectAll();
	}

	public int selectCount(T record) {
		return baseDao.selectCount(record);
	}

	public T selectByPrimaryKey(Object key) {
		return baseDao.selectByPrimaryKey(key);
	}

	public int insert(T record) {
		return baseDao.insert(record);
	}

	public int insertSelective(T record) {
		return baseDao.insertSelective(record);
	}

	public int updateByPrimaryKey(T record) {
		return baseDao.updateByPrimaryKey(record);
	}
	

	public int updateByPrimaryKeySelective(T record) {
		return baseDao.updateByPrimaryKeySelective(record);
	}

	public int delete(T record) {
		return baseDao.delete(record);
	}

	public int deleteByPrimaryKey(Object key) {
		return baseDao.deleteByPrimaryKey(key);
	}

	public List<T> selectByExample(Object example) {
		return baseDao.selectByExample(example);
	}

	public T selectOneByExample(Object example) {
		return baseDao.selectOneByExample(example);
	}

	public int selectCountByExample(Object example) {
		return baseDao.selectCountByExample(example);
	}

	public int deleteByExample(Object example) {
		return baseDao.deleteByExample(example);
	}

	public int updateByExample(T record, Object example) {
		return baseDao.updateByExample(record, example);
	}

	public int updateByExampleSelective(T record, Object example) {
		return baseDao.updateByExampleSelective(record, example);
	}

	
	public Page<T> selectByPage(T record, Page<T> pge){
		com.github.pagehelper.Page<T> p = startPage(pge);
		List<T> list = baseDao.select(record);
		pge.setRecords((int)p.getTotal());
		pge.setRs(list);
		return pge;
	}
	
	/**
	 * @param example
	 * @param pge
	 * @return
	 */
	public Page<T> selectByExamplePage(SimpleExample example, Page<T> pge){
		com.github.pagehelper.Page<T> p = startPage(pge);
		List<T> list = baseDao.selectByExample(example);
		pge.setRecords((int)p.getTotal());
		pge.setRs(list);
		return pge;
	}
	
	
	protected com.github.pagehelper.Page<T> startPage(Page<T> pge) {
		String orderby = null;
		if(StringUtils.isNotBlank(pge.getSidx())) {
			orderby = (pge.getSidxCol()+" "+pge.getSord());
		}
		com.github.pagehelper.Page<T> p = PageHelper.startPage(pge.getPage(), pge.getRows(), orderby);
		return p;
	}
	

}
