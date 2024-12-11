package com.laskapi.eshop.productservice;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.laskapi.eshop.productservice.dto.ProductDto;
import com.laskapi.eshop.productservice.entity.Category;
import com.laskapi.eshop.productservice.entity.Product;
import com.laskapi.eshop.productservice.repository.CategoryRespository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatException;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@Log4j2
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase//(connection = EmbeddedDatabaseConnection.H2)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IntegrationTests {

    @Autowired
    TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    @Autowired
    CategoryRespository categoryRespository;

    Category category;
    Product product;
    String baseUrl;
    WebTestClient client;


    @BeforeEach
    public void setup() {
        category = Category.builder().name("test cateogory").parent_id(0).build();
        baseUrl = "http://localhost:%d".formatted(port);
        client = WebTestClient.bindToServer().baseUrl(baseUrl).build();
    }

    @Order(1)
    @Test
    public void addCategoryTest() {
        assertThat(addCategory().getResponseBody().blockFirst().getName()).isEqualTo(category.getName());
    }

    @Order(2)
    @Test
    public void addCategory_duplicateCategoryName_NotAcceptable() {
        assertThat(addCategory().getStatus()).isEqualTo(HttpStatus.NOT_ACCEPTABLE);
    }

    @Order(3)
    @Test
    public void getProductsTest() {
        ResponseEntity<String> result = testRestTemplate.getForEntity("/products", String.class);
        log.info("My response:::::::::::::::::::::" + result);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Order(4)
    @Test
    public void addProductTest() throws Exception {
        category = addCategory().getResponseBody().blockFirst();
        product = Product.builder().name("test product1").category(category).price(40).quantity(20).build();

        ResponseEntity<Product> result = testRestTemplate.postForEntity("/products",
                new ProductDto(product),
                Product.class);

        log.info("My response:::::::::::::::::::::" + result);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody().getName()).isEqualTo(product.getName());
    }

    private FluxExchangeResult<Category> addCategory() {
        FluxExchangeResult<Category> result = client.post().uri(baseUrl + "/products/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(category)
                .exchange()
                //  .expectStatus().isEqualTo(HttpStatus.OK)
                .returnResult(Category.class);
        return result;


    }


    /*
        mockMvc.perform(post("/products/products").
                contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                        .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.name").value("test product"));
*/

}
