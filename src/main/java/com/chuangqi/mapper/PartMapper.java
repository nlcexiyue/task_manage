package com.chuangqi.mapper;

import com.chuangqi.entity.Part;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface PartMapper {


    /**
     * 根据科室创建时间来进行查询
     */
    @Select("select * from part where  create_time = #{createTime}")
    Part getPartByCreateTime(@Param("createTime") String createTime);


    /**
     * 查询所有科室名称
     */
    @Select("select * from part")
    @Results(id = "partResult" , value = {
            @Result(property = "partId" , column = "part_id" , id = true),
            @Result(property = "partName" , column = "part_name"),
            @Result(property = "article" , column = "article"),
            @Result(property = "pictureUrl" , column = "picture_url"),
            @Result(property = "createTime" , column = "create_time")
    })
    List<Part> getAllPart();

    /**
     * 根据id来查询科室
     */
    @Select("select * from part where part_id = #{partId}")
    @ResultMap(value = "partResult")
    Part getPartById(@Param("partId") Integer partId);

    /**
     * 新增科室
     */
    @Insert("insert into part (part_name , article , picture_url ,create_time) values (#{partName} , #{article} , #{pictureUrl} , #{createTime})")
    Integer addPart(Part part);

    /**
     * 修改科室名称和科室介绍
     */
    @Update("update part set part_name = #{partName} , article =  #{article} , picture_url = #{pictureUrl} where part_id = #{partId}")
    Integer updatePart(Part part);

    /**
     * 删除科室
     */
    @Delete("delete from part where part_id = #{partId} ")
    Integer deletePart(@Param("partId") Integer partId);
}
