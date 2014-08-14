package com.xinhuanet.batch.po;

import java.io.Serializable;
import java.util.Date;
/**
 * @author wangwei
 * @date 2013-5-30
 *
 */
public class Order implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 系统ID   作为订单号
	 */
	private String id; 
	/**
	 * 适用于退款等交易
	 */
	private String oldOrderId;
	/**
	 * 交易号，来源于第三方交易平台
	 */
	private String trxId;
	/**
	 * 用户ID
	 */
	private String uid;
	/**
	 * 用户登录名称
	 */
	private String loginName;
	/**
	 * 充值前金额
	 */
	private double beforeMoney;
	/**
	 * 充值金额
	 */
	private double money;
	/**
	 * 应用id
	 */
	private int appId;
	/**
	 * 应用订单号
	 */
	private String appOrderId;
	/**
	 * 支付状态(初始-0，成功-1，失败-2)
	 */
	private int payStatus;
	/**
	 * 支付时间
	 */
	private Date payTime;
	/**
	 * 订单变更时间
	 */
	private Date changeTime;
	/**
	 * 第三方网关（支付宝-alipay、通联支付-allinpay、汇付天下-chinapnr）
	 */
	private String payType;
	/**
	 * 订单类型(0-充值、1-即时交易、2-保证金)
	 */
	private int orderType;
	/**
	 * 交易类型 (0-支付、1-退款)
	 */
	private Integer transType;
	/**
	 * 对账状态 (0-未对账、1-已对账)
	 */
	private Integer settleAccounts;
	/**
	 * 结算时间
	 */
	private Date settleDate; 
	/**
	 * 补单次数
	 */
	private Integer repair;
	/**
	 * 补单时间
	 */
	private Date repairDate;
	/**
	 * 汇付所需要的银行网关号，默认为""值，而不是null
	 * 如果为null汇付验签失败
	 */
	private String gateId = "";
	/**
	 * 网关名称
	 */
	private String gateName;
	/**
	 * 充值IP地址
	 */
	private String ipAddress;
	/**
	 * 异常信息
	 */
	private String exception;
	/**
	 * 商品号
	 */
	private String pid;
	/**
	 * 应用私有域
	 */
	private String merpriv;
	/**
	 * 扩展字段:授权号、参考号、交易号等
	 */
	private String ext;
	
	/**
	 * 充值状态名称
	 */
	private String payStatusName;
	
	/**
	 * 第三方网关名称
	 */
	private String payTypeName;
	
	/**
	 * 币种
	 */
	private int curType;
	/**
	 * 币种名称
	 */
	private String curName;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public int getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(int payStatus) {
		this.payStatus = payStatus;
	}
	public String getPayStatusName() {
		return payStatusName;
	}
	public void setPayStatusName(String payStatusName) {
		this.payStatusName = payStatusName;
	}
	public Date getPayTime() {
		return payTime;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getPayTypeName() {
		return payTypeName;
	}
	public void setPayTypeName(String payTypeName) {
		this.payTypeName = payTypeName;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public String getGateId() {
		return gateId;
	}
	public void setGateId(String gateId) {
		this.gateId = gateId;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public int getCurType() {
		return curType;
	}
	public void setCurType(int curType) {
		this.curType = curType;
	}
	public String getCurName() {
		return curName;
	}
	public void setCurName(String curName) {
		this.curName = curName;
	}
	public int getAppId() {
		return appId;
	}
	public void setAppId(int appId) {
		this.appId = appId;
	}
	public String getAppOrderId() {
		return appOrderId;
	}
	public void setAppOrderId(String appOrderId) {
		this.appOrderId = appOrderId;
	}
	public String getGateName() {
		return gateName;
	}
	public void setGateName(String gateName) {
		this.gateName = gateName;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getMerpriv() {
		return merpriv;
	}
	public void setMerpriv(String merpriv) {
		this.merpriv = merpriv;
	}
	public int getOrderType() {
		return orderType;
	}
	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}
	public double getBeforeMoney() {
		return beforeMoney;
	}
	public void setBeforeMoney(double beforeMoney) {
		this.beforeMoney = beforeMoney;
	}
	public String getTrxId() {
		return trxId;
	}
	public void setTrxId(String trxId) {
		this.trxId = trxId;
	}
	public Integer getTransType() {
		return transType;
	}
	public void setTransType(Integer transType) {
		this.transType = transType;
	}
	public Integer getSettleAccounts() {
		return settleAccounts;
	}
	public void setSettleAccounts(Integer settleAccounts) {
		this.settleAccounts = settleAccounts;
	}
	public Date getSettleDate() {
		return settleDate;
	}
	public void setSettleDate(Date settleDate) {
		this.settleDate = settleDate;
	}
	public Integer getRepair() {
		return repair;
	}
	public void setRepair(Integer repair) {
		this.repair = repair;
	}
	public Date getRepairDate() {
		return repairDate;
	}
	public void setRepairDate(Date repairDate) {
		this.repairDate = repairDate;
	}
	public String getOldOrderId() {
		return oldOrderId;
	}
	public void setOldOrderId(String oldOrderId) {
		this.oldOrderId = oldOrderId;
	}
	public Date getChangeTime() {
		return changeTime;
	}
	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}
	@Override
	public String toString() {
		return "Order [id=" + id + ", oldOrderId=" + oldOrderId + ", trxId="
				+ trxId + ", uid=" + uid + ", loginName=" + loginName
				+ ", beforeMoney=" + beforeMoney + ", money=" + money
				+ ", appId=" + appId + ", appOrderId=" + appOrderId
				+ ", payStatus=" + payStatus + ", payTime=" + payTime
				+ ", changeTime=" + changeTime + ", payType=" + payType
				+ ", orderType=" + orderType + ", transType=" + transType
				+ ", settleAccounts=" + settleAccounts + ", settleDate="
				+ settleDate + ", repair=" + repair + ", repairDate="
				+ repairDate + ", gateId=" + gateId + ", gateName=" + gateName
				+ ", ipAddress=" + ipAddress + ", exception=" + exception
				+ ", pid=" + pid + ", merpriv=" + merpriv + ", ext=" + ext
				+ ", payStatusName=" + payStatusName + ", payTypeName="
				+ payTypeName + ", curType=" + curType + ", curName=" + curName
				+ "]";
	}
}
