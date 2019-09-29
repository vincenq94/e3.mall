package cn.itheima.manager.web.controller;

import cn.itheima.commons.controller.BaseController;
import cn.itheima.commons.utils.FastDFSClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RequestMapping(value="pictrueController")
@Controller
public class PictrueController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("IMAGE_SERVER_URL")
    private String IMAGE_SERVER_URL;

    @RequestMapping(value="uploadFile")
    @ResponseBody
    public Map uploadFile(MultipartFile uploadFile) {
        Map<String, Object> map = new HashMap<>();
        try {
            //把图片上传的图片服务器
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:conf/client.conf");
            //取文件扩展名
            String originalFilename = uploadFile.getOriginalFilename();
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            //得到一个图片的地址和文件名
            String url = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
            //补为完整的url
            url = IMAGE_SERVER_URL+url;
            //封装到map中返回
            map.put("error",0);
            map.put("url",url);
        } catch (Exception e) {
            logger.error("图片上传失败异常", e);
            e.printStackTrace();
            map.put(_MESSAGE, "图片上传失败");
            map.put("error",1);
        }
        return map;
    }





}
