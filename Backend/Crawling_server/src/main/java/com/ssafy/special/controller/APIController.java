package com.ssafy.special.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.special.domain.Product;
import com.ssafy.special.dto.ByDistance;
import com.ssafy.special.dto.DatePriceResponseDTO;
import com.ssafy.special.dto.PriceStepResponseDTO;
import com.ssafy.special.dto.ProductSellArticleSimilerResponseDTO;
import com.ssafy.special.dto.ProductSellListResponseDTO;
import com.ssafy.special.service.DatePriceService;
import com.ssafy.special.service.NearProductServiceImpl;
import com.ssafy.special.service.ProductByPriceService;
import com.ssafy.special.service.ProductSellListInfoService;
import com.ssafy.special.service.ProductService;
import com.ssafy.special.service.SimilarityService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*" )
public class APIController {

	private final ProductSellListInfoService productSellListInfoService;
	private final SimilarityService similarityService;
	private final ProductService productService;
	private final DatePriceService datePriceService;
	private final ProductByPriceService productByPriceService; 
	private final NearProductServiceImpl nearProductServiceImpl;
	//ProductSellList 뿌려줌(최신사이클만)
	@GetMapping("/productselllist")
	public ResponseEntity<Map<String, Object>> getProductSellList(
										@RequestParam(defaultValue = "0") int page, 
										@RequestParam long pid, 
										@RequestParam(defaultValue = "0")int sort, 
										@RequestParam(defaultValue = "0") List<Integer> market) {
			List<ProductSellListResponseDTO> productSellLists = productSellListInfoService.getProductSellLists(page, pid, sort, market);
			
			Long count = productSellListInfoService.getProductSellListCount(pid, market);
						
			
			Map<String, Object> ret = new HashMap<String, Object>();
			
			if(productSellLists==null) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			}else if(productSellLists.size()==0) {
				ret.put("msg", "페이지, 게시글번호를 다시 확인해 주세요");
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ret);
			}
			long totalpage= count/20;
			if(count%20>0)
				totalpage+=1;
			ret.put("totalpage", totalpage);
			ret.put("list", productSellLists);
			
			return ResponseEntity.status(HttpStatus.OK).body(ret);
	}
	
	@GetMapping("/productselldetail")
	public ResponseEntity<Map<String, Object>> getProductSellDetail(@RequestParam long id) {
			ProductSellListResponseDTO productSellDetail = productSellListInfoService.getProductSellDetail(id);
			List<ProductSellArticleSimilerResponseDTO> similerlist = similarityService.returnSimilarity(id);
			Map<String, Object> ret = new HashMap<String, Object>();
			
			if(productSellDetail==null) {
				ret.put("msg", "페이지, 게시글번호를 다시 확인해 주세요");
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ret);
			}
		
			ret.put("articleDeatil", productSellDetail);			
			ret.put("similerProduct", similerlist);
			return ResponseEntity.status(HttpStatus.OK).body(ret);
	}
	
	@GetMapping("/product")
	public ResponseEntity<Map<String, Object>> getProductPrice(@RequestParam long pid,@RequestParam(defaultValue = "0") List<Integer> market ) {
			Map<String, Object> ret = new HashMap<String, Object>();
			Long count = productSellListInfoService.getProductSellListCount(pid, market);
			
			Product product = productService.getProduct(pid);
			if(product==null) {
				ret.put("msg", "제품번호를 다시 확인해 주세요");
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ret);
			}
			ret.put("product", product);
			ret.put("searchcount", count);
			
			return ResponseEntity.status(HttpStatus.OK).body(ret);
	}
	
	@GetMapping("/dateprice")
	public ResponseEntity<Map<String, Object>> getDatePrice(@RequestParam long pid,@RequestParam(defaultValue = "7") int size ) {
			Map<String, Object> ret = new HashMap<String, Object>();
			
			List<DatePriceResponseDTO> list = datePriceService.getDatePrice(pid,size);
			
			if(list==null) {
				ret.put("msg", "제품번호를 다시 확인해 주세요");
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ret);
			}else if(list.size()==0) {
				ret.put("msg", "해당제품의 가격정보가 없습니다");
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ret);
			}
			ret.put("dateprice", list);
			
			return ResponseEntity.status(HttpStatus.OK).body(ret);
	}
	
	@GetMapping("/byprice")
	public ResponseEntity<Map<String,PriceStepResponseDTO>> getbyPrice(@RequestParam long pid, @RequestParam long cycle){
		
		return ResponseEntity.status(HttpStatus.OK).body(productByPriceService.byPrice(pid, cycle));
	}
	
//	 1 : 날짜 내림차순
//     2 : 날짜 오름차순
//     3 : 가격 내림차순
//     4 : 가격 오름차순
//     5 : 거리 가까운순
	@GetMapping("/nearProduct")
	public ResponseEntity<Map<String,Object>> getNearProduct(@RequestParam double lon,
																		@RequestParam double lat,
																		@RequestParam long pid,
																		@RequestParam(defaultValue = "0") int page,
																		@RequestParam(defaultValue = "0") int sort,
																		@RequestParam(defaultValue = "0") int market){
		Map<String,Object> res = new HashMap<String, Object>();
		res.put("result",nearProductServiceImpl.nearProduct(lon, lat, pid, page, sort, market));
		res.put("total_count", nearProductServiceImpl.nearProductCount(lon, lat, pid, market));
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}
}