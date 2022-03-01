package com.xsz.vote.service;

import com.xsz.common.domain.QueryRequest;
import com.xsz.common.domain.Tree;
import com.xsz.common.service.IService;
import com.xsz.vote.domain.VoteTopic;
import com.xsz.vote.domain.VoteTopicOption;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@CacheConfig(cacheNames = "TbDVoteTopicOptionService")
public interface TbDVoteTopicOptinService extends IService<VoteTopicOption> {
    @Cacheable(key = "#p0.toString()+(#p1 != null ? #p1.toString():'')")
    List<VoteTopicOption> findAllVoteTopicOptions(VoteTopicOption voteTopicOption, QueryRequest request);

    @CacheEvict(allEntries = true)
    public void addVoteTopicOption(VoteTopicOption voteTopicOption);

    @CacheEvict(key = "#p0",allEntries = true)
    public void updateVoteTopicOption(VoteTopicOption voteTopicOption);
    @CacheEvict(key = "#p0",allEntries = true)
    void deleteVoteTopicOptions(String voteTopicOptionIds);

    @Cacheable(key = "#p0")
    public VoteTopicOption findById(Long id);
//    Tree<VoteTopic> getVoteTopicButtonTree();
}
