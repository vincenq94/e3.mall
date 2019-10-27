package cn.e3.mall.fasedfs;

import org.csource.fastdfs.*;

public class FastDfsTest {

    public void testUpload() throws Exception{
        //图片绝对路径
        String url = "C:\\Users\\vincenq\\Pictures\\u=3370329412,3385713135&fm=26&gp=0.jpg";
        //创建一个配置文件。文件名任意，内容就是tracker服务器的地址
        //使用全局对象加载配置文件
        ClientGlobal.init("D:\\development\\IDEAworkspace\\e3.mall\\e3-parent\\e3-manager-web\\src\\main\\resources\\conf\\client.conf");
        //创建一个TrackerClient对象
        TrackerClient trackerClient = new TrackerClient();
        //通过TrackClient获得一个TrackerServer对象
        TrackerServer trackerServer = trackerClient.getConnection();
        //创建一个StrorageServer的引用，可以是null
        StorageServer storageServer = null;
        //创建一个StorageClient，参数需要TrackerServer和StrorageServer
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        //使用StorageClient上传文件
        String[] strings = storageClient.upload_file(url,"jpg",null);

        for (String string : strings) {
            System.out.println(string);
        }

    }
}
