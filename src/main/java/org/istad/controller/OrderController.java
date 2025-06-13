package org.istad.controller;

import org.istad.model.dto.ProductResponDto;
import org.istad.model.entities.Order;
import org.istad.model.service.impl.OrderServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class OrderController {
    OrderServiceImpl orderService = new OrderServiceImpl();
    public Order makeOrder(){
        return orderService.makeOrder();
    }

    public List<ProductResponDto> getAllProductInOrder(){
        return orderService.getAllProductInOrder();
    }
}
