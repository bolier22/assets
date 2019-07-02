package com.wanma.eichong.assets.mapper;

import com.wanma.eichong.assets.entity.ExecTask;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ExecTaskMapper {
    ExecTask selectByExecKey(String execKey);
}