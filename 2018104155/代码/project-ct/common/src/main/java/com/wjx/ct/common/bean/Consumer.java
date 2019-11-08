package com.wjx.ct.common.bean;

import java.io.Closeable;

/**
 * @author wangjianxin
 * @create 2019-10-23-11:35
 */
public interface Consumer extends Closeable {
    //消费数据
    public void consume();
}
