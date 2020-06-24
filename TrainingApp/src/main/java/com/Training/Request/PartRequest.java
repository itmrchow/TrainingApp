package com.Training.Request;

import java.util.Date;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

//作為接收request 之 class
//可以集中設定欄位接受格式
//之後進行轉換成model

public class PartRequest {
	
	@NotEmpty(message = "id is undefined")
	private String id;
	
	@JsonProperty("p_Name")
	@NotEmpty(message = "PartName is undefined")
	private String partName;
	
	private Date createDate;
	private Date updateDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
}
