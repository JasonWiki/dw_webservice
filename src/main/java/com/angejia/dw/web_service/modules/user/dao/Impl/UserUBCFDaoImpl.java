package com.angejia.dw.web_service.modules.user.dao.Impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;

import com.angejia.dw.web_service.core.base.dao.DataSourceSessionFactory;
import com.angejia.dw.web_service.modules.user.dao.UserUBCFDao;

@Repository("userUBCFDao")
public class UserUBCFDaoImpl implements UserUBCFDao  {

    @Autowired
    private DataSourceSessionFactory dataSourceDao;


    /**
     * 获取 CBCF 算法推荐数据
     */
    public List<Map<String, String>> getUserCBCFRecommendInventorys(String userId, String cityId, Integer offset, Integer limit) {
        // 保存推荐数据
        final List<Map<String, String>> rsResult = new ArrayList<Map<String, String>>();

        String ubcfSql = "SELECT recommend_item_id,relation_user_pf,recommend_item_pf,inventory_rs_status "
                + "FROM da_db.da_user_inventory_recommend_ubcf "
                + "WHERE user_id="+ userId 
                 + " AND user_city_id=" + cityId 
                + " ORDER BY relation_user_pf DESC "
                + "LIMIT " + offset + "," + limit;
        //System.out.println(ubcfSql);

        dataSourceDao.getProductDataJdbc().query(ubcfSql, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {

                Map<String, String> recMap = new HashMap<String, String>();
                recMap.put("inventory_rs_id", rs.getString("recommend_item_id"));
                recMap.put("relation_user_pf", rs.getString("relation_user_pf"));
                recMap.put("inventory_rs_pf", rs.getString("recommend_item_pf"));
                recMap.put("inventory_rs_status", rs.getString("inventory_rs_status"));
                recMap.put("type", "2");
                rsResult.add(recMap);
            }
        });

        return rsResult;
    }
}
