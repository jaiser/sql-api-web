package fun.jaiser.sqlapiweb.controller;

import fun.jaiser.sqlapiweb.domain.QryPageVo;
import fun.jaiser.sqlapiweb.domain.RespResult;
import fun.jaiser.sqlapiweb.evt.CommonEvt;
import fun.jaiser.sqlapiweb.evt.QryPageEvt;
import fun.jaiser.sqlapiweb.service.CommonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @DESCRIPTION: 通用类
 * @AUTHOR 许家军
 * @DATE: 2023/12/7
 */
@Api("通用接口类型")
@Slf4j
@RestController
@RequestMapping("/api/common")
public class CommonController {

    @Autowired
    private CommonService commonService;

    @ApiOperation("查询数据列表")
    @PostMapping("/query/{code}/{pageNum}/{pageSize}")
    public RespResult query(@PathVariable("code") String code,
                                                                  @PathVariable("pageNum") int pageNum,
                                                                  @PathVariable("pageSize") int pageSize,
                                                  @RequestBody CommonEvt<Map<String, Object>> evt) {
        if (!StringUtils.hasText(code)) {
            return RespResult.getInstance().error("编码不能为空！");
        }
        QryPageEvt qryPageEvt = new QryPageEvt();
        qryPageEvt.setParam(evt.getParam());
        qryPageEvt.setPageNum(pageNum);
        qryPageEvt.setPageSize(pageSize);
        qryPageEvt.setCountTotal(true);
        try {
            if (pageNum < 1 || pageSize < 1) {
                qryPageEvt.setCountTotal(false);
            }
            log.info("【查询】数据《开始》,code值为:《{}》入参为:《{}》", code, evt);
            long startTime = System.currentTimeMillis();
            QryPageVo qryPageVo = commonService.query(code, qryPageEvt);
            long endTime = System.currentTimeMillis();
            log.info("【查询】数据《成功》,查询code为:《{}》,耗时为:《{}》ms", code, (endTime - startTime));
            return RespResult.getInstance().success(qryPageVo, "查询成功");
        }catch (Exception e) {
            log.error("【查询】数据《失败》,失败原因:《{}》", e.getMessage(), e);
            return RespResult.getInstance().error("查询数据《失败》,失败原因:" + e.getMessage());
        }
    }

    @ApiOperation("新增数据")
    @PostMapping("/insert/{code}")
    public RespResult insert(@PathVariable("code") String code, @RequestBody CommonEvt<List<Map<String, Object>>> evt) {
        if (!StringUtils.hasText(code)) {
            return RespResult.getInstance().error("编码不能为空！");
        }
        try{
            log.info("【新增】数据《开始》,code值为:《{}》入参为:《{}》", code, evt);
            long startTime = System.currentTimeMillis();
            boolean res = commonService.insert(code, evt);
            long endTime = System.currentTimeMillis();
            log.info("【新增】数据《成功》,查询code为:《{}》,耗时为:《{}》ms", code, (endTime - startTime));
            return RespResult.getInstance().success(res, "新增数据成功");
        }catch (Exception e) {
            log.error("【新增】数据《失败》,失败原因:《{}》", e.getMessage(), e);
            return RespResult.getInstance().error("新增数据《失败》,失败原因:" + e.getMessage());
        }
    }

    @ApiOperation("修改数据")
    @PostMapping("/update/{code}")
    public RespResult update(@PathVariable("code") String code, @RequestBody CommonEvt<Map<String, Object>> evt) {
        if (!StringUtils.hasText(code)) {
            return RespResult.getInstance().error("编码不能为空！");
        }
        try{
            log.info("【修改】数据《开始》,code值为:《{}》入参为:《{}》", code, evt);
            long startTime = System.currentTimeMillis();
            boolean res = commonService.update(code, evt);
            long endTime = System.currentTimeMillis();
            log.info("【修改】数据《成功》,查询code为:《{}》,耗时为:《{}》ms", code, (endTime - startTime));
            return RespResult.getInstance().success(res, "修改数据成功");
        }catch (Exception e) {
            log.error("【修改】数据《失败》,失败原因:《{}》", e.getMessage(), e);
            return RespResult.getInstance().error("修改数据《失败》,失败原因:" + e.getMessage());
        }
    }

    @ApiOperation("删除数据")
    @PostMapping("/delete/{code}")
    public RespResult delete(@PathVariable("code") String code, @RequestBody CommonEvt<Map<String, Object>> evt) {
        if (!StringUtils.hasText(code)) {
            return RespResult.getInstance().error("编码不能为空！");
        }
        try{
            log.info("【删除】数据《开始》,code值为:《{}》入参为:《{}》", code, evt);
            long startTime = System.currentTimeMillis();
            boolean res = commonService.delete(code, evt);
            long endTime = System.currentTimeMillis();
            log.info("【删除】数据《成功》,查询code为:《{}》,耗时为:《{}》ms", code, (endTime - startTime));
            return RespResult.getInstance().success(res, "删除数据成功");
        }catch (Exception e) {
            log.error("【删除】数据《失败》,失败原因:《{}》", e.getMessage(), e);
            return RespResult.getInstance().error("删除数据《失败》,失败原因:");
        }
    }
}