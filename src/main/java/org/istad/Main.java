package org.istad;
import org.istad.model.dto.ProductResponDto;
import org.istad.model.entities.Product;
import org.istad.model.ropository.ProductCartRepository;
import org.istad.model.ropository.ProductRepository;
import org.istad.model.service.impl.OrderServiceImpl;
import org.istad.model.service.impl.ProductServiceImpl;
import org.istad.view.UI;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        OrderServiceImpl orderService = new OrderServiceImpl();
         UI ui = new UI();
         ui.ui();
    }
}