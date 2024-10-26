package org.example.springboot.Config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StringListTypeHandler extends BaseTypeHandler<List<String>> {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType) throws SQLException {
      try {
          ps.setString(i,objectMapper.writeValueAsString(parameter));
      } catch (JsonProcessingException e) {
          throw new RuntimeException(e);
      }
  }

  @Override
  public List<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
    String json = rs.getString(columnName);
    return convertJsonToList(json);
  }

  @Override
  public List<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    String json = rs.getString(columnIndex);
    return convertJsonToList(json);
  }

  @Override
  public List<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    String json = cs.getString(columnIndex);
    return convertJsonToList(json);
  }
  public List<String> convertJsonToList(String json){
    try {
      return json ==null ? null : objectMapper.readValue(json, new TypeReference<List<String>>() {});
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
