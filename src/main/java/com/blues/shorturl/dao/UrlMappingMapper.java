package com.blues.shorturl.dao;

import com.blues.shorturl.entity.UrlMapping;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UrlMappingMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UrlMapping record);

    int insertSelective(UrlMapping record);

    UrlMapping selectByPrimaryKey(Long id);

    UrlMapping selectByMd5(String md5);

    UrlMapping selectByKeyword(String keyword);

    List<UrlMapping> selectByCondition(UrlMapping record);

    List<UrlMapping> selectByBizType(List<String> types);

    int updateByPrimaryKeySelective(UrlMapping record);

    int updateByPrimaryKey(UrlMapping record);
}