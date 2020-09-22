package com.blues.shorturl.dao;

import com.blues.shorturl.entity.AccessControl;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 访问控制表(AccessControl)表数据库访问层
 *
 * @author makejava
 * @since 2020-09-22 14:52:34
 */
@Mapper
public interface AccessControlDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    AccessControl queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<AccessControl> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param accessControl 实例对象
     * @return 对象列表
     */
    List<AccessControl> queryAll(AccessControl accessControl);

    /**
     * 新增数据
     *
     * @param accessControl 实例对象
     * @return 影响行数
     */
    int insert(AccessControl accessControl);

    /**
     * 修改数据
     *
     * @param accessControl 实例对象
     * @return 影响行数
     */
    int update(AccessControl accessControl);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}