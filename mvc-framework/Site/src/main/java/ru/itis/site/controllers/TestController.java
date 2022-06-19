package ru.itis.site.controllers;


import ru.itis.framework.annotaions.Controller;
import ru.itis.framework.annotaions.RequestMapping;
import ru.itis.framework.messages.Header;
import ru.itis.framework.messages.Request;
import ru.itis.framework.messages.RequestMethod;
import ru.itis.framework.messages.Response;
import ru.itis.site.models.Product;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Controller
public class TestController {

    @RequestMapping(path = "/products")
    public String getProductsPage(Request request) {
        return "products";
    }

    @RequestMapping(path = "/products", method = RequestMethod.POST)
    public Response postProduct(Product product, Request request) {
        System.out.println(product);
        return Response.builder()
                .status(HttpServletResponse.SC_FOUND)
                .headers(Collections.singletonList(new Header("Location", request.getUri())))
                .build();
    }

    @RequestMapping(path = "/products/apples")
    public List<Product> getProducts(Request request) {
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            products.add(Product.builder().id(i).name("apple").price(i * 10).build());
        }
        return products;
    }

    @RequestMapping(path = "/products/name/available", placeholder = "name")
    public Product getProductByName(String name) {
        return Product.builder().id(42).name(name).price(100).build();
    }

    @RequestMapping(path = "/products/id", placeholder = "id")
    public Response getProductById(Request request, String productId) {
        Product product = Product.builder().id(Integer.parseInt(productId)).name("milk").price(90).build();
        return Response.builder()
                .viewName("products")
                .model(Collections.singletonMap("products", Collections.singletonList(product)))
                .build();
    }
}
