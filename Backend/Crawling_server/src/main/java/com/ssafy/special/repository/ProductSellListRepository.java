package com.ssafy.special.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ssafy.special.domain.Product;
import com.ssafy.special.domain.ProductSellList;
import com.ssafy.special.dto.ByDistance;


public interface ProductSellListRepository extends JpaRepository<ProductSellList, Long>, ProductSellListRepositoryCustom {

//    @Query(value = "SELECT * INTO OUTFILE '/home/ubuntu/mysqltablefile/sellList.txt' FIELDS TERMINATED BY '|' " + 
//    		"LINES TERMINATED BY '\\n' " + 
//    		"FROM product_sell_list "+
//    		"where cycle >= :date" ,
//    		nativeQuery = true)
  @Query(value = "SELECT *  FROM product_sell_list where cycle >= :date",
	nativeQuery = true)
    List<ProductSellList> txtProductSellList(@Param("date")Long date);
  	
  	Optional<List<ProductSellList>> findByProductId(Product product);
  	
  	Optional<List<ProductSellList>> findByCycleLessThan(Long cycle);
  	
  	Optional<List<ProductSellList>> findByLocation(String location);
//  	"ORDER BY :sort"
  	@Query(value="SELECT id as id,aid as aid, market as market, product_id as productId, title as title, content as content, price as price, create_date as createDate, link as link, img as img, location as location, x as x, y as y, cycle as cycle,\r\n" + 
  			"ST_Distance_Sphere(POINT(:lon, :lat), POINT(x, y)) AS distance\r\n" + 
  			"FROM product_sell_list\r\n" + 
  			"WHERE ST_Distance_Sphere(POINT(:lon, :lat), POINT(x, y)) <= 10000 and product_id=:id and market in :market \r\n"
  			,
  			nativeQuery = true)
  	Optional<List<ByDistance>> nearProduct(@Param("lon")double x, @Param("lat")double y,@Param("id")long id, @Param("market") List<String> market, Pageable p);
  	
  	@Query(value="SELECT count(*)" + 
  			"FROM product_sell_list\r\n" + 
  			"WHERE ST_Distance_Sphere(POINT(:lon, :lat), POINT(x, y)) <= 10000 and product_id=:id and market in :market \r\n"
  			,
  			nativeQuery = true)
  	int nearProductCount(@Param("lon")double x, @Param("lat")double y,@Param("id")long id, @Param("market") List<String> market);
  	
  	int countByCycleAndAidAndMarket(long cycle, long aid, String market);
}
