package com.moloom.img.api.config.DaoTypeHandler;

import com.moloom.img.api.entity.ImgCategory;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.springframework.context.annotation.Configuration;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author: moloom
 * @date: 2024-11-04 03:05
 * @description: 对枚举类型 imgCategory 参数进行转换。要添加 @Configuration ，否则在 insert 语句中，它找不到这个类!
 */

@Configuration
public class ImgCategoryTypeHandler extends BaseTypeHandler<ImgCategory> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, ImgCategory parameter, JdbcType jdbcType) throws SQLException {
        ps.setByte(i, parameter.getValue());
    }

    @Override
    public ImgCategory getNullableResult(ResultSet rs, String columnName) throws SQLException {
        System.out.println("\n\n" + rs.getByte(columnName) + "\n\n");
        byte value = rs.getByte(columnName);
        return ImgCategory.matchFromValue(value);
    }

    @Override
    public ImgCategory getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        byte value = rs.getByte(columnIndex);
        return ImgCategory.matchFromValue(value);
    }

    @Override
    public ImgCategory getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        byte value = cs.getByte(columnIndex);
        return ImgCategory.matchFromValue(value);
    }
}
