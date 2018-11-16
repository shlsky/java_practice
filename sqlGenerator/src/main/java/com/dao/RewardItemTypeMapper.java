package com.dao;

import com.entity.RewardItemType;
import java.util.List;

public interface RewardItemTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RewardItemType record);

    RewardItemType selectByPrimaryKey(Integer id);

    List<RewardItemType> selectAll();

    int updateByPrimaryKey(RewardItemType record);
}