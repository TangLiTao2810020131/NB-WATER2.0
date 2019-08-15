package com.ets.nb_iot.model;

/**
 * NBi直读转换
 * @author 吴浩
 * @create 2018-12-26 10:38
 */
public class DirectReadNBI {
	private String tablbType;//表类型
	private String tableAddress;//表地址
	private String cumulativeFlowRate;//累计流量
	private String tableState;//表状态
	private String accumulatedHeat;//累计热量
	private String instantaneousValue;//进回水温度
	private String accumulatedEnergy;//累计电量
	private String valveControl;//阀门控制
	public String getTablbType() {
		return tablbType;
	}
	public void setTablbType(String tablbType) {
		this.tablbType = tablbType;
	}
	public String getTableAddress() {
		return tableAddress;
	}
	public void setTableAddress(String tableAddress) {
		this.tableAddress = tableAddress;
	}
	public String getCumulativeFlowRate() {
		return cumulativeFlowRate;
	}
	public void setCumulativeFlowRate(String cumulativeFlowRate) {
		this.cumulativeFlowRate = cumulativeFlowRate;
	}
	public String getTableState() {
		return tableState;
	}
	public void setTableState(String tableState) {
		this.tableState = tableState;
	}
	public String getAccumulatedHeat() {
		return accumulatedHeat;
	}
	public void setAccumulatedHeat(String accumulatedHeat) {
		this.accumulatedHeat = accumulatedHeat;
	}
	public String getInstantaneousValue() {
		return instantaneousValue;
	}
	public void setInstantaneousValue(String instantaneousValue) {
		this.instantaneousValue = instantaneousValue;
	}
	public String getAccumulatedEnergy() {
		return accumulatedEnergy;
	}
	public void setAccumulatedEnergy(String accumulatedEnergy) {
		this.accumulatedEnergy = accumulatedEnergy;
	}
	public String getValveControl() {
		return valveControl;
	}
	public void setValveControl(String valveControl) {
		this.valveControl = valveControl;
	}
	@Override
	public String toString() {
		return "DirectReadNBI [tablbType=" + tablbType + ", tableAddress=" + tableAddress + ", cumulativeFlowRate="
				+ cumulativeFlowRate + ", tableState=" + tableState + ", accumulatedHeat=" + accumulatedHeat
				+ ", instantaneousValue=" + instantaneousValue + ", accumulatedEnergy=" + accumulatedEnergy
				+ ", valveControl=" + valveControl + "]";
	}
}
