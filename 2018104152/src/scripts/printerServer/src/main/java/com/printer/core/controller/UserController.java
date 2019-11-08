package com.printer.core.controller;

import com.printer.core.entity.User;
import com.printer.core.service.FileOrderService;
import com.printer.core.service.UserService;
import com.printer.core.utils.JsonUtil;
import com.printer.core.utils.MessageUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;

@RestController
@RequestMapping("/api")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private FileOrderService fileOrderService;

    @RequestMapping(value="/login", method= RequestMethod.POST)
    @ResponseBody
    // 如果cookie中的sessionId命中，则获取到该session；否则创建新的session
    public String login(@RequestBody User user, HttpSession session) {
        String userName = user.getUserName();
        String password = user.getPassword();
        long userId;

        // session中存在userId，则让用户直接登陆，肯定是旧用户
        if (session.getAttribute("userId") != null) {
            // 使用Long进行强转，然后自动拆箱
            userId = (Long) session.getAttribute("userId");

            System.out.println("Auto login:" + userId);
            // 返回订单表

            return JsonUtil.marshal(fileOrderService.getOrdersByUserId(userId));
        }

        // 如果session未命中，且用户名为空（自动登陆），则返回失败
        else if (userName == null) {
            return MessageUtil.getMessage("failed");
        }

        // session未命中，但请求中有用户名 （过期或注销）
        else {
            User userData = userService.getUserByName(userName);
            // 如果是新用户登陆则存入用户表，返回登陆成功
            if (userData == null) {
                user.setCreateTime(new Date());
                User newUser = userService.addUser(user);
                userId = newUser.getUserId();

                // 在session中设置userId
                session.setAttribute("userId", userId);

                System.out.println(userId);
                return "{}";
            }
            // 旧用户则返回订单列表
            else if(userData.getPassword().equals(password)){

                userId = userData.getUserId();
                // 在session中设置userId
                session.setAttribute("userId", userId);

                // 返回订单表
                return JsonUtil.marshal(fileOrderService.getOrdersByUserId(userId));
            }else{
                return "{\"message\":\"password error\"}";
            }
        }
        // 如果是新session则在响应中添加set-cookie，内含sessionId
    }


    @RequestMapping(value="/logout", method=RequestMethod.GET)
    @ResponseBody
    public String logout(HttpSession session) {

        //session.invalidate();                    // 删除整个session，下次访问创建新的
        session.removeAttribute("userId");      // 删除属性，session仍存在

        return MessageUtil.getMessage("success");
    }
}
