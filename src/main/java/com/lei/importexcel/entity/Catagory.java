package com.lei.importexcel.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author leiyunlong
 * @version 1.0
 * @date 2020/2/27 16:14
 */

@Data
@Entity
@Table(name = "CATAGORY")
@Component
@EntityListeners(AuditingEntityListener.class)
public class Catagory implements Serializable {

    private static final long serialVersionUID = -3145519548971854247L;
    @Id
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @GeneratedValue(generator = "jpa-uuid")
    @ExcelIgnore
    private String id;

    @ExcelProperty(value = {"用户信息","姓名"}, index = 0)
    private String name;

    @ExcelProperty(value = {"用户信息","性别"}, index = 1)
    private int sex;

    @ExcelProperty(value ={"用户信息","年龄"}, index = 2)
    private int age;

    @ExcelProperty(value = {"用户信息","身份证"}, index = 3)
    private String idcard;
}
