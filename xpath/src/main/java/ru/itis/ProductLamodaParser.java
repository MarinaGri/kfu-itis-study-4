package ru.itis;


import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ProductLamodaParser {
    private final String CATALOG_ITEM = "//div[@class='products-catalog__list']/div[@data-position]//*[@data-name]";
    private final String PRODUCT_NAME = "data-name";
    private final String PRODUCT_IMG = "data-image";
    private final String PRODUCT_PRICE_ORIGIN = "data-price-origin";
    private final String PRODUCT_PRICE = "data-price";

    public List<Product> parse(String filePath) {
        HtmlCleaner cleaner = new HtmlCleaner();

        List<Product> products = new ArrayList<>();

        try {
            TagNode root = cleaner.clean(new File(filePath), "UTF-8");
            try {
                Object[] itemNodes = root.evaluateXPath(CATALOG_ITEM);
                for (Object itemNode : itemNodes) {
                    TagNode tagNode = (TagNode) itemNode;
                    Product product = parseProduct(tagNode);
                    products.add(product);
                }
            } catch (XPatherException ex) {
                System.out.println("Cant parse file");
            }
        } catch (IOException ex) {
            System.out.println("Cant read file with path " + filePath);
        }
        return products;
    }

    private Product parseProduct(TagNode tagNode) throws XPatherException {
        Product product = new Product();

        String priceOrigin = tagNode.getAttributeByName(PRODUCT_PRICE_ORIGIN);
        String priceSale = tagNode.getAttributeByName(PRODUCT_PRICE);
        Double price = Double.parseDouble(priceSale == null ? priceOrigin : priceSale);
        product.setPrice(price);

        product.setName(tagNode.getAttributeByName(PRODUCT_NAME));
        product.setImg(tagNode.getAttributeByName(PRODUCT_IMG));
        return product;
    }
}
