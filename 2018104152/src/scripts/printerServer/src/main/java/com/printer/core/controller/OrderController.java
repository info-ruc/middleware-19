package com.printer.core.controller;

import com.printer.core.common.dto.ResultDTO;
import com.printer.core.common.log.PLog;
import com.printer.core.entity.FileOrder;
import com.printer.core.entity.User;
import com.printer.core.service.FileOrderService;
import com.printer.core.service.RedisService;
import com.printer.core.utils.FileUtil;
import com.printer.core.utils.MessageUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Resource
    private FileOrderService fileOrderService;

    @Resource
    private RedisService redisService;

    // required=false 表示参数可以为空，file对象接收上传文件
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody                      // 表示响应内容加入响应体中返回（如果是对象可自动转化为JSON）

    public ResultDTO uploadFile(@RequestParam(name = "file", required = false) MultipartFile file, HttpSession session) {

        if (session.getAttribute("userId") == null) {
            return new ResultDTO(false, 400, "Need to login first");
        }

        long userId = (Long) session.getAttribute("userId");

        // 这一步是否移入processFile中？
        if (file == null || file.isEmpty()) {
            return new ResultDTO(false, 400, "The file is empty");
        }

        FileOrder fileOrder;

        try {
            // 处理上传的文件，转化为pdf后存放于指定路径
            fileOrder = FileUtil.processFile(file);
            // System.out.println();

            if (fileOrder != null) {
                fileOrder.setUserId(userId);
                // 添加订单到数据库
                FileOrder newOrder = fileOrderService.addFileOrder(fileOrder);
                // 返回订单信息给前端
                return new ResultDTO(true, 200, "Upload success", fileOrderService.getOrderById(newOrder.getOrderId()));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        // 如果发生异常 或 fileOrder对象为空 均会返回失败
        return new ResultDTO(false, 500, "Failed to process file");

    }

    // 对redis mq的测试
    @RequestMapping(value = "/uploadTest", method = RequestMethod.GET)
    @ResponseBody                      // 表示响应内容加入响应体中返回（如果是对象可自动转化为JSON）
    public String uploadTest(HttpSession session) {
        User user = new User();
        user.setUserName("zzz");
        try {
            redisService.rpush("123", "789");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    @RequestMapping(value = "/deleteOrders", method = RequestMethod.POST)
    @ResponseBody                      // 表示响应内容加入响应体中返回（如果是对象可自动转化为JSON）
    public ResultDTO deleteOrders(@RequestBody ArrayList<Long> orders, HttpSession session) {
        for (Long orderId : orders) {
            try {
                FileOrder fileOrder = fileOrderService.getOrderById(orderId);
                // 删除订单数据
                fileOrderService.deleteOrders(orderId);
                // 删除相应文件
                FileUtil.deleteFile(fileOrder.getPath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ResultDTO(true, 200, "Delete success");
    }
}
