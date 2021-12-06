package edu.soft1.controller;

import edu.soft1.pojo.User;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;
import java.util.UUID;


@Controller
@RequestMapping(value = "/param1")
public class MyController {

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




//@RequestMapping("/hello")
//    public String hello(HttpServletRequest request){
//        System.out.println("---hello()---");
//        return "hello";
//    }

//    @RequestMapping("/hello")
//    public ModelAndView hello(){
//        System.out.println("---hello()---");
//        ModelAndView mav = new ModelAndView();
//        mav.setViewName("hello");
//        mav.addObject("msg","I'm peter!");
//        return mav;
//    }

//    @RequestMapping("/hello")
//    public String hello(String username, Model model){
//        System.out.println("---hello()---");
////       model.addAttribute("username",username);
//       model.addAttribute(username);
//        return "hello";
//    }

        @RequestMapping("/hello")
    public String hello(String username, Map<String,String> map){
        System.out.println("---hello()---");
       map.put("username    ",username);
        return "hello";
    }


    @RequestMapping(value = "/param1",method = {RequestMethod.GET})
    public String param1(HttpServletRequest request){
    //接收client发来的数据
    String name = request.getParameter("name");

    System.out.println("name="+name);
    request.setAttribute("name",name);//将数据存入request
    //调用业务层的方法
    //页面跳转
        return "hello";
    }
    @RequestMapping(value = "/param2",method = {RequestMethod.GET,RequestMethod.POST})
    public String param2(HttpServletRequest request, HttpSession session){
        //接收client发来的数据
        String name = request.getParameter("username");
        String age = request.getParameter("age");
        System.out.println("name="+name+",age="+age);
        session.setAttribute("age",age);
        request.setAttribute("name",name);//将数据存入request
        return "hello";
    }

    @RequestMapping(value = "/param3",method = {RequestMethod.POST})
    public String param3(String username,int age){
       System.out.println("---param3()---");
        System.out.println("username="+username);
        System.out.println("age="+age);
        return "hello";
    }

    @RequestMapping(value = "/param4",method = {RequestMethod.POST})
    public String param4(
            @RequestParam(value = "username",required = false) String u,
            @RequestParam(value = "age",defaultValue = "18")int a,HttpSession session){
        System.out.println("---param4()---");
        System.out.println("u="+u);
        System.out.println("a="+a);
        session.setAttribute("name",u);
        return "redirect:test";
    }


    @RequestMapping(value = "/param5",method = {RequestMethod.POST})
    public String param5(User user,HttpSession session){
        System.out.println("---param5()---");
        System.out.println("username="+user.getUsername());
        System.out.println("age="+user.getAge());
        session.setAttribute("name",user.getUsername());
        return "redirect:test";
    }


    @RequestMapping("test")
    public String test(){
    System.out.println("---test---");
    return "hello";
    }

    @RequestMapping("/reg")
    public String reg(User user){
        System.out.println("username="+user.getUsername());
        System.out.println("pwd="+user.getAge());
        System.out.println("birthday="+user.getBirthday());
        System.out.println("city="+user.getAddress().getCity());
        System.out.println("street="+user.getAddress().getStreet());
        System.out.println("phone="+user.getAddress().getPhone());
        return "redirect:/param1/test";
    }
}
