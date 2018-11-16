package com.dao;

import com.entity.RewardPrizeType;
import java.util.List;

public interface RewardPrizeTypeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RewardPrizeType record);

    RewardPrizeType selectByPrimaryKey(Long id);

    List<RewardPrizeType> selectAll();

    int updateByPrimaryKey(RewardPrizeType record);
}