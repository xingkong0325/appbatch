package com.xinhuanet.batch.po;

import java.io.Serializable;

public class ChinapnrProperties implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String version;					//版本号
	private String cmdId;					//消息类型
	private String merId;					//商户号
	private String usrId;					//用户号
	private String merUsrId;				//用户号
	private String operId;					//管理员号
	private String chkValue;				//签名
	private String respCode;				//应答返回码
	private String errMsg;					//应答返回码
	private String ordId;					//订单号
	private String ordAmt;					//订单金额
	private String curCode;					//币种
	private String pid;						//商品编号
	private String retUrl;					//返回url
	private String merPriv;					//商户私有域
	private String gateId;					//银行ID
	private String usrMp;					//购买者手机号
	private String divDetails;				//分账明细
	private String benDetails;				//退款入账分润明细串
	private String isCrCover;				//是否需要退款垫资
	private String orderType;				//订单类型
	private String payUsrId;				//付费用户号
	private String trxId;					//钱管家交易唯一标识
	private String retType;					//返回类型
	private String pnrNum;					//Pnr号
	private String refAmt;					//退款总金额
	private String oldOrdId;				//原始订单号
	private String transAmt;				//交易金额
	private String avlBal;					//可用余额
	private String tmpBal;					//临时余额
	private String sepBal;					//专用余额
	private String liqBal;					//待结算余额
	private String acctBal;					//账户余额
	private String procStat;				//处理状态
	private String refCnt;					//退款次数
	private String transType;				//交易类型
	private String merDate;					//商户日期
	private String merTime;					//商户时间
	private String bgRetUrl;				//订单支付时，商户后台应答地址
	private String isBalance;				//是否结算
	
	private String HPVersion;				//支付版本
	private String cancelURL;				//变长 120位   撤销交易时，返回商户的地址，商户自己负责维护
	private String cancelPriv;				//撤销私有域
	
	private int signRet;					//签名或验签返回值，非Chinapnr自有属性
	private String signMsg;					//签名信息
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getCmdId() {
		return cmdId;
	}
	public void setCmdId(String cmdId) {
		this.cmdId = cmdId;
	}
	public String getMerId() {
		return merId;
	}
	public void setMerId(String merId) {
		this.merId = merId;
	}
	public String getUsrId() {
		return usrId;
	}
	public void setUsrId(String usrId) {
		this.usrId = usrId;
	}
	public String getMerUsrId() {
		return merUsrId;
	}
	public void setMerUsrId(String merUsrId) {
		this.merUsrId = merUsrId;
	}
	public String getOperId() {
		return operId;
	}
	public void setOperId(String operId) {
		this.operId = operId;
	}
	public String getChkValue() {
		return chkValue;
	}
	public void setChkValue(String chkValue) {
		this.chkValue = chkValue;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public String getOrdId() {
		return ordId;
	}
	public void setOrdId(String ordId) {
		this.ordId = ordId;
	}
	public String getOrdAmt() {
		return ordAmt;
	}
	public void setOrdAmt(String ordAmt) {
		this.ordAmt = ordAmt;
	}
	public String getCurCode() {
		return curCode;
	}
	public void setCurCode(String curCode) {
		this.curCode = curCode;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getRetUrl() {
		return retUrl;
	}
	public void setRetUrl(String retUrl) {
		this.retUrl = retUrl;
	}
	public String getMerPriv() {
		return merPriv;
	}
	public void setMerPriv(String merPriv) {
		this.merPriv = merPriv;
	}
	public String getGateId() {
		return gateId;
	}
	public void setGateId(String gateId) {
		this.gateId = gateId;
	}
	public String getUsrMp() {
		return usrMp;
	}
	public void setUsrMp(String usrMp) {
		this.usrMp = usrMp;
	}
	public String getDivDetails() {
		return divDetails;
	}
	public void setDivDetails(String divDetails) {
		this.divDetails = divDetails;
	}
	public String getBenDetails() {
		return benDetails;
	}
	public void setBenDetails(String benDetails) {
		this.benDetails = benDetails;
	}
	public String getIsCrCover() {
		return isCrCover;
	}
	public void setIsCrCover(String isCrCover) {
		this.isCrCover = isCrCover;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getPayUsrId() {
		return payUsrId;
	}
	public void setPayUsrId(String payUsrId) {
		this.payUsrId = payUsrId;
	}
	public String getTrxId() {
		return trxId;
	}
	public void setTrxId(String trxId) {
		this.trxId = trxId;
	}
	public String getRetType() {
		return retType;
	}
	public void setRetType(String retType) {
		this.retType = retType;
	}
	public String getPnrNum() {
		return pnrNum;
	}
	public void setPnrNum(String pnrNum) {
		this.pnrNum = pnrNum;
	}
	public String getRefAmt() {
		return refAmt;
	}
	public void setRefAmt(String refAmt) {
		this.refAmt = refAmt;
	}
	public String getOldOrdId() {
		return oldOrdId;
	}
	public void setOldOrdId(String oldOrdId) {
		this.oldOrdId = oldOrdId;
	}
	public String getTransAmt() {
		return transAmt;
	}
	public void setTransAmt(String transAmt) {
		this.transAmt = transAmt;
	}
	public String getAvlBal() {
		return avlBal;
	}
	public void setAvlBal(String avlBal) {
		this.avlBal = avlBal;
	}
	public String getTmpBal() {
		return tmpBal;
	}
	public void setTmpBal(String tmpBal) {
		this.tmpBal = tmpBal;
	}
	public String getSepBal() {
		return sepBal;
	}
	public void setSepBal(String sepBal) {
		this.sepBal = sepBal;
	}
	public String getLiqBal() {
		return liqBal;
	}
	public void setLiqBal(String liqBal) {
		this.liqBal = liqBal;
	}
	public String getAcctBal() {
		return acctBal;
	}
	public void setAcctBal(String acctBal) {
		this.acctBal = acctBal;
	}
	public String getProcStat() {
		return procStat;
	}
	public void setProcStat(String procStat) {
		this.procStat = procStat;
	}
	public String getRefCnt() {
		return refCnt;
	}
	public void setRefCnt(String refCnt) {
		this.refCnt = refCnt;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public String getMerDate() {
		return merDate;
	}
	public void setMerDate(String merDate) {
		this.merDate = merDate;
	}
	public String getMerTime() {
		return merTime;
	}
	public void setMerTime(String merTime) {
		this.merTime = merTime;
	}
	public String getBgRetUrl() {
		return bgRetUrl;
	}
	public void setBgRetUrl(String bgRetUrl) {
		this.bgRetUrl = bgRetUrl;
	}
	public String getIsBalance() {
		return isBalance;
	}
	public void setIsBalance(String isBalance) {
		this.isBalance = isBalance;
	}
	
	public String getHPVersion() {
		return HPVersion;
	}
	public void setHPVersion(String hPVersion) {
		HPVersion = hPVersion;
	}
	public String getCancelURL() {
		return cancelURL;
	}
	public void setCancelURL(String cancelURL) {
		this.cancelURL = cancelURL;
	}
	public String getCancelPriv() {
		return cancelPriv;
	}
	public void setCancelPriv(String cancelPriv) {
		this.cancelPriv = cancelPriv;
	}
	public int getSignRet() {
		return signRet;
	}
	public void setSignRet(int signRet) {
		this.signRet = signRet;
	}
	public String getSignMsg() {
		return signMsg;
	}
	public void setSignMsg(String signMsg) {
		this.signMsg = signMsg;
	}
	@Override
	public String toString() {
		return "ChinapnrProperties [version=" + version + ", cmdId=" + cmdId
				+ ", merId=" + merId + ", usrId=" + usrId + ", merUsrId="
				+ merUsrId + ", operId=" + operId + ", chkValue=" + chkValue
				+ ", respCode=" + respCode + ", errMsg=" + errMsg + ", ordId="
				+ ordId + ", ordAmt=" + ordAmt + ", curCode=" + curCode
				+ ", pid=" + pid + ", retUrl=" + retUrl + ", merPriv="
				+ merPriv + ", gateId=" + gateId + ", usrMp=" + usrMp
				+ ", divDetails=" + divDetails + ", benDetails=" + benDetails
				+ ", isCrCover=" + isCrCover + ", orderType=" + orderType
				+ ", payUsrId=" + payUsrId + ", trxId=" + trxId + ", retType="
				+ retType + ", pnrNum=" + pnrNum + ", refAmt=" + refAmt
				+ ", oldOrdId=" + oldOrdId + ", transAmt=" + transAmt
				+ ", avlBal=" + avlBal + ", tmpBal=" + tmpBal + ", sepBal="
				+ sepBal + ", liqBal=" + liqBal + ", acctBal=" + acctBal
				+ ", procStat=" + procStat + ", refCnt=" + refCnt
				+ ", transType=" + transType + ", merDate=" + merDate
				+ ", merTime=" + merTime + ", bgRetUrl=" + bgRetUrl
				+ ", isBalance=" + isBalance + ", HPVersion=" + HPVersion
				+ ", cancelURL=" + cancelURL + ", cancelPriv=" + cancelPriv
				+ ", signRet=" + signRet + ", signMsg=" + signMsg + "]";
	}
}