package cn.itheima.search.interfaces;

import cn.itheima.commons.pojo.SearchResult;

public interface ISearchService {

	SearchResult search(String keyword, int page, int rows)  throws Exception;
}
