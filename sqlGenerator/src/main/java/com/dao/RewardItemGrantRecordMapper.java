package com.dao;

import com.entity.RewardItemGrantRecord;
import java.util.List;

public interface RewardItemGrantRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RewardItemGrantRecord record);

    RewardItemGrantRecord selectByPrimaryKey(Long id);

    List<RewardItemGrantRecord> selectAll();

    int updateByPrimaryKey(RewardItemGrantRecord record);
}