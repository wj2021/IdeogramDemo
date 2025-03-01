package com.example.ideogram.dao.mapper;

import com.example.ideogram.dao.model.IdeoPic;
import com.example.ideogram.dao.model.IdeoPicExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IdeoPicMapper {
    long countByExample(IdeoPicExample example);

    int deleteByExample(IdeoPicExample example);

    int insert(IdeoPic row);

    int insertSelective(IdeoPic row);

    List<IdeoPic> selectByExampleWithBLOBs(IdeoPicExample example);

    List<IdeoPic> selectByExample(IdeoPicExample example);

    int updateByExampleSelective(@Param("row") IdeoPic row, @Param("example") IdeoPicExample example);

    int updateByExampleWithBLOBs(@Param("row") IdeoPic row, @Param("example") IdeoPicExample example);

    int updateByExample(@Param("row") IdeoPic row, @Param("example") IdeoPicExample example);
}