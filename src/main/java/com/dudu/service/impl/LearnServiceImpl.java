package com.dudu.service.impl;

import com.dudu.dao.LearnMapperWithXml;
import com.dudu.domain.LearnResource;
import com.dudu.service.LearnService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("learnService")
public class LearnServiceImpl implements LearnService {
    @Resource
    private LearnMapperWithXml learnMapperWithXml;

    @Override
    public int add(LearnResource learnResource) {
        //引用dao层LearnMapper类的实例的add方法
        return learnMapperWithXml.add(learnResource);
    }

    @Override
    public int update(LearnResource learnResource) {
        return learnMapperWithXml.update(learnResource);
    }

    @Override
    public int deleteByIds(String[] ids) {
        return learnMapperWithXml.deleteByIds(ids);
    }

    @Override
    public LearnResource queryLearnResourceById(Long id) {
        return learnMapperWithXml.queryLearnResourceById(id);
    }

    /**
     * @Author haien
     * @Description 动态查询
     * @Date 2018/11/3
     * @Param [params]
     * @return java.util.List<com.dudu.domain.LearnResource>
     **/
    @Override
    public List<LearnResource> queryLearnResourceList(Map<String, Object> params) {
        //物理分页:对mybatis流程进行增强，添加了limit以及count查询
        PageHelper.startPage(Integer.parseInt(params.get("pageNum").toString()), Integer.parseInt(params.get("rows").toString())); //page: 页码，rows：容量
        return learnMapperWithXml.queryLearnResourceList(params);
    }
}













