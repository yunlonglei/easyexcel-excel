package com.lei.importexcel.dao;

import com.lei.importexcel.entity.Catagory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author leiyunlong
 * @version 1.0
 * @date 2020/2/28 9:06
 */
public interface ExcelJpa extends JpaRepository<Catagory, String> {
}
