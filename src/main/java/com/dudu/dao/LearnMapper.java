package com.dudu.dao;

import com.dudu.domain.LearnResource;
import com.dudu.service.LearnService;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Component //不加的话service那边引入LearnMappers时会警告
@Mapper
public interface LearnMapper {

    //简单的可以直接用注解完成
    @Insert("insert into learn_resource values(#{id},#{author},#{title},#{url})")
    public int add(LearnResource learnResource);

    @Update("update learn_resource set author=#{author},title=#{title},url=#{url} where id=#{id}")
    public int update(LearnResource learnResource);

    //复杂的需要创建动态sql
    @DeleteProvider(type=LearnSqlBuilder.class,method="deleteByIds")
    public int deleteByIds(@Param("ids")String[] ids);

    @Select("select * from learn_resource where id=#{id}")
    @Results(
            {
                    @Result(id=true,column="id",property = "id"),
                    @Result(column="author",property = "author"),
                    @Result(column="title",property = "title"),
            }
    )
    public LearnResource queryLearnResourceById(@Param("id") Long id);

    /**
     * @Author haien
     * @Description 动态查询
     * @Date 2018/11/3
     * @Param [params]
     * @return java.util.List<com.dudu.domain.LearnResource>
     **/
    @SelectProvider(type=LearnSqlBuilder.class,method="queryLearnResourceByParams")
    public List<LearnResource> queryLearnResourceList(Map<String,Object> params);

    /**
     * @Author haien
     * @Description 动态sql的创建类
     * @Date 2018/11/3
     **/
    class LearnSqlBuilder{

        /**
         * @Author haien
         * @Description 动态查询
         * @Date 2018/11/2
         * @Param [params]
         * @return java.lang.String
         **/
        public String queryLearnResourceByParams(final Map<String,Object> params){
            //方式一、使用java1234的做法
             SQL sql= new SQL(){
                {
                    SELECT("*");
                    FROM("learn_resource");
                    StringBuffer sb=new StringBuffer();
                    if(params.get("author")!=null){
                        sb.append(" and author like '%"+params.get("author") +"%'");
                    }
                    if(params.get("title")!=null){
                        sb.append(" and title="+params.get("title"));
                    }
                    if(!sb.toString().equals("")){
                        WHERE(sb.toString().replaceFirst("and",""));
                    }
                }
            };
            System.out.println(sql.toString());
            return sql.toString();

            //方式二、用嘟嘟博客的做法
            /*
            StringBuffer sql=new StringBuffer();
            sql.append("selelct * from learn_resource where 1=1");
            if(params.get("author")!=null){
                sql.append(" and author like '%").append(params.get("author")).append("%'");
            }
            if(params.get("title")!=null){
                sql.append(" and title like '%").append(params.get("title")).append("%'");
            }
            System.out.println("查询sql=="+sql.toString());
            return sql.toString();
            */
        }

        /**
         * @Author haien
         * @Description 批量删除
         * @Date 2018/11/2
         * @Param [ids]
         * @return java.lang.String
         **/
        public String deleteByIds(@Param("ids")final String[] ids){
            StringBuffer sql=new StringBuffer();
            sql.append("delete from learn_resource where id in(");
            for(int i=0;i<ids.length;i++){
                if(i==ids.length-1){
                    sql.append(ids[i]); //最后一个则不用再跟个逗号
                }else{
                    sql.append(ids[i]).append(",");
                }
            }
            sql.append(")");
            return sql.toString();
        }
    }
}






















