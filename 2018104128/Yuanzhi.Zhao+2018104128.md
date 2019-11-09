## ShardingSphere Middleware

***

**Student:** Yuanzhi.Zhao 																				**Number: **2018104128

***



### Overview

​		*ShardingSphere is an open-source ecosphere consists of a set of distributed database middleware solutions, including 3 independent products, Sharding-JDBC, Sharding-Proxy & Sharding-Sidecar (todo). They all provide functions of data sharding, distributed transaction and database orchestration, applicable in a variety of situations such as Java isomorphism, heterogeneous language and cloud native. Aiming at reasonably making full use of the computation and storage capacity of the database in a distributed system, ShardingSphere defines itself as a middleware, rather than a totally new type of database.*

***



### Project Introduction

​		*This project is about Car-Parts management  system produced by me, and is using by my friends. Before, I just use java swing, mysql to do it. Now, I got some inspiration from this course, and start to optimize this project with ShardingSphere middleware.*

1. Code structure:

   ```txt
   src/com.zyz.jdbc: database connection tools.
   src/com.zyz.swing: main panel code.
   src/com.zyz.util: some useful tools.
   src/libs: JAR Files
   src/otherfiles: goods info and Material coding
   ```

2. Main panel:

![image-20191107221644703](/Users/xingdong/Library/Application Support/typora-user-images/image-20191107221644703.png)



3. Order record panel:

![image-20191107221709179](/Users/xingdong/Library/Application Support/typora-user-images/image-20191107221709179.png)



***

### ShardingSphere In My Project

1. It's about Sharding-JDBC:

![img](https:////upload-images.jianshu.io/upload_images/4459384-31d452355a1cf4eb?imageMogr2/auto-orient/strip|imageView2/2/w/1080)

2. Code in my project:

```java
package com.zyz.util;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class DBUtil {

    private DBUtil(){};

    private static DBUtil instance = new DBUtil();

    private static Connection connection = null;

    public static Connection getConn() throws SQLException {

        Map<String, DataSource> dataSourceMap = new HashMap<>();

        // configure data source
        BasicDataSource dataSource1 = new BasicDataSource();

        dataSource1.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource1.setUrl("jdbc:mysql://localhost:3306/parts");
        dataSource1.setUsername("root");
        dataSource1.setPassword("20192019");
        dataSourceMap.put("wb", dataSource1);

        TableRuleConfiguration tableRuleConfiguration = new TableRuleConfiguration("goods_info");


        // goods_info table according to number to be divided
        tableRuleConfiguration.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("number",
                "database${number % 2}"));

        ShardingRuleConfiguration shardingRuleConfiguration = new ShardingRuleConfiguration();
        shardingRuleConfiguration.getTableRuleConfigs().add(tableRuleConfiguration);

        DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfiguration,
                new Properties());

        return connection = dataSource.getConnection();
    }

    public DBUtil getInstance(){
        return instance;
    }
}
```



***

### Ending

​		 *Sharding sphere is a very powerful distributed database middleware solution. There are easy to understand row expressions for configuring data nodes and data slicing algorithms. The official documents are complete, the case code project is complete, and the sub database and sub table can be completed in a short time.*

​        *This article briefly introduces the middleware. If you need to know more about it, please refer to the official address*

```htt
Website: https://shardingsphere.apache.org/document/current/cn/overview/
github:  https://github.com/apache/incubator-shardingsphere
```

***





**Thanks**

