package com.example.demo.dao;

import com.example.demo.bean.Word;
import com.example.demo.framework.BaseDao;

import java.util.List;

public interface WordDao extends BaseDao<Word> {

    List<Word> searchWordByKey(String key);
}
