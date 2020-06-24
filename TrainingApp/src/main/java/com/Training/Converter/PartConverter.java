package com.Training.Converter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.Training.Model.PartModel;
import com.Training.Request.PartRequest;
import com.Training.Response.PartResponse;

public class PartConverter {

	private PartConverter() {

	}

	public static PartModel toPartModel(PartRequest partRequest) {
		PartModel partModel = new PartModel();
		partModel.setId(partRequest.getId());
		partModel.setPartName(partRequest.getPartName());

		return partModel;
	}

	public static PartRequest toPartRequest(PartModel partModel) {
		PartRequest partRequest = new PartRequest();
		partRequest.setId(partModel.getId());
		partRequest.setPartName(partModel.getPartName());
		partRequest.setCreateDate(partModel.getCreateDate());
		partRequest.setUpdateDate(partModel.getUpdateDate());

		return partRequest;
	}

	public static PartResponse toPartResponse(PartModel partModel) {
		PartResponse partResponse = new PartResponse();
		partResponse.setId(partModel.getId());
		partResponse.setPartName(partModel.getPartName());
		partResponse.setCreateDate(partModel.getCreateDate());
		partResponse.setUpdateDate(partModel.getUpdateDate());

		return partResponse;
	}

	public static List<PartResponse> toPartResponseList(List<PartModel> partModels) {
		List<PartResponse> partResponseList = new ArrayList<PartResponse>();
		
		partModels.forEach(partModel -> partResponseList.add(toPartResponse(partModel)));

		return partResponseList;
	}

}
