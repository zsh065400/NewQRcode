package org.laosao.two.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 网络个人名片二维码
 * <p/>
 * Created by lenovo on 2015/3/3.
 */
public class PersonIdCard extends BmobObject {

	//姓名
	private String name;
	//头像
	private BmobFile head;
	//QQ
	private String qq;
	//微信
	private String wechat;
	//联系地址
	private String address;
	//工作单位
	private String company;
	//职务
	private String job;
	//邮箱
	private String e_email;
	//联系电话
	private String phone;
	//微博
	private String weibo;
	//传真
	private String chuanzhen;
	//个人说明
	private String person;
	//查询标记
	private String url;

	public PersonIdCard() {

	}

	public PersonIdCard(String name, BmobFile head, String qq,
	                    String wechat, String address, String company,
	                    String job, String e_email, String phone,
	                    String weibo, String chuanzhen, String person,
	                    String url) {
		this.name = name;
		this.head = head;
		this.qq = qq;
		this.wechat = wechat;
		this.address = address;
		this.company = company;
		this.job = job;
		this.e_email = e_email;
		this.phone = phone;
		this.weibo = weibo;
		this.chuanzhen = chuanzhen;
		this.person = person;
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BmobFile getHead() {
		return head;
	}

	public void setHead(BmobFile head) {
		this.head = head;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getE_email() {
		return e_email;
	}

	public void setE_email(String e_email) {
		this.e_email = e_email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getWeibo() {
		return weibo;
	}

	public void setWeibo(String weibo) {
		this.weibo = weibo;
	}

	public String getChuanzhen() {
		return chuanzhen;
	}

	public void setChuanzhen(String chuanzhen) {
		this.chuanzhen = chuanzhen;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
