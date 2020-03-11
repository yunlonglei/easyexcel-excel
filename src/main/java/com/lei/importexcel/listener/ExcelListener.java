package com.lei.importexcel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author leiyunlong
 * @version 1.0
 * @date 2020/2/27 16:13
 */
@Data
public class ExcelListener extends AnalysisEventListener {
    /**
     * 可以通过实例获取该值
     */
    private List<Object> datas = new ArrayList<>();
    @Override
    public void invoke(Object object, AnalysisContext analysisContext) {
        //数据存储到list，供批量处理，或后续自己业务逻辑处理。
        datas.add(object);
        //根据自己业务做处理
//        doSomething(object);
//        System.err.println("Row:" + analysisContext.getCurrentRowNum() + " Data:" + object);
    }

    private void doSomething(Object object) {
        //1、入库调用接口
        System.out.println("》》》》》》》》监听中间");
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // datas.clear();//解析结束销毁不用的资源
//        System.err.println("doAfterAllAnalysed...");
    }
}
