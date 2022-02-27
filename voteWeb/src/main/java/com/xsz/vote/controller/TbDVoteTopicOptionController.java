package com.xsz.vote.controller;

import com.xsz.common.annotation.Log;
import com.xsz.common.controller.BaseController;
import com.xsz.common.domain.QueryRequest;
import com.xsz.common.domain.ResponseBo;
import com.xsz.common.domain.Tree;
import com.xsz.common.util.FileUtil;
import com.xsz.system.domain.User;
import com.xsz.vote.domain.VoteTopic;
import com.xsz.vote.domain.VoteTopicOption;
import com.xsz.vote.service.TbDVoteTopicOptinService;
import com.xsz.vote.service.TbDVoteTopicService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("voteTopic")
public class TbDVoteTopicOptionController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TbDVoteTopicService voteTopicService;
    @Autowired
    private TbDVoteTopicOptinService voteTopicOptinService;

    @RequestMapping("")
    @RequiresPermissions("voteTopicOption:list")
    public String index(Model model){
        User user = super.getCurrentUser();
        model.addAttribute("user",user);
        return "voteTopicOption/voteTopicOption";
    }

    @RequestMapping("getVoteTopicOption")
    @ResponseBody
    public ResponseBo getVoteTopicOption(Long id){
        try {
            VoteTopicOption voteTopicOption = this.voteTopicOptinService.findById(id);
            return ResponseBo.ok(voteTopicOption);
        }catch (Exception e){
            logger.error("获取投票选项失败",e);
            return ResponseBo.error("获取投票选项失败，请联系网站联络员");
        }
    }

    @Log("获取投票选项信息")
    @RequestMapping("list")
    @RequiresPermissions("voteTopicOption:list")
    @ResponseBody
    public Map<String,Object> voteTopicOptionList(QueryRequest request, VoteTopicOption voteTopicOption){
        return super.selectByPageNumSize(request,()->
                this.voteTopicOptinService.findAllVoteTopicOptions(voteTopicOption,request));
    }

    @RequestMapping("excel")
    @ResponseBody
    public ResponseBo voteTopicOptionExcel(VoteTopicOption voteTopicOption){
        try {
            List<VoteTopicOption> list = this.voteTopicOptinService.findAllVoteTopicOptions(voteTopicOption, null);
            return FileUtil.createExcelByPOIKit("投票选项表",list,VoteTopicOption.class);
        }catch (Exception e){
            logger.error("导出投票选项信息Excel失败",e);
            return ResponseBo.error("导出excel失败，请联系管理员");
        }
    }

    @RequestMapping("csv")
    @ResponseBody
    public ResponseBo voteTopicOptionCsv(VoteTopicOption voteTopicOption){
        try {
            List<VoteTopicOption> list = this.voteTopicOptinService.findAllVoteTopicOptions(voteTopicOption, null);
            return FileUtil.createCsv("投票选项表",list,VoteTopicOption.class);
        }catch (Exception e){
            logger.error("导出投票选项信息Csvl失败",e);
            return ResponseBo.error("导出Scv失败，请联系管理员");
        }
    }

    @Log("新增投票选项")
    @RequiresPermissions("voteTopicOption:add")
    @RequestMapping("add")
    @ResponseBody
    public ResponseBo addVoteTopicOption(VoteTopicOption voteTopicOption){
        try {
            voteTopicOption.setCreatetime(new Date());
            VoteTopic voteTopic = voteTopicService.findById((voteTopicOption.getTopicid()).longValue());
            voteTopicOption.setVoteid(voteTopic.getVoteid());
            //更新主题的选项数量
            int optcount = voteTopic.getOptioncount() == null ? 0 : voteTopic.getOptioncount();
            optcount++;
            voteTopic.setOptioncount(optcount);
            voteTopicService.updateVoteTopic(voteTopic);
            this.voteTopicOptinService.addVoteTopicOption(voteTopicOption);
            return ResponseBo.ok("新增投票选项成功");
        }catch (Exception e){
            logger.error("新增投票选项失败");
            return ResponseBo.error("新增投票选项失败，请联系网络管理员")
        }
    }

    @Log("修改投票选项")
    @RequiresPermissions("voteTopicOption:update")
    @RequestMapping("update")
    @ResponseBody
    public ResponseBo updateUser(VoteTopicOption voteTopicOption){
        try {
            this.voteTopicOptinService.updateVoteTopicOption(voteTopicOption);
            return ResponseBo.ok("修改投票选项成功");
        }catch (Exception e){
            logger.error("修改投票选项失败");
            return ResponseBo.error("修改投票选项失败，请联系网络管理员")
        }
    }


    @Log("删除投票选项")
    @RequiresPermissions("voteTopicOption:delete")
    @RequestMapping("delete")
    @ResponseBody
    public ResponseBo deleteVoteTopicOptions(String ids){
        try {
            this.voteTopicOptinService.deleteVoteTopicOptions(ids);
            return ResponseBo.ok("删除投票选项成功");
        }catch (Exception e){
            logger.error("删除投票选项失败");
            return ResponseBo.error("删除投票选项失败，请联系网络管理员");
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
