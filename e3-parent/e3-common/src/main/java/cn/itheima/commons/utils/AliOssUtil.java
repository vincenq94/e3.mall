//package cn.itheima.commons.utils;
//
//import com.aliyun.oss.ClientConfiguration;
//import com.aliyun.oss.OSSClient;
//import com.aliyun.oss.model.ObjectMetadata;
//import com.aliyun.oss.model.PutObjectResult;
//
//import java.io.InputStream;
//
//public class AliOssUtil {
//
//	public static SystemConfFileReadUtil readUtil = new SystemConfFileReadUtil("jlake_oss.properties");
//	public static String putObject(InputStream content, String path, String contentType, long fileSize)
//	    throws Exception
//	{
//	    ClientConfiguration conf = new ClientConfiguration();
//
//	    conf.setMaxConnections(10);
//
//	    conf.setConnectionTimeout(5000);
//
//	    conf.setMaxErrorRetry(3);
//
//	    conf.setSocketTimeout(5000);
//
//	    OSSClient client = new OSSClient(readUtil.getConfiguration().getString("endpoint"),
//	    		readUtil.getConfiguration().getString("accesskey_id"),
//	    		readUtil.getConfiguration().getString("accesskey_secret"),  conf);
//
//	    ObjectMetadata meta = new ObjectMetadata();
//
//	    meta.setContentLength(fileSize);
//	    meta.setContentType(contentType);
//
//	    PutObjectResult result = client.putObject(
//	    		readUtil.getConfiguration().getString("bucketname"),
//	    		path, content, meta);
//	    return readUtil.getConfiguration().getString("domainurl") + "/" + path;
//	 }
//
//	 public static void delObject(String path)
//	    throws Exception
//	  {
//	    ClientConfiguration conf = new ClientConfiguration();
//
//	    conf.setMaxConnections(10);
//
//	    conf.setConnectionTimeout(5000);
//
//	    conf.setMaxErrorRetry(3);
//
//	    conf.setSocketTimeout(5000);
//
//	    OSSClient client = new OSSClient(readUtil.getConfiguration().getString("endpoint"), readUtil.getConfiguration().getString("accesskey_id"), readUtil.getConfiguration().getString("accesskey_secret"), conf);
//
//	    client.deleteObject(readUtil.getConfiguration().getString("bucketname"), path);
//	  }
//
//	 public static String getWeiXinPushUrl(String path)
//	    throws Exception
//	  {
//	   return readUtil.getConfiguration().getString(path);
//	  }
//}
