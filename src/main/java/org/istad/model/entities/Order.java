package org.istad.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Order {
    Integer id;
    Integer user_id;
    Integer product_id;
    Date order_date;
}
