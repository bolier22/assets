package com.wanma.eichong.assets;

import com.wanma.eichong.assets.entity.ExecTask;
import com.wanma.eichong.assets.entity.TblAreaCode;
import com.wanma.eichong.assets.mapper.ExecTaskMapper;
import com.wanma.eichong.assets.service.AreaCodeService;
import com.wanma.eichong.assets.service.ExecTaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AssetsApplicationTests {

    @Autowired
    ExecTaskService execTaskService;

    @Test
    public void contextLoads() {
//        ExecTask execTask = execTaskService.selectByExecKey("execkey");
//        execTask.getExecUrl();
    }

    /**
    把 mybatis 输出的sql日志还原成完整的sql语句。 将日志输出的sql语句中的问号 ? 替换成真正的参数值。 通过 "Tools -> MyBatis Log Plugin" 菜单
    或快捷键 "Ctrl+Shift+Alt+O" 启用。
    点击窗口左边的 "Filter" 按钮，可以过滤不想要输出的sql语句。
    点击窗口左边的 "Format Sql" 按钮，可以格式化输出的sql语句。
    选中console的sql日志，右击 "Restore Sql from Selection" 菜单可以还原sql语句。
    前提条件：输出的sql日志必须包含"Preparing:"和"Parameters:"才能正常解析。
    **/

    @Autowired
    AreaCodeService areaCodeService;
    @Test
    public void contextLoads2() {
//        List<TblAreaCode> findProvince = areaCodeService.findProvince();
//        for(TblAreaCode a:findProvince){
//            System.out.println(a.getAreaFullName());
//        }

//        for(int i=0;i<10;i++){
//            System.out.println("---1111---"+i);
//            for(int k=0;k<4;k++){
//                if(k == 2){
//                    break;
//                }
//                System.out.println("----3333----"+k);
//            }
//            System.out.println("---2222---"+i);
//            System.out.println("---4444---"+i);
//            System.out.println("---5555---"+i);
//            System.out.println("---6666---"+i);
//            System.out.println("---7777---"+i);
//            System.out.println("---8888---"+i);
//        }
    }
}
