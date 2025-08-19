package com.lyh.aicodemother.service;

import com.lyh.aicodemother.model.Dto.user.AppQueryRequest;
import com.lyh.aicodemother.model.entity.App;
import com.lyh.aicodemother.model.vo.AppVO;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;

import java.util.List;

/**
 * 应用 服务层。
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 */
public interface AppService extends IService<App> {
   public AppVO getAppVO(App app);

   public QueryWrapper getQueryWrapper(AppQueryRequest appQueryRequest);

   public List<AppVO> getAppVoList(List<App> appList);
}
