package com.dudu.controller;

import com.alibaba.fastjson.JSONObject;
import com.dudu.domain.LearnResource;
import com.dudu.service.LearnService;
import com.dudu.tools.ServletUtil;
import com.github.pagehelper.PageInfo;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/learn")
public class LearnController {

    @Resource
    private LearnService learnService;
    private static final Logger logger=LoggerFactory.getLogger(LearnController.class);

    @RequestMapping("")
    public String learn(){
        return "learn-resource";
    }

    @RequestMapping(value="/queryLearnList",method=RequestMethod.GET,produces = "application/json;charset=utf-8") //produces:指定返回值类型和字符集，不过使用了@ResponseBody的话就不用指定返回值了
    public void queryLearnList(HttpServletRequest request, HttpServletResponse response) throws JSONException, IOException {
        //取得当前页码
        String pageNum=request.getParameter("pageNum");
        //取得每页行数
        String rows=request.getParameter("rows");
        String author=request.getParameter("author");
        String title=request.getParameter("title");
        Map<String,Object> params=new HashMap<String, Object>();
        params.put("pageNum",pageNum);
        params.put("rows",rows);
        params.put("author",author);
        params.put("title",title);

        List<LearnResource> learnResourceList=learnService.queryLearnResourceList(params);
        PageInfo<LearnResource> pageInfo=new PageInfo<LearnResource>(learnResourceList);
        JSONObject jo=new JSONObject();
        jo.put("rows",pageInfo); //LearnResource实体类集合
        jo.put("total",pageInfo.getPages()); //总页数
        jo.put("records",pageInfo.getTotal()); //总记录数
        ServletUtil.createSuccessResponse(200,jo,response); //jo的位置定义的是Object类
    }

    /**
     * 新添教程
     * @param request
     * @param response
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public void addLearn(HttpServletRequest request , HttpServletResponse response) throws JSONException {
        JSONObject result=new JSONObject();
        String author = request.getParameter("author");
        String title = request.getParameter("title");
        String url = request.getParameter("url");
        if(null==author){
            result.put("message","作者不能为空!");
            result.put("flag",false);
            ServletUtil.createSuccessResponse(200, result, response);
            //return result.toString();
        }
        if(null==title){
            result.put("message","教程名称不能为空!");
            result.put("flag",false);
            ServletUtil.createSuccessResponse(200, result, response);
            //return result.toString();
        }
        if(null==url){
            result.put("message","地址不能为空!");
            result.put("flag",false);
            ServletUtil.createSuccessResponse(200, result, response);
            //return result.toString();
        }
        LearnResource learnResouce = new LearnResource();
        learnResouce.setAuthor(author);
        learnResouce.setTitle(title);
        learnResouce.setUrl(url);
        int index=learnService.add(learnResouce);
        if(index>0){
            result.put("message","教程信息添加成功!");
            result.put("flag",true);
        }else{
            result.put("message","教程信息添加失败!");
            result.put("flag",false);
        }
        ServletUtil.createSuccessResponse(200, result, response);
        //return result.toString();
    }
    /**
     * 修改教程
     * @param request
     * @param response
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public void updateLearn(HttpServletRequest request , HttpServletResponse response) throws JSONException {
        JSONObject result=new JSONObject();
        String id = request.getParameter("id");
        LearnResource learnResouce=learnService.queryLearnResourceById(Long.valueOf(id));
        String author = request.getParameter("author");
        String title = request.getParameter("title");
        String url = request.getParameter("url");
        if(null==author){
            result.put("message","作者不能为空!");
            result.put("flag",false);
            ServletUtil.createSuccessResponse(200, result, response);
            //return result.toString();
        }
        if(null==title){
            result.put("message","教程名称不能为空!");
            result.put("flag",false);
            ServletUtil.createSuccessResponse(200, result, response);
        }
        if(null==url){
            result.put("message","地址不能为空!");
            result.put("flag",false);
            ServletUtil.createSuccessResponse(200, result, response);
        }
        learnResouce.setAuthor(author);
        learnResouce.setTitle(title);
        learnResouce.setUrl(url);
        int index=learnService.update(learnResouce);
        System.out.println("修改结果="+index);
        if(index>0){
            result.put("message","教程信息修改成功!");
            result.put("flag",true);
        }else{
            result.put("message","教程信息修改失败!");
            result.put("flag",false);
        }
        ServletUtil.createSuccessResponse(200, result, response);
    }
    /**
     * 删除教程
     * @param request
     * @param response
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    public void deleteUser(HttpServletRequest request ,HttpServletResponse response) throws JSONException {
        String ids = request.getParameter("ids");
        System.out.println("ids==="+ids);
        JSONObject result = new JSONObject();
        //删除操作
        int index = learnService.deleteByIds(ids.split(","));
        if(index>0){
            result.put("message","教程信息删除成功!");
            result.put("flag",true);
        }else{
            result.put("message","教程信息删除失败!");
            result.put("flag",false);
        }
        ServletUtil.createSuccessResponse(200, result, response);
    }
}
























