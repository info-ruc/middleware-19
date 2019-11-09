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

//        try {
//
//            Class.forName("org.sqlite.JDBC");
//            connection = DriverManager.getConnection("jdbc:sqlite:parts.db");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }


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

        return dataSource.getConnection();
    }

    public DBUtil getInstance(){
        return instance;
    }
}
