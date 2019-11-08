package com.wjx.ct.common.bean;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * @author wangjianxin
 * @create 2019-10-24-9:15
 */

public class TestAPI {
    public static Configuration conf;
    private ThreadLocal<Connection> connHolder = new ThreadLocal<Connection>();
    private static ThreadLocal<Admin> adminHolder = new ThreadLocal<Admin>();
    protected void start() throws Exception {
        getConnection();
        getAdmin();
    }
    public static Connection getConnection() throws Exception{
        //创建连接对象
        conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "192.168.43.176");
        conf.set("hbase.zookeeper.property.clientPort", "2181");
        Connection connection = ConnectionFactory.createConnection(conf);
        System.out.println(connection);
        return connection;
    }
    protected synchronized Admin getAdmin() throws Exception {
        Admin admin = adminHolder.get();
        if ( admin == null ) {
            admin = getConnection().getAdmin();
            adminHolder.set(admin);
        }
        return admin;
    }


//    static {
//        //1.获取配置文件信息
////             HBaseConfiguration conf= new HBaseConfiguration();
////            configuration.set("hbase.zookeeper.quorum","192.168.43.176");
//////            configuration.set("hbase.zookeeper.property.clientPort", "2181");
////            conf = HBaseConfiguration.create();
////            conf.set("hbase.zookeeper.quorum", "192.168.43.176");
////            conf.set("hbase.zookeeper.property.clientPort", "2181");
//        //2.创建连接对象
//        conf = HBaseConfiguration.create();
//        conf.set("hbase.zookeeper.quorum", "192.168.43.176");
//        conf.set("hbase.zookeeper.property.clientPort", "2181");
//        try {
//            Connection connection = ConnectionFactory.createConnection(conf);
//            System.out.println(connection);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
////        System.out.println(connection);
////        System.out.println("创建连接对象connection");
////        //3.创建admin对象
////        admin = connection.getAdmin();
////        System.out.println(admin);
//    }
//    static{
////使用 HBaseConfiguration 的单例方法实例化
//        conf = HBaseConfiguration.create();
//        conf.set("hbase.zookeeper.quorum", "192.168.43.176");
//        conf.set("hbase.zookeeper.property.clientPort", "2181");
//    }
//    判断表是否存在
//    public static boolean isTableExist(String tableName) throws IOException {
//        //3.判断表是否存在
//        boolean exists= admin.tableExists(TableName.valueOf(tableName));
//        //5.返回结果
//        return exists;
//    }

//    public static void close(){
//        if(admin!=null) {
//            try {
//                admin.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        if (connection!=null){
//            try {
//                connection.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//    public static void createTable(String tableName,String...cfs) throws IOException {
//        //1.判断是否存在列族信息
//        if(cfs.length<=0){
//            System.out.println("请设置列族信息");
//            return;
//        }
//        //2.判断表是否存在
//        if(isTableExist(tableName)){
//            System.out.println("表已经存在");
//            return;
//        }
//        //3.创建表
//
//        HTableDescriptor hTableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));
//        //4.循环添加列族信息
//        for (String cf : cfs) {
//            //5.创建列族描述器
//            HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(cf);
//            //6.添加具体列族信息
//            hTableDescriptor.addFamily(hColumnDescriptor);
//        }
//        admin.createTable(hTableDescriptor);
//    }
//    //删除表
//    public static void dropTable(String tableName) throws IOException {
//        //1.判断表是否存在
//        if(!isTableExist(tableName)){
//            System.out.println(tableName+"表不存在");
//        }
//        //2.使表下线
//        admin.disableTable(TableName.valueOf(tableName));
//        //3.删除表
//        admin.deleteTable(TableName.valueOf(tableName));
//    }
//   //创建命名空间
//    public static void createNameSpace(String ns){
//        NamespaceDescriptor namespaceDescriptor = NamespaceDescriptor.create(ns).build();
//        try {
//            admin.createNamespace(namespaceDescriptor);
//        } catch (NamespaceExistException e){
//            System.out.println("命名空间存在");
//        }catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println("存在也在这");
//    }
    //向表插入数据
    public static void putData(String tableName,String rowKey,String cf,String cn,String value) throws Exception {
        //创建连接对象
//        System.out.println(conf);
//        Connection connection = ConnectionFactory.createConnection(conf);
//        System.out.println(connection);
        Connection conn = getConnection();
        Table table = conn.getTable(TableName.valueOf(tableName));
//        System.out.println(TestAPI.connection);
//        Table table =TestAPI.connection.getTable(TableName.valueOf(tableName));
        Admin admin = conn.getAdmin();
//        TableName table = TableName.valueOf(tableName);
        System.out.println("连接成功");
        //创建put对象
        Put put = new Put(Bytes.toBytes(rowKey));
        //给put对象赋值
        put.addColumn(Bytes.toBytes(cf),Bytes.toBytes(cn),Bytes.toBytes(value));
        table.put(put);
        //5.关闭连接
        table.close();
        System.out.println("插入数据成功");
    }
//    public static void addRowData(String tableName, String rowKey, String columnFamily, String column, String value) throws IOException{
////创建 HTable 对象
////        HTable hTable = new HTable(conf, tableName);
//        Table hTable = connection.getTable(TableName.valueOf(tableName));
////向表中插入数据
//        Put put = new Put(Bytes.toBytes(rowKey));
////向 Put 对象中组装数据
//        put.add(Bytes.toBytes(columnFamily), Bytes.toBytes(column), Bytes.toBytes(value));
//        put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes("sex"), Bytes.toBytes("male"));
//        hTable.put(put);
//        hTable.close();
//    }
//    //获取数据
//    public static void getData(String tableName,String rowkey,String cf,String cn) throws IOException {
//      //1.获取表对象
////        HTable table = new HTable(conf, tableName);
//        Table table = connection.getTable(TableName.valueOf(tableName));
//        //2.获取get
//        Get get = new Get(Bytes.toBytes(rowkey));
//        get.addFamily(Bytes.toBytes(cf));
//        get.addColumn(Bytes.toBytes(cf),Bytes.toBytes(cn));
//        //3.获取数据
//        Result result = table.get(get);
//        for (Cell cell : result.rawCells()) {
//            //5.打印数据
//            System.out.println(
//                    "CF"+Bytes.toString(CellUtil.cloneFamily(cell))+
//                    ",CN"+Bytes.toString(CellUtil.cloneQualifier(cell))+
//                    ",Value"+Bytes.toString(CellUtil.cloneValue(cell)));
//        }
//        table.close();
//    }
//    public static void scanTable(String tableName) throws IOException {
//        //1.获取对象
////        HTable table = new HTable(conf, tableName);
//        Table table = connection.getTable(TableName.valueOf(tableName));
//        //2.扫描表
//        Scan scan = new Scan();
//        //3.扫描表
//        ResultScanner scanner = table.getScanner(scan);
//        //4.解析resultScanner
//
//        for (Result result : scanner) {
//           //5.解析result并打印
//            for (Cell cell : result.rawCells()) {
//                System.out.println(
//                        "CF"+Bytes.toString(CellUtil.cloneFamily(cell))+
//                                ",CN"+Bytes.toString(CellUtil.cloneQualifier(cell))+
//                                ",Value"+Bytes.toString(CellUtil.cloneValue(cell)));
//            }
//            }
//        //7.关闭资源
//        }
//     //8.删除数据
//    public static  void deleteData(String tableName,String rowkey,String cf,String cn) throws IOException {
//        //1.获取对象
////        HTable table = new HTable(conf, tableName);
//        Table table = connection.getTable(TableName.valueOf(tableName));
//        //2.构建删除对象
//        Delete delete = new Delete(Bytes.toBytes(rowkey));
//        //2.1构建删除的列
//        delete.addColumns(Bytes.toBytes(cf),Bytes.toBytes(cn));
//        //2.2执行删除操作
//        table.delete(delete);
//        System.out.println(1);
//        //3.关闭连接
//        table.close();
//    }

public static void main(String[] args) throws Exception {
        // 创建消费者
        //1.测试表是否存在
//        System.out.println(isTableExist("student"));
//        //2.创建表测试
//          createTable("stu","info2");
//        //3.判断是否创建成功
//         System.out.println(isTableExist("stu8"));
//        //4.删除表测试
//        dropTable("stu");
//        System.out.println(isTableExist("stu"));
//        //5.创建命名空间
//        createNameSpace("0408");
        //addRowData("stu8","1001","info","name","lisi");
         putData("stu8","1002","info","name","li8");
         System.out.println(1);
        //获取单行数据
//        getData("stu8","1001","info","name");
//        scanTable("stu8");
//        deleteData("stu8","1001","info","name");
//        scanTable("stu8");
//        //关闭资源
//        close();
    }
}

