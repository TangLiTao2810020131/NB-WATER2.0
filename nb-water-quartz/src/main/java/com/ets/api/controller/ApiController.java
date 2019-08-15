package com.ets.api.controller;
 
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ets.business.schedule.service.ScheduleJobService;
 

/**
 * 
 * @ClassName:     ApiController.java 
 * @Description:   定时任务接口
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月24日 下午5:01:44
 */
@RestController
@RequestMapping("/api")
public class ApiController {
 
    @Resource
    private ScheduleJobService jobService;
     
    /**
     * 
    * @Title: changeJobStatus 
    * @Description: 启动或停驶定时任务
    * @param: @param jobId 任务ID
    * @param: @param cmd 任务类型（start：启动，stop：停止）
    * @return: boolean    
    * @Date: 2019年7月24日 下午5:46:28  
    * @throws
     */
    @PostMapping(value="/changeJob")
    public boolean changeJob(@RequestBody Map<String,Object> map){
        try{
        	String jobId = (String) map.get("jobid");
        	String cmd  = (String) map.get("type");
        	return jobService.changeJob(jobId,cmd);  
        }catch(Exception e){
            return false;
        }
    }
}