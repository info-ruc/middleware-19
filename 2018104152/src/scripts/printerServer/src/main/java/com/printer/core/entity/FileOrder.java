package com.printer.core.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class FileOrder implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long orderId;
    private long userId;               // 后期可能替换为userName
    @Column(length = 255)
    private String fileName;
    @Column(length = 255)
    private String path;
    private int startPage;
    private int count;
    private int copies;
    private float price;
    private Date createTime;
    private int isDuplex;              // 可能有问题
    private int type;                  // 0:待打印,  1:已推送到管理员,  2:管理员完成订单
    private long adminId;

    public FileOrder() {}

    public FileOrder(String fileName, String path, int startPage, int count, int copies, Date createTime,
                     int isDuplex) {
        this.fileName = fileName;
        this.path = path;
        this.startPage = startPage;
        this.count = count;
        this.copies = copies;
        this.createTime = createTime;
        this.isDuplex = isDuplex;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getIsDuplex() {
        return isDuplex;
    }

    public void setIsDuplex(int isDuplex) {
        this.isDuplex = isDuplex;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getAdminId() {
        return adminId;
    }

    public void setAdminId(long adminId) {
        this.adminId = adminId;
    }
}

