package com.lei.importexcel.service;

import com.lei.importexcel.dao.ExcelJpa;
import com.lei.importexcel.entity.Catagory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author leiyunlong
 * @version 1.0
 * @date 2020/2/28 9:02
 */
@Service
public class ExcelService {
    @Autowired
    ExcelJpa excelJpa;

    /**
     * 导入的excel插入数据库
     */
    public Catagory CatagorySave(Catagory catagory) {
        Catagory catagory1 = excelJpa.save(catagory);
        return catagory1;
    }
    /**
     * 导入的excel插入数据库
     */
    public List<Catagory> CatagoryFindAll() {
        List<Catagory> all = excelJpa.findAll();
        return all;
    }
}
