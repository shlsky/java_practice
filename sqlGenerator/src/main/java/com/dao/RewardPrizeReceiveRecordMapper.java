package com.dao;

import com.entity.RewardPrizeReceiveRecord;
import java.util.List;

public interface RewardPrizeReceiveRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RewardPrizeReceiveRecord record);

    RewardPrizeReceiveRecord selectByPrimaryKey(Long id);

    List<RewardPrizeReceiveRecord> selectAll();

    int updateByPrimaryKey(RewardPrizeReceiveRecord record);
}