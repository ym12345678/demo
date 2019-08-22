package com.domain.filter.constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 需要拦截的URI常量
 */
public class PreventDupURI {

    //需要拦截的URI常量集合
    public static final List<String> PREVENT_DUP_URI = new ArrayList<String>(Arrays.asList(
            //测试
//            "/testPreventDupToken",

            //正式
            "/register",//会员注册

            "/api/bets/bet",//会员下注接口

            "/api/bank/update",//更新银行信息
            "/api/bankCard/delete",//删除银行卡
            "/api/bankCard/save",//会员绑定银行卡


            "/api/canback/saveParams",//保存退水设置
            "/api/canback/saveDefaultParams",//保存会员退水设置

            "/api/lotteryManage/update",//更新彩种封盘时间
            "/api/lotteryManage/changeState",//修改彩票管理启停状态

            "/api/member/recharge",//会员充值
            "/api/member/withdraw",//会员提款接口
            "/api/member/setSecurityAnswer",//设置安全问题答案
            "/api/member/savePersonalData",//保存个人资料

            "/api/escort/saveParams",//批量更新彩种玩法降陪管理表

            "/api/payment/pay",//支付提交接口

            "/api/memberCount/save",//新增会员概要信息

            "/api/memberClassify/update2",//更新用户类型

            "/api/receivabale/save",//收款设置 保存/修改一条收款设置记录
            "/api/receivabale/disableorenableconfig",//收款设置 启用/弃用一条数据/删除一条数据

            "/api/userType/saveOrUpdate",//用户类型控制层 新增/修改用户类型数据接口

            "/api/branch/saveRemainder",//用户管理-下线会员-修改页面信息-额度修改保存
            "/api/branch/updateMemberInfo",//用户管理-下线会员-修改页面保存 （此接口不能修改余额）

            "/api/branch/createAgent",//新建代理

            "/api/handledCommission/handledCommission",//处理代理赚水

            "/api/changeUserState",//修改用户状态

            "/api/operationUser/updatePassword",//代理修改登陆密码
            "/api/operationUser/updatePayPassword",//修改支付密码
            "/api/operationUser/withdrawalPassword",//代理设置提款密码
            "/api/operationUser/transfer",//代理转帐
            "/api/operationUser/updateAgentInformation",//修改代理详细信息
            "/api/operationUser/createInvitationCode",//代理生成验证码

            "/api/user/createSubaccount",//新建子账号
            "/api/user/updateSubaccount",//修改子账号的权限
            "/api/user/deleteSubaccount",//删除子账号的接口

            "/api/operate/insertOrUpdate",//新建用户操作变更记录

            "/api/vipAutoClassification/insertOrUpdate",//vip自动分类 设置 新增或修改（修改时要将未修改参数值都传入实体对象）

            "/api/vip/add",//用户vip自动升级配置 新增用户vip自动升级配置
            "/api/vip/update",//更新用户vip自动升级配置
            "/api/vip/delete",//根据id删除用户vip自动升级配置

            "/api/notice/save",//公告控制层保存和修改

            "/api/messages/sendMessages", //消息控制层 发送消息接口

            "/api/bets/betApp"//app端下注接口







    ));
}
