package com.xsz.vote.service.impl;

import com.xsz.common.domain.QueryRequest;
import com.xsz.common.domain.Tree;
import com.xsz.common.service.impl.BaseService;
import com.xsz.common.util.TreeUtils;
import com.xsz.vote.dao.VoteTopicMapper;
import com.xsz.vote.domain.Vote;
import com.xsz.vote.domain.VoteTopic;
import com.xsz.vote.domain.VoteTopicOption;
import com.xsz.vote.service.TbDVoteTopicOptinService;
import com.xsz.vote.service.TbDVoteTopicService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service("voteTopicOptionsService")
@Transactional(propagation = Propagation.SUPPORTS,readOnly = true,rollbackFor = Exception.class)
public class VoteTopicOptionServiceImpl extends BaseService<VoteTopicOption> implements TbDVoteTopicOptinService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TbDVoteTopicService voteTopicService;

    @Override
    public List<VoteTopicOption> findAllVoteTopicOptions(VoteTopicOption voteTopicOption, QueryRequest request) {
        try {
            Example example = new Example(Vote.class);
            Example.Criteria criteria = example.createCriteria();
            if (StringUtils.isNotBlank(voteTopicOption.getOptions())){
                criteria.andCondition("options=",voteTopicOption.getOptions());
            }
            example.setOrderByClause("CREATETIME");
            return this.selectByExample(example);
        }catch (Exception e){
            logger.error("获取投票项目信息失败",e);
            return new ArrayList<>();
        }
    }

    @Override
    public void addVoteTopicOption(VoteTopicOption voteTopicOption) {
        save(voteTopicOption);

    }

    @Override
    public void updateVoteTopicOption(VoteTopicOption voteTopicOption) {
        voteTopicOption.setCreatetime(new Date());
        this.updateNotNull(voteTopicOption);

    }

    @Override
    public void deleteVoteTopicOptions(String voteTopicOptionIds) {
        List<String> list = Arrays.asList(voteTopicOptionIds.split(","));
        this.batchDelete(list,"ids",VoteTopicOption.class);
    }

    @Override
    public VoteTopicOption findById(Long id) {
        return this.selectByKey(id);
    }
}
