package com.xinhuanet.batch.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.xinhuanet.batch.po.Order;

public class OrdersRowMapper implements RowMapper<Order> {

	@Override
	public Order mapRow(ResultSet rs, int num) throws SQLException {
		Order o = new Order();
		o.setId(rs.getString("id"));
		o.setUid(rs.getString("uid"));
		o.setLoginName(rs.getString("loginname"));
		o.setMoney(rs.getDouble("money"));
		o.setAppId(rs.getInt("appid"));
		o.setAppOrderId(rs.getString("apporderid"));
		o.setTrxId(rs.getString("trxId"));
		o.setPayStatus(rs.getInt("paystatus"));
		o.setPayTime(rs.getTimestamp("paytime"));
		o.setPayType(rs.getString("paytype"));
		o.setGateId(rs.getString("gateid"));
		o.setGateName(rs.getString("gatename"));
		o.setIpAddress(rs.getString("ipaddress"));
		o.setException(rs.getString("exception"));
		o.setPid(rs.getString("pid"));
		o.setMerpriv(rs.getString("merpriv"));
		o.setExt(rs.getString("ext"));
		o.setOrderType(rs.getInt("orderType"));
		o.setBeforeMoney(rs.getDouble("beforemoney"));
		
		o.setOldOrderId(rs.getString("oldOrderId"));
		o.setSettleAccounts(rs.getInt("settleAccounts"));
		o.setSettleDate(rs.getTimestamp("settleDate"));
		o.setRepair(rs.getInt("repair"));			//补单次数
		o.setRepairDate(rs.getTimestamp("repairDate"));//补单时间
		o.setChangeTime(rs.getTimestamp("changetime"));//变更时间
		return o;
	}

}
