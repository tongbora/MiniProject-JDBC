package org.istad;
import org.istad.model.dto.ProductResponDto;
import org.istad.model.entities.Product;
import org.istad.model.ropository.ProductCartRepository;
import org.istad.model.ropository.ProductRepository;
import org.istad.model.service.impl.OrderServiceImpl;
import org.istad.model.service.impl.ProductServiceImpl;
import org.istad.view.ProductView;
import org.istad.view.UI;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class Main {
    public static void main(String[] args)  {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        new ProductView().productView();
    }
}