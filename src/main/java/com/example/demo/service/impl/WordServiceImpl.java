package com.example.demo.service.impl;

import com.example.demo.bean.Word;
import com.example.demo.dao.WordDao;
import com.example.demo.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("wordService")
public class WordServiceImpl implements WordService {

    @Autowired
    private WordDao wordDao;

    @Override
    public boolean saveWord(Word obj) {
        boolean result = false;
        try {
            wordDao.insert(obj);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Word> searchWord(String key) {
        return wordDao.searchWordByKey(key);
    }
}
