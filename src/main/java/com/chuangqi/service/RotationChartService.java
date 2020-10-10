package com.chuangqi.service;

import com.chuangqi.entity.RotationChart;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

public interface RotationChartService {

    /**
     * 根据filename和pictureUrl和modelId来进行查询
     */
    RotationChart getRotationChartByMore( String createTime);

    /**
     *查询所有轮播图
     */
    List<RotationChart> getAllRotationChart();

    /**
     * 新增轮播图
     */
    Boolean addRotationChart(Integer modelId , String filename , String pictureUrl , Date createTime);

    /**
     * 删除轮播图
     */
    Boolean deleteRotationChart(Integer id);

    /**
     * 根据id查询
     */
    RotationChart getRotationChart( Integer id);
}
