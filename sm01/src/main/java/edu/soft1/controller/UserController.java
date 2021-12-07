package edu.soft1.controller;

import edu.soft1.pojo.User;
import net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    @RequestMapping(value = "upload",method = {RequestMethod.POST})
    public String fileUpload(MultipartFile image,HttpServletRequest request) throws IOException {
        InputStream is = image.getInputStream();
        String filename = image.getOriginalFilename();
        String realPath = request.getServletContext().getRealPath("/images");
        System.out.println("上传路径="+realPath);
        FileOutputStream os = new FileOutputStream(new File(realPath,doFileName(filename)));
        int size = IOUtils.copy(is,os);
        System.out.println("完成上传size="+size+"Byte");
        os.close();is.close();
        return "welcome";
    }

    private String doFileName(String filename) {
        String extension = FilenameUtils.getExtension(filename);
        String uuid = UUID.randomUUID().toString();
        System.out.println("上传文件名"+uuid);
        return uuid+"."+extension;
    }

    @RequestMapping(value = "upload2",method = {RequestMethod.POST})
    public String fileUpload2(MultipartFile[] images,HttpServletRequest request,Map<String,Object> map) throws IOException {
        InputStream is = null;OutputStream os = null;
        int count = 0;
        for (MultipartFile image:images) {
            is = image.getInputStream();
            String filename = image.getOriginalFilename();
            System.out.println("文件原名称=" + filename);
            if (filename.equals("")) {
                continue;}
            String realPath = request.getServletContext().getRealPath("/images");
            System.out.println("上传路径=" + realPath);
            os = new FileOutputStream(new File(realPath, doFileName(filename)));
            int size = IOUtils.copy(is, os);
            if (size > 0) {count++;}
        }
        os.close();is.close();
        map.put("msg2",count);
        System.out.println("成功上传"+count+"张图片！");
        return "welcome";
    }
    private String doFileName2(String filename) {
        String extension = FilenameUtils.getExtension(filename);
        String uuid = UUID.randomUUID().toString();
        System.out.println("上传文件名"+uuid);
        return uuid+"."+extension;
    }

    @RequestMapping(value="/load.do/{fileName}",method={RequestMethod.GET})
    public void load(@PathVariable String fileName, HttpServletRequest request, HttpServletResponse response)
            throws IOException{
        //设置文件下载
        response.setHeader("Content-Disposition","attachment;filename="+doFileName2(request, fileName));
        //文件存储的真实位置
        String realPath = request.getServletContext().getRealPath("/images");
        System.out.println("下载路径realPath="+realPath);
        //根据存储的文件，获取输入流对象
        InputStream is = new FileInputStream(new File(realPath, fileName));
        //根据相应对象获取输出流对象
        OutputStream os = response.getOutputStream();
        //把输入流写入输出流
        int size = IOUtils.copy(is, os);
        if (size > 0){

        }
        //释放资源，原则：先开后关，后开先关
        os.close();
        is.close();
    }
    //针对中文名称，需要分浏览器来处理
    public String doFileName2(HttpServletRequest request, String filename){
        try{
            //获取请求头部信息的User-Agent对应的值
            String userAgent=request.getHeader("User-Agent");
            if(userAgent.toUpperCase().indexOf("FIREFOX")>0){//火狐浏览器
                filename= "=?UTF-8?B?"+(new String(Base64.encodeBase64(filename.getBytes("utf-8"))))+"?=";
            }else{//其他浏览器
                filename  = URLEncoder.encode(filename,"utf-8");
            }
            System.out.println("下载文件名="+filename);
        }catch(Exception e){
            e.printStackTrace();
        }
        return filename;
    }


    @RequestMapping("/hello")
    public String hello(){
        System.out.println("----hello----");
        return "hello";
    }
    @RequestMapping(value = "/login")
    public String login(User user, HttpSession session,HttpServletRequest request){
        System.out.println("----login----");
        int flag = 1;//调用业务层
        if (flag ==1){
            System.out.println("username="+user.getUsername());
            session.setAttribute("user",user);
            return "welcome";//登录成功
        }
        System.out.println("登录失败,返回首页index");
        request.setAttribute("error","用户名或密码不正确");
        return "forward:/index.jsp";//登录失败
//        return "redirect:/index.jsp";//登录失败
    }



    @RequestMapping("/reg")
    public String reg(User user){
        System.out.println("username="+user.getUsername());
        System.out.println("pwd="+user.getPwd());
        System.out.println("pwd="+user.getAge());
        System.out.println("birthday="+user.getBirthday());
        System.out.println("city="+user.getAddress().getCity());
        System.out.println("street="+user.getAddress().getStreet());
        System.out.println("phone="+user.getAddress().getPhone());
        return "hello";
    }

    @RequestMapping("/delete")
    public String delete(HttpServletRequest request){
        System.out.println("----执行delete()成功----");
        request.setAttribute("CRUDmsg","删除功能执行完毕");
        return "hello";
    }

    @RequestMapping("/welcome")
    public String welcome(){
        System.out.println("----welcome----");
        return "welcome";
    }

    @RequestMapping("/logout")//登出功能
    public String logout(HttpSession session){
        //清空session
        session.invalidate();
        System.out.println("已登出~");
        return "redirect:/index.jsp";
    }

}
