package com.lc.demo.service;

import com.lc.demo.bean.Classes;

import java.util.List;

/**
 * @ClassName ClassService
 * @Deacription TODO
 **/
public interface ClassService {

    List<Classes> getAllClass();

    Classes selectById(String classId);

    Classes selectByName(String className);

    int deleteById(String classId);

    int addClass(Classes classes);


}
