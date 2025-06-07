package longhoang.uet.mobile.closm.repositories;

import longhoang.uet.mobile.closm.dtos.response.itemDTO.TopTaggedItemByCategory;
import longhoang.uet.mobile.closm.models.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductItemRepository extends JpaRepository<ProductItem, Long> {
    
    // Lấy tất cả các biến thể theo product_id
    List<ProductItem> findAllByBaseProductId(Long productId);

    List<ProductItem> findDistinctByTag(String tag);
    @Query(value = "SELECT distinct tag from product_items", nativeQuery = true)
    List<String> findDistinctTags();

    @Query(value = "SELECT distinct tag from product_items where base_product_id = :productId", nativeQuery = true)
    List<String> findAllDistinctTagByProductId(long productId);

    List<ProductItem> findAllByTag(String tag);

    @Query(value = """

            with bestSellingTags as (
    select i.tag,sum(oi.quantity) as sold_quantity from product_items i
    join orders_items oi on i.id = oi.product_item_id
    where i.base_product_id = :id
    group by i.tag
   order by sum(oi.quantity) desc
    Limit 4)
    select bst.tag,bst.sold_quantity, (select i.image_url from product_items i
                                        join orders_items oi on i.id = oi.product_item_id
                                        where i.tag = bst.tag
                                        group by oi.product_item_id
                                        order by sum(oi.quantity) desc 
                                       limit 1) as image_url
   from bestSellingTags bst
    """, nativeQuery = true)
    List<TopTaggedItemByCategory> getTopProductByTagGroupedByBaseProduct(long id);

    @Query(value = "SELECT min(id) from product_items where base_product_id = :baseProductId group by tag  limit 4", nativeQuery = true)
    List<Long> getMinIdProductGroupByTag(Long baseProductId);

    @Query(value = "select sum(quantity) from product_items where base_product_id = :baseProductId group by base_product_id", nativeQuery = true)
    Integer getSumProductByBaseProductId(Long baseProductId);
}
