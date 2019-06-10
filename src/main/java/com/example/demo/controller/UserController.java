package com.example.demo.controller;


import com.example.demo.bean.Result;
import com.example.demo.bean.User;
import com.example.demo.bean.Word;
import com.example.demo.service.UserService;
import com.example.demo.service.WordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private WordService wordService;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * 保存文章
     *
     * @param request
     * @param account
     * @param title
     * @param content
     * @return
     */
    @RequestMapping(value = "/saveWord", method = RequestMethod.POST)
    @ResponseBody
    public Result saveWord(HttpServletRequest request,
                           @RequestParam(name = "userName") String account,
                           @RequestParam(name = "title") String title,
                           @RequestParam(name = "word") String content) {
        Result ret = new Result();
        ret.setCode(500);
        if (account != null && title != null && content != null) {
            User user = this.userService.getUserByAccount(account);
            if (null != user) {
                Word word = new Word();
                word.setUid(user.getId());
                word.setTime(new Date());
                word.setTitle(title);
                word.setContent(content);
                if (wordService.saveWord(word)) {
                    ret.setCode(200);
                    ret.setMessage("ok");
                    logger.info("文章保存成功");
                } else {
                    ret.setMessage("error");
                    logger.info("文章保存失败");
                }
            } else {
                ret.setMessage("invalid");
                logger.info("用户不存在");
            }
        } else {
            ret.setMessage("param error");
        }
        return ret;
    }


    /**
     * 检索文章
     *
     * @param key
     * @return
     */
    @RequestMapping(value = "/searchWord", method = RequestMethod.GET)
    @ResponseBody
    public Result searchWord(@RequestParam(name = "key") String key) {
        Result ret = new Result();
        ret.setCode(500);
        if (key != null) {
            List<Word> data = this.wordService.searchWord(key);
            if (null != data) {

                ret.setCode(200);
                ret.setMessage("ok");
                ret.setData(data);
                logger.info("检索成功，检索到" + data.size() + "个");
            } else {
                ret.setMessage("no data");
                logger.info("没数据");
            }
        } else {
            ret.setMessage("param error");
            logger.error("参数不正确");
        }
        return ret;
    }

    /**
     * 登录
     *
     * @param account
     * @param pwd
     * @return
     */
    @RequestMapping(value = "/login")
    @ResponseBody
    public Result login(@RequestParam(name = "userName", required = true) String account,
                        @RequestParam(name = "pwd") String pwd) {
        User user = this.userService.getUserByAccount(account);
        Result ret = new Result();
        ret.setCode(500);
        if (null != user) {
            if (user.getPassword().equals(pwd)) {
                ret.setCode(200);
                ret.setMessage("ok");
            } else {
                ret.setMessage("invalid");
            }
        } else {
            ret.setMessage("Account error");
        }
        logger.info("login with response " + ret.getMessage());
        return ret;
    }


    /**
     * 注册
     *
     * @param request
     * @param account
     * @param pwd
     * @param email
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Result register(HttpServletRequest request,
                           @RequestParam(name = "userName", required = true) String account,
                           @RequestParam(name = "pwd") String pwd,
                           @RequestParam(name = "email") String email) {
        Result ret = new Result();
        ret.setCode(500);
        if (account != null && pwd != null && email != null) {

            User userObj = new User();
            userObj.setEmail(email);
            userObj.setUsername(account);
            userObj.setPassword(pwd);
            userObj.setRegTime(new Date());
            userObj.setRole("2");
            userObj.setStatus(1);
            userObj.setRegIp(getIpAddress(request));
            boolean result = userService.addUser(userObj);
            if (result) {
                ret.setCode(200);
                ret.setMessage("ok");
            }
        } else {
            ret.setMessage("invalid");
        }
        logger.info("register with response " + ret.getMessage());
        return ret;

    }

    /**
     * 获取用户信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/showUser")
    @ResponseBody
    public Result getUser(@RequestParam(name = "id", required = true) int id) {
        User user = this.userService.getUserById(id);
        Result ret = new Result();
        ret.setCode(200);
        ret.setData(user);
        ret.setMessage("ok");
        return ret;
    }

    /**
     * 更改用户信息
     *
     * @param id
     * @param email
     * @return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Result modifyUser(@RequestParam(name = "id", required = true) int id,
                             @RequestParam(name = "email") String email) {
        User user = userService.getUserById(id);
        if (null != user) {
            user.setEmail(email);
        }
        userService.modify(user);
        Result ret = new Result();
        ret.setCode(200);
        ret.setMessage("ok");
        return ret;
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


}
