package com.example.data.controller;


import com.example.data.service.DataUserApiService;
import com.example.data.utils.Result;
import com.example.data.utils.ResultUtil;
import com.example.data.utils.bcrypt.BCryptPasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


@CrossOrigin
@RestController
@RequestMapping("/api")
public class DataUserApiController {

    private static final Logger logger = LoggerFactory.getLogger(DataUserApiController.class);
    @Autowired
    private DataUserApiService dataUserApiService;

    //查询用户名是否重复
    @RequestMapping(value = "/selectName",method = RequestMethod.GET)
    public Result showName(String username,HttpServletRequest request) throws Exception{
        List name=dataUserApiService.selectName(username);
        if(name == null){
            return ResultUtil.success();
        }else{
            return ResultUtil.error("用户名已存在！");
        }
    }

    //首页九宫格显示
    @RequestMapping(value = "/gridshow",method = RequestMethod.GET)
    public Result gridshow() throws Exception{
        List list=dataUserApiService.gridshow();
        if(list.isEmpty()){
            return ResultUtil.error("暂无数据！");
        }else{
            return ResultUtil.success(list);
        }
    }

    //导入库接口
    @RequestMapping(value = "/importProject",method = RequestMethod.POST)
    public Result importProject(@RequestBody Map<String, String> body) throws Exception{
        String project_name=body.get("project_name");
        String type = body.get("type");
        String city_name = body.get("city_name");
        String area = body.get("area");
        String lt = body.get("lt");
        String username = body.get("username");
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式
        String project_time = dateFormat.format(now);

        String projectName=dataUserApiService.selectProName(project_name);
        if(projectName!=null){
            return ResultUtil.success("项目名称已存在，请修改后保存！");
        }else{
            int num=dataUserApiService.importProject(project_name,city_name,type,project_time,username,lt,area);
            if(num>0){
                return ResultUtil.success("保存成功！");
            }else{
                return ResultUtil.error("保存失败！");
            }
        }
    }

    //导出库接口
    @RequestMapping(value = "/exportProject",method = RequestMethod.GET)
    public Result exportProject(String project_name) throws Exception{
        List project = dataUserApiService.exportProject(project_name);
        List list = dataUserApiService.projectInfoPoint(project_name);
        Map map = new HashMap();
        map.put("project",project);
        map.put("point",list);
        if(project.isEmpty()){
            return ResultUtil.error("导出失败！");
        }else {
            return ResultUtil.success(map);
        }
    }

    //显示所有项目名称
    @RequestMapping(value = "/projectInfo",method = RequestMethod.GET)
    public Result projectInfo(){
        List list =  dataUserApiService.projectInfo();
        return ResultUtil.success(list);
    }

    //打点标记
    @RequestMapping(value = "/pointed",method = RequestMethod.POST)
    public Result pointed(@RequestBody Map<String, String> body) throws Exception{
        String project_name = body.get("project_name");
        String point_lon = body.get("point_lon");
        String point_lat = body.get("point_lat");
        String point_describe = body.get("point_describe");
//        String point_time = body.get("point_time");
        int num = dataUserApiService.pointed(project_name,point_lon,point_lat,point_describe);
        if(num>0){
            return ResultUtil.success("标记成功!");
        }else{
            return ResultUtil.error("标记失败！");
        }
    }


    //重置密码查询用户名
    @RequestMapping(value = "/showName",method = RequestMethod.GET)
    public Result selectName(String username) throws Exception{
        String name=dataUserApiService.showName(username);
        if(name == null){
            return  ResultUtil.error("此用户名不存在！");
        }else{
            return ResultUtil.success(name);
        }
    }

    //发送邮件
    @RequestMapping(value = "/eamil",method = RequestMethod.GET)
    public  Result mail(String username) throws MessagingException, IOException {
        String eamil = dataUserApiService.selectEmail(username);
        if(eamil==null){
            return ResultUtil.error("暂无此用户或暂无此用户的邮箱！");
        }else {
            logger.info("开始发送邮件！");
            Properties props = new Properties();
            // 读取配置文件
            props.load(this.getClass().getResourceAsStream("/emailConfig.properties"));
            Session session = Session.getInstance(props);
            // 创建邮件对象
            Message msg = new MimeMessage(session);
            try {
                msg.setSubject("修改密码");
            } catch (MessagingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            // 设置邮件内容
            String msgContent = "亲爱的 " + username + "  您好：<br/><br/>"
                    + "您在&nbsp;" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "&nbsp;提交重置密码的请求。<br/><br/>"
                    + "请打开以下链接重置密码：<br/><br/>"
                    + "<a href='http://62.234.56.122:8088/demo/email.html'>http://" + props.getProperty("url") + "/email.html</a><br/><br/>"
                    + "(如果上面不是链接形式，请将该地址手工粘贴到浏览器地址栏再访问)。" + "<br/><br/>"
                    + "如果您没有请求重置密码，请忽略此邮件。" + "<br/><br/>"
                    + "此为自动发送邮件，请勿直接回复！";

            msg.setContent(msgContent, "text/html;charset=utf-8");// 设置邮件内容，为html格式
            // 设置发件人
            msg.setFrom(new InternetAddress(MimeUtility.encodeText("修改密码") + " <" + props.getProperty("mail.username") + ">"));// 设置邮件来源
            Transport transport = session.getTransport();
            // 连接邮件服务器
            transport.connect(props.getProperty("mail.username"), props.getProperty("mail.password"));
            // 发送邮件
            transport.sendMessage(msg, new Address[]{new InternetAddress(eamil)});
            // 关闭连接
            transport.close();
            logger.info("发送邮件成功！");
            return ResultUtil.success("发送邮件成功！");
        }
    }

    //重置密码
    @RequestMapping(value = "/updatePwd", method = RequestMethod.POST)
    public Result updatePwd(@RequestBody Map<String, String> body) throws Exception{
        String username = body.get("username");
        String password = body.get("password");
//        String pwd = dataUserApiService.showPwd(username);
//        Boolean end=new BCryptPasswordEncoder().matches(password, pwd); //检查是否为原密码
//        if (end == false) {
//            return ResultUtil.error("旧密码不正确！");
//        }else{
            int num=dataUserApiService.updatePwd(username,new BCryptPasswordEncoder().encode(password));
            if(num>0){
                return ResultUtil.success("修改成功！");
            }else{
                return ResultUtil.error("异常状况，请联系管理员！");
            }
        }
}
