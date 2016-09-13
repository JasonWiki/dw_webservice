package com.angejia.dw.web_service.core.utils.hbase;


// Hbase 基础类
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
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

public class HbaseUtil {

    // 配置文件
    public HBaseConfiguration conf;

    // 连接工厂
    public ConnectionFactory connectionFactory;

    // admin 
    public Admin admin;

    // table
    public HTable table;


 // list Table
    public void listTable() throws IOException {
        admin.listTables();
    }

}
