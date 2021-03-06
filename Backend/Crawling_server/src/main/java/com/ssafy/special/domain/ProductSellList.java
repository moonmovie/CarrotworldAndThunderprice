package com.ssafy.special.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@AllArgsConstructor
public class ProductSellList {

	@Id
	@GeneratedValue
	private long id;
	
	private long aid;//게시글 pid

	private String market;//마켓종류
		
	@ManyToOne
	@JoinColumn(name="productId")
	private Product productId;//품명
	
	private String title;//제목
	
	@Column(length = 8192)
	private String content;//내용
	
	private long price;//가격
	
	private LocalDateTime createDate;//작성일자
	
	private String link;//게시글 상세보기 링크
	
	private String img;//이미지 링크
	
	private String location;//지역
	
	@Column(nullable = true)
	private Double x;
	
	@Column(nullable = true)
	private Double y;
	
	@OneToMany(mappedBy = "articleA", cascade = CascadeType.REMOVE)
	private List<ProductSellArticleSimiler> productSellArticleSimilerA;
	
	@OneToMany(mappedBy = "articleB", cascade = CascadeType.REMOVE)
	private List<ProductSellArticleSimiler> productSellArticleSimilerB;
	
	
	/*
	 * 년월일시간데이터를 집어넣음으로써 크롤링 데이터가 누적될때 어떤 데이터가 최신으로업데이트 된건지 파악하고자함(21년9월20일10시 -> 21092010)
	 * 이유: 해당 테이블은 분석을위한 데이터누적기능과, 프론트에서 보여줄 한달치의 판매글 리스트 데이터를 저장하는 두가지 기능을 함
	 * 그렇기때문에 누적된 데이터중에서 프론트로 뿌려줘야하는 최신데이터가 무엇인지 확인이 가능해야함 이를위해 cycle컬럼을 만듬
	 * 
	 * 예시: 프론트에서 판매글 리스트 요청하면 현재 날짜, 시간 확인해서 (현재시간 - 1) 의 주기컬럼 이상인 데이터들을 뿌려줌
	 * (ex. 21년 9월 20일 11시라면 21092010 보다 큰 값을가지는 데이터들을 뿌려주면됨) 
	 */
	private Long cycle;//크롤링 주기 파악을 위해 만듬

	public ProductSellList() {
		LocalDateTime now= LocalDateTime.now();
		this.cycle=Long.parseLong(now.format(DateTimeFormatter.ofPattern("yyMMddHH")));//21년 9월 20일 13시 -> 21092013 
	}

	public ProductSellList(long id, String market) {
		super();
		this.id = id;
		this.market = market;
	}	
	
	
	
}
