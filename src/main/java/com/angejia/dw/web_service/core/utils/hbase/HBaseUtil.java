package com.angejia.dw.web_service.core.utils.hbase;

import java.io.IOException;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;


// Hbase 基础类
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.Scan;

/**
 * scan 比较运算符号
 * CompareFilter.CompareOp
 *  EQUAL 相等
    GREATER 大于
    GREATER_OR_EQUAL 大于等于
    LESS 小于
    LESS_OR_EQUAL 小于等于
    NOT_EQUAL 不等于
 */
import org.apache.hadoop.hbase.filter.CompareFilter;


/**
 * 比较器
 */
import org.apache.hadoop.hbase.filter.RegexStringComparator; // 匹配正则表达式
import org.apache.hadoop.hbase.filter.SubstringComparator;    // 匹配子字符串
import org.apache.hadoop.hbase.filter.BinaryPrefixComparator;    //匹配前缀字节数据
import org.apache.hadoop.hbase.filter.BinaryComparator;      // 匹配完整字节数组


/**
 * 过滤器 
 */
import org.apache.hadoop.hbase.filter.FilterList;    // 多个过滤器组合 
    //FilterList.Operator.MUST_PASS_ALL(必须通过所有) 
    //FilterList.Operator.MUST_PASS_ONE(必须通过一个)
import org.apache.hadoop.hbase.filter.RowFilter;    // row key 过滤器
import org.apache.hadoop.hbase.filter .PrefixFilter;    // row key 前缀过滤器
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter; // 列值过滤器
import org.apache.hadoop.hbase.filter.ColumnPrefixFilter;  // 列过滤器
import org.apache.hadoop.hbase.filter.ValueFilter;    // 值


/**
 * HBase 连接工具类
 * @author Jason
 *
 */
public class HBaseUtil {

    private static final Logger logger = Logger.getLogger(HBaseUtil.class);

    public static final String CLIENT_PORT = "2181";//端口
    
    // zookeeper 地址
    public String zookeepers;

    // 配置
    public Configuration configuration;

    // 连接
    public Connection connection;

    // table 连接池
    public HTablePool tablePool;

    // 连接池大小
    private static final int poolsize = 100;


    public String getZookeepers() {
        return zookeepers;
    }
    public void setZookeepers(String zookeepers) {
        this.zookeepers = zookeepers;
    }


    public Configuration getConfiguration() {
        if (configuration == null) {
            Configuration conf = HBaseConfiguration.create();  
            //conf.set("hbase.zookeeper.quorum", this.getZookeepers());
            conf.set("hbase.zookeeper.quorum", this.getZookeepers());   
            conf.set("hbase.zookeeper.property.clientPort", CLIENT_PORT);
            configuration = conf;
        }
        return configuration;
    }
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

 
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    public Connection getConnection() {
        if (connection == null) {
            try {
                connection = ConnectionFactory.createConnection(this.getConfiguration());
            } catch (IOException e1) {
                logger.debug("HBase 连接错误");
                e1.printStackTrace();
            }
        }
        return connection;
    }


    public HTablePool getTablePool() {
        if (tablePool == null) {
            tablePool = new HTablePool(this.getConfiguration(), poolsize);
        }
        return tablePool;
    }
    public void setTablePool(HTablePool tablePool) {
        this.tablePool = tablePool;
    }


    // list Table
    public void listTable() {
        try {
            System.out.println(this.getConnection().getAdmin().listTables());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 根据 row key 获取数据(完全匹配): 单条数据查询
     */
    public Result findByKey(String tableName, String rowKey) {

        // 获取表连接池
        HTableInterface table = this.getTablePool().getTable(tableName);

        // 查询条件
        Get get = new Get(Bytes.toBytes(rowKey));

        Result result = null;
        try {
            result = table.get(get);
            table.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }


    /**
     * 根据 row-key 获取指定列族下的列数据: 单条数据查询
     * @param tableName     table name
     * @param rowKey        row-key
     * @param columnFamily  列族
     * @param columnNames   列
     * @return Map<String,String>
     */
    public Map<String,String> select(String tableName, String rowKey, String columnFamily, List<String> columnNames ) {

        Map<String, String> result = new HashMap<String, String>();

        // 通过 rowkey 查找数据
        Result rowData = this.findByKey(tableName, rowKey);

        if(rowData != null && !rowData.isEmpty()){
            for ( String curColumnName : columnNames ) {
                byte[] columnNameValue = rowData.getValue(Bytes.toBytes(columnFamily), Bytes.toBytes(curColumnName));
                String columnNameValueToString = Bytes.toString(columnNameValue) ;
                //System.out.println(columnNameValueToString);
                if (columnNameValueToString != null) result.put(curColumnName, columnNameValueToString );
            }
        }

        return result;
    }
    




}
