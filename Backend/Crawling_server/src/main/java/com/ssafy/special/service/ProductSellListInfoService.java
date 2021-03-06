package com.ssafy.special.service;

import java.util.List;

import com.ssafy.special.dto.ProductSellListResponseDTO;

public interface ProductSellListInfoService {
	// 현재 사이클 한시간 전인 데이터만 가져옴
	List<ProductSellListResponseDTO> getProductSellLists(int page, long pid, int sort, List<Integer> market);
	// 현재 사이클 한시간 전인 데이터 갯수 가져옴
	Long getProductSellListCount(long pid, List<Integer> market);
	//게시글 상세보기
	ProductSellListResponseDTO getProductSellDetail(long id);
}
