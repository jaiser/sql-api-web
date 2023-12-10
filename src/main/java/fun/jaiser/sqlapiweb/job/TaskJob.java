package fun.jaiser.sqlapiweb.job;

import fun.jaiser.sqlapiweb.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @DESCRIPTION: 定时任务
 * @AUTHOR 许家军
 * @DATE: 2023/12/10
 */
@Component
@Slf4j
public class TaskJob {

    @Autowired
    private CommonService commonService;


    @Scheduled(cron = "${job.update-data-source}")
    private void updDataSourceInfo() {
        log.info("===============定时任务开始->更新数据源===============");
        long startTime = System.currentTimeMillis();
        commonService.updateDataSource();
        long endTime = System.currentTimeMillis();
        log.info("===============定时任务结束->更新数据源->耗时《{}》ms===============", endTime - startTime);

    }
}