package com.dao;

import com.entity.RewardPrizeRelation;
import java.util.List;

public interface RewardPrizeRelationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RewardPrizeRelation record);

    RewardPrizeRelation selectByPrimaryKey(Long id);

    List<RewardPrizeRelation> selectAll();

    int updateByPrimaryKey(RewardPrizeRelation record);
}