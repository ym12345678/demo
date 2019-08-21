package com.domain;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author YM
 * @desoription
 * @Date 2019年08月21日
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(generator="uuid2")
    @GenericGenerator(name="uuid2",strategy="org.hibernate.id.UUIDGenerator")
    @Column(length=32)
    private String id;

    //帐号
    @Column(name = "account",nullable = false,unique = true)
    private String account;

    //密码
    @Column(name = "passwd",nullable = false)
    @JSONField(serialize = false)
    private String passwd;

    //昵称
    @Column(name = "nick_name",length = 30)
    private String nickName;

    //角色id
    @Column(name = "role_id", length = 32)
    private String roleId;

    //使用状态（1-正常 0-停用 2-冻结投注 3-冻结资金 4-暂停提款功能）
    @Column(name = "user_state")
    private Integer userState;

    //支付密码
    @Column(name = "paymentPassword" , length = 50)
    private String paymentPassword;



    /** 用户在线状态（0-离线 1-在线） **/
    @Column(name = "online")
    private Integer online = 0;


    /** 登录方式   0-离线   1-pc 2-安卓    3-ios **/
    @Column(name = "agent")
    private Integer agent = 0;


    //开放的盘口
    @Column(name = "handicap",nullable = false)
    private String handicap;


    /** 会员是否拥有独立退水 (默认盘口)*/
    @Column(name = "default_handicap", length = 10)
    public String defaultHandicap;


    //对应代理赚取退水下拉列表的值
    @Column(name = "commission",precision = 14, scale = 4)
    private BigDecimal commission;

    //分红占成
    @Column(name = "share",precision = 14, scale = 4)
    private BigDecimal share;

    //分红模式,0表示反佣，1表示反水
    @Column(name = "share_model")
    private Integer shareModel;

    //分红条件
    @Column(name = "share_condition")
    private String shareCondition;

    //推广链接类型（0-无活动，1-有活动）
    @Column(name = "popularize_type")
    private Integer popularizeType;

    //账号使用期限（值为空不限制）
    @Column(name = "use_limit_time")
    private Date useLimitTime;

    //绑定ip
    @Column(name = "ip",length = 50)
    private String ip;

    //银行种类
    @Column(name = "bank_type",length = 30,nullable = false)
    private String bankType;

    //代理转帐（0-停用，1-使用）
    @Column(name = "agent_transfer",length = 30)
    private Integer agentTransfer;

    //帐号类型（0-试玩，1-正式,2-游客）
    @Column(name = "account_type",length = 30 ,nullable = false)
    private Integer accountType;

    //vip级别
    @Column(name = "vip_type",length = 30)
    private String vipType;

    //风险等级
    @Column(name = "risk_level",length = 30)
    private String riskLevel;

    //风控备注
    @Lob
    @Basic(fetch=FetchType.LAZY)
    @Column(name = "risk_remark")
    private String riskRemark;

    //财务备注
    @Lob
    @Basic(fetch=FetchType.LAZY)
    @Column(name = "finance_remark")
    private String financeRemark;

    //备注
    @Lob
    @Basic(fetch=FetchType.LAZY)
    @Column(name = "user_remark")
    private String userRemark;

    //用户类型
    @Column(name = "user_type",length = 30)
    public String userType;

    //来源类型
    @Column(name = "source_type",length = 30)
    public Byte sourceType;

    //帐户余额
    @Column(name = "remainder",precision = 14, scale = 4)
    public BigDecimal remainder;

    //代理线随机生成的验证码
    @Column(name = "invitation_code",length = 10)
    public String invitationCode;

    // 代理赚水模式 0-无条件 1-有条件
    @Column(name = "earning_water_model")
    private Integer earningWaterModel;


    //处理代理赚水密码(只有公司有)
    @Column(name = "commission_pd",length = 50)
    public String commissionPd;

    @Column(name = "version_remainder")
    public int versionRemainder = 0;

    @Transient
    public String parentAccount;


    /** 会员是否拥有独立退水 */
    @Column(name = "own_canback")
    public Integer ownCanback = 0;
}
