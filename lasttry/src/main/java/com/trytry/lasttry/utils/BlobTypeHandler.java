package com.trytry.lasttry.utils;


import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


//用于处理Java中二进制到Bolb数据类型的转换
@MappedTypes(byte[].class)
@MappedJdbcTypes(JdbcType.BLOB)
public class BlobTypeHandler implements TypeHandler<byte[]> {
    @Override
    public void setParameter(PreparedStatement ps, int i, byte[] parameter, JdbcType jdbcType) throws SQLException {
        ps.setBytes(i, parameter);
    }

    @Override
    public byte[] getResult(ResultSet rs, String columnName) throws SQLException {
        return rs.getBytes(columnName);
    }

    @Override
    public byte[] getResult(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getBytes(columnIndex);
    }

    @Override
    public byte[] getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return cs.getBytes(columnIndex);
    }
}
