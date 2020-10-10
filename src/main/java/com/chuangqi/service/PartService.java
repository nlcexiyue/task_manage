package com.chuangqi.service;

import com.chuangqi.entity.Part;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PartService {


    /**
     * 根据科室创建时间来进行查询
     */
    Part getPartByCreateTime(String createTime);

    /**
     * 查询所有科室
     */
    List<Part> getAllPart();

    /**
     * 根据id来查询科室
     */
    Part getPartById( Integer partId);

    /**
     * 新增科室
     */
    Boolean addPart(Part part);

    /**
     * 修改科室名称
     */
    Boolean updatePart(Part part);

    /**
     * 删除科室
     */
    Boolean deletePart( Integer partId);
}
