package com.example.demo.service;

import com.example.demo.bean.Word;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("WordService")
public interface WordService {

    boolean saveWord(Word obj);

    List<Word> searchWord(String key);
}
