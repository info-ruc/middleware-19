package com.wjx.ct.consumer.dao;

import com.wjx.ct.common.bean.BaseDao;
import com.wjx.ct.common.constant.Names;
import com.wjx.ct.common.constant.ValueConstant;
import com.wjx.ct.consumer.bean.Calllog;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangjianxin
 * @create 2019-10-23-13:13
 */
public class HBaseDao extends BaseDao {
    public void init() throws Exception {
        start();
        createNamepsaceNX(Names.NAMESPACE.getValue());
        createTableXX(Names.TABLE.getValue(), ValueConstant.REGION_COUNT,Names.CF_CALLER.getValue(),Names.CF_CALLEE.getValue());
        end();
    }
    public void insertDate(Calllog log)throws Exception{
        log.setRowkey(genRegionNum(log.getCall1(),log.getCalltime())+"_"+log.getCall1()+"_"+log.getCall2()+"_"+log.getCalltime()+"_"+log.getDuration());
        putData(log);
    }
    public void insertData(Calllog log) throws Exception {
        log.setRowkey(genRegionNum(log.getCall1(),log.getCalltime())+"_"+log.getCall1()
                +"_"+log.getCalltime()+"_"+log.getCall2()+"_"+log.getDuration());
        System.out.println(22222);
        putData(log);
    }
    public void insertData(String value) throws Exception {
        //将通话日志保存到hbase的表中
        //1. 获取通话日志
        //2.创建数据对象
        String[] values= value.split("\t");
        String call1= values[0];
        String call2= values[1];
        String calltime= values[2];
        String duration= values[3];

//
//        String rowkey=REGION_Num+call1+calltime+call2+duration;
//        长度原则。（64kb推荐长度为10-100byte） 8的倍数 能短则短 rowkey如果太长，影响性能
//        唯一原则。rowkey 应该具备唯一性
//        散列原则。3-1 盐值散列，不能使用时间戳作为rowkey，
//        rowkey增加随机数
//        字符串反转
//        计算分区好hashmap
//        String rowkey=genRegionNum(call1,calltime)+"_"+calltime+"_"+call2+"_"+duration;
        String rowkey = genRegionNum(call1, calltime) + "_" + call1 + "_" + calltime + "_" + call2 + "_" + duration + "_1";
        System.out.println("rowkey成功");
        // 主叫用户
        Put put = new Put(Bytes.toBytes(rowkey));

        byte[] family = Bytes.toBytes(Names.CF_CALLER.getValue());

        put.addColumn(family, Bytes.toBytes("call1"), Bytes.toBytes(call1));
        put.addColumn(family, Bytes.toBytes("call2"), Bytes.toBytes(call2));
        put.addColumn(family, Bytes.toBytes("calltime"), Bytes.toBytes(calltime));
        put.addColumn(family, Bytes.toBytes("duration"), Bytes.toBytes(duration));
        put.addColumn(family, Bytes.toBytes("flg"), Bytes.toBytes("1"));

        String calleeRowkey = genRegionNum(call2, calltime) + "_" + call2 + "_" + calltime + "_" + call1 + "_" + duration + "_0";

        // 被叫用户
        Put calleePut = new Put(Bytes.toBytes(calleeRowkey));
        byte[] calleeFamily = Bytes.toBytes(Names.CF_CALLEE.getValue());
        calleePut.addColumn(calleeFamily, Bytes.toBytes("call1"), Bytes.toBytes(call2));
        calleePut.addColumn(calleeFamily, Bytes.toBytes("call2"), Bytes.toBytes(call1));
        calleePut.addColumn(calleeFamily, Bytes.toBytes("calltime"), Bytes.toBytes(calltime));
        calleePut.addColumn(calleeFamily, Bytes.toBytes("duration"), Bytes.toBytes(duration));
        calleePut.addColumn(calleeFamily, Bytes.toBytes("flg"), Bytes.toBytes("0"));

        // 3. 保存数据
        List<Put> puts = new ArrayList<Put>();
        puts.add(put);
        puts.add(calleePut);

        putData(Names.TABLE.getValue(), puts);
    }
}
