package com.xsz.vote.controller;

import com.xsz.common.annotation.Log;
import com.xsz.common.controller.BaseController;
import com.xsz.common.domain.QueryRequest;
import com.xsz.common.domain.ResponseBo;
import com.xsz.common.domain.Tree;
import com.xsz.common.util.FileUtil;
import com.xsz.system.domain.User;
import com.xsz.vote.domain.Vote;
import com.xsz.vote.domain.VoteTopic;
import com.xsz.vote.service.TbDVoteTopicService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("voteTopic")
public class TbDVoteTopicController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TbDVoteTopicService voteTopicService;

    @RequestMapping("")
    @RequiresPermissions("voteTopic:list")
    public String index(Model model){
        User user = super.getCurrentUser();
        model.addAttribute("user",user);
        return "voteTopic/voteTopic";
    }

    @RequestMapping("getVoteTopic")
    @ResponseBody
    public ResponseBo getVoteTopic(Long id){
        try {
            VoteTopic voteTopic = this.voteTopicService.findById(id);
            return ResponseBo.ok(voteTopic);
        }catch (Exception e){
            logger.error("获取投票项目失败",e);
            return ResponseBo.error("获取投票项目失败，请联系网站联络员");
        }
    }

    @Log("获取投票项目信息")
    @RequestMapping("list")
    @RequiresPermissions("voteTopic:list")
    @ResponseBody
    public Map<String,Object> voteTopicList(QueryRequest request, VoteTopic voteTopic){
        return super.selectByPageNumSize(request,()->
                this.voteTopicService.findAllVoteTopinc(voteTopic,request));
    }

    @RequestMapping("excel")
    @ResponseBody
    public ResponseBo voteTopicExcel(VoteTopic voteTopic){
        try {
            List<VoteTopic> list = this.voteTopicService.findAllVoteTopinc(voteTopic, null);
            return FileUtil.createExcelByPOIKit("投票项目表",list,VoteTopic.class);
        }catch (Exception e){
            logger.error("导出投票项目信息Excel失败",e);
            return ResponseBo.error("导出excel失败，请联系管理员");
        }
    }

    @RequestMapping("csv")
    @ResponseBody
    public ResponseBo voteTopicCsv(VoteTopic voteTopic){
        try {
            List<VoteTopic> list = this.voteTopicService.findAllVoteTopinc(voteTopic, null);
            return FileUtil.createCsv("投票项目表",list,VoteTopic.class);
        }catch (Exception e){
            logger.error("导出投票项目信息Csvl失败",e);
            return ResponseBo.error("导出Scv失败，请联系管理员");
        }
    }

    @Log("新增投票项目")
    @RequiresPermissions("voteTopic:add")
    @RequestMapping("add")
    @ResponseBody
    public ResponseBo addVoteTopic(VoteTopic voteTopic){
        try {
            this.voteTopicService.addVoteTopic(voteTopic);
            return ResponseBo.ok("新增投票项目成功");
        }catch (Exception e){
            logger.error("新增投票项目失败");
            return ResponseBo.error("新增投票项目失败，请联系网络管理员");
        }
    }

    @Log("修改投票项目")
    @RequiresPermissions("voteTopic:update")
    @RequestMapping("update")
    @ResponseBody
    public ResponseBo updateUser(VoteTopic voteTopic){
        try {
            this.voteTopicService.updateVoteTopic(voteTopic);
            return ResponseBo.ok("修改投票项目成功");
        }catch (Exception e){
            logger.error("修改投票项目失败");
            return ResponseBo.error("修改投票项目失败，请联系网络管理员");
        }
    }


    @Log("删除投票项目")
    @RequiresPermissions("voteTopic:delete")
    @RequestMapping("delete")
    @ResponseBody
    public ResponseBo deleteVoteTopics(String ids){
        try {
            this.voteTopicService.deleteVoteTopics(ids);
            return ResponseBo.ok("删除投票项目成功");
        }catch (Exception e){
            logger.error("删除投票项目失败");
            return ResponseBo.error("删除投票项目失败，请联系网络管理员");
        }
    }

    @RequestMapping("voteTopicButtonTree")
    @ResponseBody
    public ResponseBo getVoteButtonTree(){
        try {
            Tree<VoteTopic> tree = this.voteTopicService.getVoteTopicButtonTree();
            return ResponseBo.ok(tree);
        }catch (Exception e){
            logger.error("获取投票项目表失败");
            return ResponseBo.error("获取投票项目表失败，请联系网络管理员");
        }
    }
}
