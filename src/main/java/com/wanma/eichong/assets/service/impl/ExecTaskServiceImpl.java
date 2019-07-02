package com.wanma.eichong.assets.service.impl;

import com.wanma.eichong.assets.entity.ExecTask;
import com.wanma.eichong.assets.mapper.ExecTaskMapper;
import com.wanma.eichong.assets.service.ExecTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExecTaskServiceImpl implements ExecTaskService {

    @Autowired
    ExecTaskMapper execTaskMapper;

    @Override
    public ExecTask selectByExecKey(String execKey) {
        return execTaskMapper.selectByExecKey(execKey);
    }

}