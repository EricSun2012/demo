package com.example.demo.framework;


/**
 * 通用泛型DAO基类
 *
 * @param <T>
 * @author g
 */
public interface BaseDao<T> {
    int deleteByPrimaryKey(Integer id);

    int insert(T record);

    int insertSelective(T record);

    T selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKey(T record);
}
