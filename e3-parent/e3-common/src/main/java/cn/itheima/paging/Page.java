package cn.itheima.paging;


import org.springframework.beans.factory.InitializingBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Page<T> implements InitializingBean /* extends RowBounds*/ {

	private int rows =  0; // 每页显示数量
	
	private int page =  0; // 当前页码
	
	private String sidx; //排序字段
	
	private String sidxCol; //数据库排序字段
	
	private String sord = ""; // 排序规则 asc desc
	
	private int records = 0;//总数量
	
	private int total = 0; //总页数
	
	private int offset;
	
	private int limit;
	
	private List<T> rs;
	
	public Page() {
	}
	
	public Page(int rows, int page) {
	}
	
	public Page(int rows, int page, String sidx, String sord) {
	}
	
	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
		
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
//		rowBounds();
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public List<T> getRs() {
		return rs;
	}

	public void setRs(List<T> rs) {
		this.rs = rs;
	}
	
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getRecords() {
		return records;
	}

	public void setRecords(int records) {
		this.records = records;
	}

	public void rowBounds() {
		if(rows != 0 && page != 0) {
			this.limit = rows;
			this.offset = (page-1) * rows;
		}
	}
	
	public Map<String, Object> getMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);//当前页
		map.put("total", records%rows==0? records/rows: (records/rows)+1);//总页数
		map.put("records", records);//查询出的记录数
		map.put("rows", rs);//data
		return map;
	}


	public String getSidxCol() {
		return humpToUnderline(sidx);
	}

	public void setSidxCol(String sidxCol) {
		this.sidxCol = sidxCol;
	}

	/***
	* 驼峰命名转为下划线命名
	 * @param para
	 */
	 
	public static String humpToUnderline(String para){
        StringBuilder sb=new StringBuilder(para);
        int temp=0;//定位
        if (!para.contains("_")) {
            for(int i=0;i<para.length();i++){
                if(Character.isUpperCase(para.charAt(i))){
                    sb.insert(i+temp, "_");
                    temp+=1;
                }
            }
        }
        return sb.toString().toUpperCase();
    }

	@Override
	public void afterPropertiesSet() throws Exception {
		rowBounds();
	}

	@Override
	public String toString() {
		return "Page [rows=" + rows + ", page=" + page + ", sidx=" + sidx + ", sidxCol=" + sidxCol + ", sord=" + sord
				+ ", records=" + records + ", total=" + total + ", offset=" + offset + ", limit=" + limit + ", rs=" + rs
				+ "]";
	}
	
	
	
}
