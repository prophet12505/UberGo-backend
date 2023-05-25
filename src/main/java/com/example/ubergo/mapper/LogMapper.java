package com.example.ubergo.mapper;


import com.example.ubergo.entity.Log;
import com.example.ubergo.entity.Person;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Component
@Mapper
public interface LogMapper {
    @Insert("INSERT INTO log(source_module,level,log_content) VALUES(#{sourceModule},#{level},#{logContent})")
    public int createALog(Log log);


}