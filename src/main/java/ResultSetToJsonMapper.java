package main.java;// convenient JDBC result set to JSON array mapper

import java.math.BigDecimal;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
//import org.springframework.jdbc.support.JdbcUtils;

public class ResultSetToJsonMapper
{
    public static JSONArray mapResultSet(ResultSet rs) throws SQLException
    {
        JSONArray jArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        if (rs.isBeforeFirst()) {
            rs.next();
        }
        do
        {
            for (int index = 1; index <= columnCount; index++)
            {
                String column = rsmd.getColumnName(index);
                Object value = rs.getObject(column);
                if (value == null)
                {
                    jsonObject.put(column, "");
                } else if (value instanceof Integer) {
                    jsonObject.put(column, (Integer) value);
                } else if (value instanceof String) {
                    jsonObject.put(column, (String) value);
                } else if (value instanceof Boolean) {
                    jsonObject.put(column, (Boolean) value);
                } else if (value instanceof Date) {
                    jsonObject.put(column, ((Date) value));
                } else if (value instanceof Long) {
                    jsonObject.put(column, (Long) value);
                } else if (value instanceof Double) {
                    jsonObject.put(column, (Double) value);
                } else if (value instanceof Float) {
                    jsonObject.put(column, (Float) value);
                } else if (value instanceof BigDecimal) {
                    jsonObject.put(column, (BigDecimal) value);
                } else if (value instanceof Byte) {
                    jsonObject.put(column, (Byte) value);
                } else if (value instanceof byte[]) {
                    jsonObject.put(column, (byte[]) value);
                } else {
                    throw new IllegalArgumentException("Unmappable object type: " + value.getClass());
                }
                jArray.add(jsonObject);
            }

        }while(rs.next());
        return jArray;
    }
}