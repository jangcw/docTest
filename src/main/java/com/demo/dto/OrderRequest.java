package com.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {
    private String orderId;
    private String product;
    private int quantity;
}