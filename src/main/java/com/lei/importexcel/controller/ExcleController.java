package com.lei.importexcel.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.fastjson.JSONObject;
import com.lei.importexcel.entity.Catagory;
import com.lei.importexcel.listener.ExcelListener;
import com.lei.importexcel.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * @author leiyunlong
 * @version 1.0
 * @date 2020/2/27 16:15
 */
@Controller
public class ExcleController {
    @Autowired
    ExcelService ExcelService;

    /**
     * 单sheet导入数据库
     *
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping("/importexcel")
    public String importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();

        //实例化实现了AnalysisEventListener接口的类
        ExcelListener listener = new ExcelListener();
        /*
         * 传入参数（旧）
         * ExcelReader excelReader = new ExcelReader(inputStream, ExcelTypeEnum.XLS, null, listener);
         */
        // 传入参数及读取信息
        EasyExcel.read(inputStream, Catagory.class, listener)
                .excelType(ExcelTypeEnum.XLS)
                .excelType(ExcelTypeEnum.XLSX)
                .headRowNumber(1)
                .sheet(0)
                .doRead();
        /*
         * 读取信息（旧）
         * excelReader.read(new Sheet(1, 3, Catagory.class));
         */
        //获取数据
        List<Object> list = listener.getDatas();

        //转换数据类型,并插入到数据库
        for (int i = 0; i < list.size(); i++) {
            Catagory catagory = (Catagory) list.get(i);
            ExcelService.CatagorySave(catagory);
            //catagoryMapper.insertCategory(catagory);
        }
        return "excel";
    }

    /**
     * 跳转
     *
     * @return
     */
    @RequestMapping("/")
    public String excel() {
        return "excel";
    }

    /**
     * 多sheet导入
     *
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping("/importexcelmoresheet")
    public String toEntity(MultipartFile file) throws IOException {
        Map<String, Object> result = new HashMap<>();
        ExcelListener listener = new ExcelListener();
        ExcelReaderBuilder builder = EasyExcel.read(file.getInputStream(), Catagory.class, listener)
                .excelType(ExcelTypeEnum.XLS)
                .excelType(ExcelTypeEnum.XLSX);
        ExcelReader reader = builder.build();
        //sheet集合
        List<ReadSheet> sheets = reader.excelExecutor().sheetList();
        for (ReadSheet sheet : sheets) {
            listener.getDatas().clear();
            System.out.println("sheet name:{}" + sheet.getSheetName());
            //读取每一个sheet的内容
            reader.read(sheet);
            List<Object> list = listener.getDatas();
            for (int i = 0; i < list.size(); i++) {
                Catagory catagory = (Catagory) list.get(i);
                ExcelService.CatagorySave(catagory);
                //catagoryMapper.insertCategory(catagory);
            }
            System.out.println("content:{}" + JSONObject.toJSONString(list));
        }
        reader.finish();
        result.put("list", listener.getDatas());
        return "excel";
    }

    /**
     * 导出EXCEL，从数据库
     *
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/exportexcle")
    public String exporExcel(HttpServletResponse response) throws IOException {
        //        ExcelWriter writer = null;
        OutputStream outputStream = response.getOutputStream();
        try {
            //添加响应头信息，后面修改文件名
            response.setHeader("Content-disposition", "attachment; filename=" + "abc.xls");
            //设置类型
            response.setContentType("application/msexcel;charset=UTF-8");
            //设置头
            response.setHeader("Pragma", "No-cache");
            //设置头
            response.setHeader("Cache-Control", "no-cache");
            //设置日期头
            response.setDateHeader("Expires", 0);

            //实例化 ExcelWriter
//            writer = new ExcelWriter(outputStream, ExcelTypeEnum.XLS, true);

            //获取数据
            List<Catagory> catagoryList = ExcelService.CatagoryFindAll();
            //要忽略的字段
            Set<String> excludeColumnFiledNames = new HashSet<String>();
            excludeColumnFiledNames.add("id");
            //实例化表单
//            Sheet sheet = new Sheet(1, 0, Catagory.class);
            EasyExcel.write(outputStream, Catagory.class)
                    //导出时添加想要忽略的字段
                    .excludeColumnFiledNames(excludeColumnFiledNames)
                    .excelType(ExcelTypeEnum.XLSX)
                    .sheet("目录")
                    .doWrite(catagoryList);
//            sheet.setSheetName("目录");

            //输出
//            writer.write(catagoryList, sheet);
//            writer.finish();
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            response.getOutputStream().close();
        }
        return "index";
    }
}
