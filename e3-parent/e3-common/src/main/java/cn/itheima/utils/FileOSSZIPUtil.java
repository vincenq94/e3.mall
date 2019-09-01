package cn.itheima.utils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GetObjectRequest;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 将OSS文件下载到本地并打包成zip格式保存
 */
@RestController
public class FileOSSZIPUtil {
	
	public static SystemConfFileReadUtil readUtil = new SystemConfFileReadUtil("jlake_oss.properties");
    /**
     * 从OSS服务中下载所需文件到本地临时文件
     * @param endponit oss对外服务的域名
     * @param accessKeyId 用户身份认证标识
     * @param accessKeySecret 用于加密签名字符串，oss用来验证签名字符串的秘钥
     * @param bucketName 要访问的存储空间
     * @param objectNames 要下载的对象/文件
     * @return
     */
    private static String downloadOSS(List<String> objectNames){
    	
        String basePath=System.getProperty("java.io.tmpdir")+"downloadOSSFiles";
        OSSClient ossClient = null;
        try{
        	
    	    
            //创建OSSClient实例，用于操作oss空间
            ossClient = new OSSClient(readUtil.getConfiguration().getString("endpoint"), 
    	    		readUtil.getConfiguration().getString("accesskey_id"), 
    	    		readUtil.getConfiguration().getString("accesskey_secret"));
            for (String objectName:objectNames){
                //指定文件保存路径
                String filePath = basePath+"/"+objectName.substring(0,objectName.lastIndexOf("/"));
                //判断文件目录是否存在，不存在则创建
                File file = new File(filePath);
                if (!file.exists()){
                    file.mkdirs();
                }
                //判断保存文件名是否加后缀
                if (objectName.contains(".")){
                    //指定文件保存名称
                    filePath = filePath+"/"+objectName.substring(objectName.lastIndexOf("/")+1);
                }

                //获取OSS文件并保存到本地指定路径中，此文件路径一定要存在，若保存目录不存在则报错，若保存文件名已存在则覆盖本地文件
                ossClient.getObject(new GetObjectRequest(readUtil.getConfiguration().getString("bucketname"),objectName),new File(filePath));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }finally {
            //关闭oss连接
            if (ossClient != null){
                ossClient.shutdown();
            }
        }
        return basePath;
    }

    /**
     * 从OSS服务中下载所需文件到本地临时文件，并将文件以ZIP格式进行压缩
     * @param endpoint oss对外服务的域名
     * @param accessKeyId 用户身份认证标识
     * @param accessKeySecret 用于加密签名字符串，oss用来验证签名字符串的秘钥
     * @param bucketName 要访问的存储空间
     * @param objectNames 要下载的对象/文件
     * @param zipName zip文件名
     * @throws Exception
     */
    @RequestMapping("/oss_zip")
    public static void fileToZip(HttpServletResponse response,List<String> objectNames,String zipName) throws Exception {
        File file = null;
        try{
            //调用方法货物OSS中的文件
            String fileName = downloadOSS(objectNames);
            //获取待压缩文件源
            file = new File(fileName);
            //指定压缩文件存放路径
           // String  zipFileName = "src/main/resources/zipFiles/"+zipName+".zip";
   //         String  zipFileName = "src/"+zipName+".zip";
    //        File zipFile = new File(zipFileName);
            //构建输出流
 //           FileOutputStream fout = new FileOutputStream(zipFile);
            //构建压缩输出流
//            ArchiveOutputStream aos = new ArchiveStreamFactory().createArchiveOutputStream(ArchiveStreamFactory.ZIP, fout);
            ArchiveOutputStream aos = new ArchiveStreamFactory().createArchiveOutputStream(ArchiveStreamFactory.ZIP, response.getOutputStream());
            //判断获取的压缩实例是否为zip格式
            if (aos instanceof ZipArchiveOutputStream){
                //进行压缩实例强转
                ZipArchiveOutputStream zipos = (ZipArchiveOutputStream) aos;

                //将指定文件封装成压缩项，添加到压缩流中
                //判断文件是否存在
                if (file.exists()){
                    //判断文件类型，调用文件处理方法
                    zipDir(zipos,file,"");
                }
            }
            //关闭流
            aos.flush();
            aos.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }finally {
            //删除文件源，只保留zip文件
            if (file.exists()){
                //判断文件类型
                if(file.isDirectory()){
                    //对文件夹进行处理递归删除（有内容的文件夹不能直接被删除）
                    deleteFile(file);
                    file.delete();//删除空文件夹
                }else {
                    file.delete();//文件直接删除
                }
            }
        }
    }

    /**
     * 对压缩文件夹进行循环处理
     * @param zipos 压缩流
     * @param file 要处理的文件
     * @param baseDir 要处理的文件的文件夹路径
     * @throws IOException
     */
    private static void zipDir(ZipArchiveOutputStream zipos,File file,String baseDir) throws IOException {
        if (file.isDirectory()){
            File[] files = file.listFiles();
            for (File f:files){
                //对文件进行递归判断
                zipDir(zipos,f,file.getName()+File.separator);
            }
        }else{
            //将文件封装成压缩项
            //根据文件创建zip内容实体
            ZipArchiveEntry zipArchiveEntry = new ZipArchiveEntry(file,file.getName());
            //将根据文件创建的实体保存到压缩流中
            zipos.putArchiveEntry(zipArchiveEntry);
            //将内容输出到压缩文件中
            zipos.write(FileUtils.readFileToByteArray(file));
            zipos.closeArchiveEntry();
        }
    }

    /**
     * 文件及文件夹的递归删除
     * @param file
     */
    private static void deleteFile(File file){
        if (file.isDirectory()){
            File[] files = file.listFiles();
            for (File f: files){
                deleteFile(f);
                //将循环后的空文件夹删除
                if(f.exists()){
                    f.delete();
                }
            }
        }else{
            file.delete();
        }
    }

    public static void batchExport(HttpServletResponse response,List<String> filesList) {
       // ArrayList<String> fileNames = new ArrayList<>();
        //oss的存储空间内部是扁平的，没有文件系统目录概念，所有的对象都隶属于其对应的存储空间，在下载是必须明确的指出要下在的文件，指定其上层（文件系统概念）文件夹，无法下载其内部文件
        //fileNames.add("image1/"); //WARN com.aliyun.oss - Cannot read object content stream: src\main\resources\files\image1 (拒绝访问。)
       
       // fileNames.add("up_img/20190508/1557299953854_app使用手册1.0.docx");
      
		/*
		 * fileNames.add("image1/image2/3.jpg"); fileNames.add("image1/0.jpg");
		 */
        try {
            
            fileToZip(response,filesList,"testImage");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
