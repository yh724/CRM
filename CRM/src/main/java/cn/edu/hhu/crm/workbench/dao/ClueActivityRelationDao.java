package cn.edu.hhu.crm.workbench.dao;

import cn.edu.hhu.crm.workbench.domain.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationDao {


    int deleteRelationById(String id);

    int insertOne(ClueActivityRelation car);

    List<ClueActivityRelation> getRelationsByClueId(String clueId);

    int deleteByClueId(String clueId);
}
