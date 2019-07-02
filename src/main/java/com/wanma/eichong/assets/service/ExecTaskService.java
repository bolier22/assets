package com.wanma.eichong.assets.service;

import com.wanma.eichong.assets.entity.ExecTask;

public interface ExecTaskService {
    ExecTask selectByExecKey(String execKey);
}