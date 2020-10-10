package com.chuangqi.service.Impl;

import com.chuangqi.entity.Part;
import com.chuangqi.mapper.PartMapper;
import com.chuangqi.service.PartService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: xiyue
 * \* Date: 2020/2/6
 * \* Time: 11:18
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Service
public class PartServiceImpl implements PartService {

    @Resource
    private PartMapper partMapper;


    /**
     * 根据科室创建时间来进行查询
     */
    @Override
    public Part getPartByCreateTime(String createTime) {
        return partMapper.getPartByCreateTime(createTime);
    }

    /**
     * 查询所有科室
     */
    @Override
    public List<Part> getAllPart() {
        return partMapper.getAllPart();
    }

    /**
     * 根据id来查询科室
     */
    @Override
    public Part getPartById(Integer partId) {
        return partMapper.getPartById(partId);
    }

    /**
     * 新增科室
     */
    @Override
    public Boolean addPart(Part part) {
        Integer i = partMapper.addPart(part);
        if(i!=0){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 修改科室名称
     */
    @Override
    public Boolean updatePart(Part part) {
        Integer i = partMapper.updatePart(part);
        if(i!=0){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 删除科室
     */
    @Override
    public Boolean deletePart(Integer partId) {
        Integer i = partMapper.deletePart(partId);
        if(i!=0){
            return true;
        }else {
            return false;
        }
    }
}