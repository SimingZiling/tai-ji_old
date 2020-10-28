package org.shaoyin.database.orm;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对象关系映射
 */
public class ObjectRelationMapping {

    /**
     * 处理结果集，得到map的list，其中一个map对象对应的记录。
     * @param resultSet 结果集
     * @return Map列表
     */
    public static List<Map<String, Object>> handleResultSetToMapList(ResultSet resultSet) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        try {
            List<String> columnLabels = getColumnLabels(resultSet);
            while (resultSet.next()) {
                Map<String, Object> map = new HashMap<>();
                for (String columnLabel : columnLabels) {
                    Object value = resultSet.getObject(columnLabel);
                    map.put(columnLabel, value);
                }
                mapList.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mapList;
    }

    /**
     * 处理结果集，得到map对应的记录
     * @param resultSet 结果集
     * @return 集合
     */
    public static Map<String, Object> handleResultSetToMap(ResultSet resultSet) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<String> columnLabels = getColumnLabels(resultSet);
            resultSet.next();
            for (String columnLabel : columnLabels) {
                Object value = resultSet.getObject(columnLabel);
                map.put(columnLabel, value);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 获取结果集的ColumnCount 对应的list。
     * @param resultSet 结果集
     * @return ColumnCount列表
     */
    private static List<String> getColumnLabels(ResultSet resultSet) throws SQLException {
        List<String> labels = new ArrayList<>();
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
            labels.add(resultSetMetaData.getColumnLabel(i + 1));
        }
        return labels;
    }

}
