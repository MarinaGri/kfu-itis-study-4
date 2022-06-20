package ru.itis;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ProductLamodaParser parser = new ProductLamodaParser();
        List<Product> products = parser.parse("C:\\Users\\Marina\\Desktop\\source.html");
        products.forEach(System.out::println);
    }
}
