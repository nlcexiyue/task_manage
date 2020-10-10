package com.chuangqi.service.Impl;

import com.chuangqi.entity.RotationChart;
import com.chuangqi.mapper.RotationChartMapper;
import com.chuangqi.service.RotationChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: xiyue
 * \* Date: 2020/2/3
 * \* Time: 10:01
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Service
public class RotationChartServiceImpl implements RotationChartService {

    @Resource
    private RotationChartMapper rotationChartMapper;

    /**
     * 根据filename和pictureUrl和modelId来进行查询
     */
    @Override
    public RotationChart getRotationChartByMore(String createTime) {
        return  rotationChartMapper.getRotationChartByMore(createTime);
    }

    /**
     *查询所有轮播图
     */
    @Override
    public List<RotationChart> getAllRotationChart() {
        return rotationChartMapper.getAllRotationChart();
    }

    /**
     * 新增轮播图
     */
    @Override
    public Boolean addRotationChart(Integer modelId , String filename, String pictureUrl , Date createTime) {
        Integer i = rotationChartMapper.addRotationChart(modelId , filename, pictureUrl,createTime);
        if(i!=0){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 删除轮播图
     */
    @Override
    public Boolean deleteRotationChart(Integer id) {
        Integer i = rotationChartMapper.deleteRotationChart(id);
        if(i!=0){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 根据id查询
     */
    @Override
    public RotationChart getRotationChart(Integer id) {
        return rotationChartMapper.getRotationChart(id);
    }
}