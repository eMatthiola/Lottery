package cn.itedus.lottery.infrastructure.dao;

import cn.itedus.lottery.infrastructure.po.UserTakeActivityCount;
import middleware.db.router.annotation.DBRouter;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName IUserTakeActivityCountDao
 * @Description Recording of 用户活动参与次数表Dao
 * @Author Matthiola
 * @Date 2023/9/30 15:21
 */
@Mapper
public interface IUserTakeActivityCountDao {


    /**
     * 新增数据
     * @param userTakeActivityCountRequest 用户活动参与次数
     */
    @DBRouter
    UserTakeActivityCount queryUserTakeActivityCount(UserTakeActivityCount userTakeActivityCountRequest);

    /**
     * 更新领取次数信息
     * @param userTakeActivityCount 请求入参
     * @return 更新数量
     */
    @DBRouter
    int updateLeftCount(UserTakeActivityCount userTakeActivityCount);

    /**
     * 插入领取次数信息
     * @param userTakeActivityCount 请求入参
     */

    void insert(UserTakeActivityCount userTakeActivityCount);
}
