package com.chuangqi.mapper;

import com.chuangqi.entity.RotationChart;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

public interface RotationChartMapper {

    /**
     * 根据filename和pictureUrl和modelId来进行查询
     */
    @Select("select * from rotation_chart where  create_time = #{createTime} ")
    @ResultMap(value = "RotationChartResult")
    RotationChart getRotationChartByMore(@Param("createTime")String createTime);

    /**
     *查询所有轮播图
     */
    @Select("select * from rotation_chart")
    @Results(id = "RotationChartResult" , value = {
            @Result(property = "id" , column = "id" , id = true),
            @Result(property = "modelId" , column = "model_id"),
            @Result(property = "filename" ,column = "filename"),
            @Result(property = "pictureUrl" , column = "picture_url")
    })
    List<RotationChart> getAllRotationChart();

    /**
     * 新增首页轮播图
     */
    @Insert("insert into rotation_chart (model_id , filename , picture_url , create_time ) " +
            "values (#{modelId} , #{filename} , #{pictureUrl} , #{createTime})")
    Integer addRotationChart(@Param("modelId") Integer modelId , @Param("filename")String filename ,
                             @Param("pictureUrl")String pictureUrl , @Param("createTime") Date createTime);

    /**
     * 删除轮播图
     */
    @Delete("delete from rotation_chart where id = #{id}")
    Integer deleteRotationChart(@Param("id") Integer id);

    /**
     * 根据id查询
     */
    @Select("select * from rotation_chart where id = #{id}")
    @ResultMap(value = "RotationChartResult")
    RotationChart getRotationChart(@Param("id") Integer id);
}
