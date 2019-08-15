package com.ets.system.log.login.entity;
/**
 * @author: 姚轶文
 * @date:2018年9月5日 下午2:06:52
 * @version :
 * 
 */
public class IpBean {

	public String log_id;
	public String msg;
	public String ret;
	public Data data;
	
	public class Data
	{
		public String area;
		public String city;
		public String city_id;
		public String country;
		public String country_id;
		public String ip;
		public String isp;
		public String long_ip;
		public String region;
		public String region_id;
		public String getArea() {
			return area;
		}
		public void setArea(String area) {
			this.area = area;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getCity_id() {
			return city_id;
		}
		public void setCity_id(String city_id) {
			this.city_id = city_id;
		}
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		public String getCountry_id() {
			return country_id;
		}
		public void setCountry_id(String country_id) {
			this.country_id = country_id;
		}
		public String getIp() {
			return ip;
		}
		public void setIp(String ip) {
			this.ip = ip;
		}
		public String getIsp() {
			return isp;
		}
		public void setIsp(String isp) {
			this.isp = isp;
		}
		public String getLong_ip() {
			return long_ip;
		}
		public void setLong_ip(String long_ip) {
			this.long_ip = long_ip;
		}
		public String getRegion() {
			return region;
		}
		public void setRegion(String region) {
			this.region = region;
		}
		public String getRegion_id() {
			return region_id;
		}
		public void setRegion_id(String region_id) {
			this.region_id = region_id;
		}
		@Override
		public String toString() {
			return "Data [area=" + area + ", city=" + city + ", city_id=" + city_id + ", country=" + country
					+ ", country_id=" + country_id + ", ip=" + ip + ", isp=" + isp + ", long_ip=" + long_ip
					+ ", region=" + region + ", region_id=" + region_id + "]";
		}
		
		
	}

	public String getLog_id() {
		return log_id;
	}

	public void setLog_id(String log_id) {
		this.log_id = log_id;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getRet() {
		return ret;
	}

	public void setRet(String ret) {
		this.ret = ret;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "IpBean [log_id=" + log_id + ", msg=" + msg + ", ret=" + ret + ", data=" + data.toString() + "]";
	}
	
	
	
	
}
