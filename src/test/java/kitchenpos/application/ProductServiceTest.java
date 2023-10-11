package kitchenpos.application;

import kitchenpos.dao.ProductDao;
import kitchenpos.domain.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductDao productDao;

    @InjectMocks
    private ProductService productService;

    @Test
    void 상품을_생성한다() {
        // given
        final Product expect = new Product();
        when(productDao.save(any(Product.class)))
                .thenReturn(expect);

        // when
        final Product product = new Product();
        product.setPrice(new BigDecimal(1000));
        final Product result = productService.create(product);

        // then
        assertThat(result).isEqualTo(expect);
    }

    @Test
    void 상품을_생성할_때_가격이_없으면_실패한다() {
        // given
        final Product product = new Product();

        // when, then
        assertThatThrownBy(() -> productService.create(product))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 상품을_생성할_때_가격이_0보다_작으면_실패한다() {
        // given
        final Product product = new Product();
        product.setPrice(new BigDecimal(-1000));

        // when, then
        assertThatThrownBy(() -> productService.create(product))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 모든_상품_목록을_반환한다() {
        // given
        final List<Product> expect = List.of();
        when(productDao.findAll())
                .thenReturn(expect);

        // when
        final List<Product> result = productService.list();

        // then
        assertThat(result).isEmpty();
    }
}