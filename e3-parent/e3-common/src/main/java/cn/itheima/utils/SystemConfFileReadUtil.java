package cn.itheima.utils;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.log4j.Logger;

public class SystemConfFileReadUtil {

	private Logger logger = Logger.getLogger(SystemConfFileReadUtil.class);
	private String path = null;
	private Configuration configuration = null;
	  
	public SystemConfFileReadUtil(String path)
	{
	    this.path = path;
	    try
	    {
	      init();
	    }
	    catch (Exception e)
	    {
	      this.logger.error("初始失败", e);
	    }
	}
	  
	private void init() throws Exception
	{
	    if (org.apache.commons.lang3.StringUtils.endsWith(this.path, "properties"))
	    {
	      this.configuration = new PropertiesConfiguration(this.path);
	      
	      ((PropertiesConfiguration)this.configuration).setReloadingStrategy(new FileChangedReloadingStrategy());
	      
	      ((PropertiesConfiguration)this.configuration).setAutoSave(true);
	    }
	    else if (!org.apache.commons.lang3.StringUtils.endsWith(this.path, "xml"))
	    {
	      throw new Exception("没有该类型的解析器");
	    }
	}
	  
	public Configuration getConfiguration()
	{
	    return this.configuration;
	}
	  
}
