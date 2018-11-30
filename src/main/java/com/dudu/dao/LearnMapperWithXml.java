package com.dudu.dao;

import com.dudu.domain.LearnResource;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Author haien
 * @Description 用xml配置方式来实现dao层
 * @Date 2018/11/5
 **/
@Mapper //照样是要加mapper注解
public interface LearnMapperWithXml {
    public int add(LearnResource learnResource);
    public int update(LearnResource learnResource);
    public int deleteByIds(String[] ids);
    public LearnResource queryLearnResourceById(Long id);
    public List<LearnResource> queryLearnResourceList(Map<String,Object> params);
}
