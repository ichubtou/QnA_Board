package com.example.qnaboard.order.entity;

import com.example.qnaboard.audit.Auditable;
import com.example.qnaboard.coffee.entity.Coffee;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class OrderCoffee extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderCoffee;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "COFFEE_ID")
    private Coffee coffee;

    public void addOrder(Order order) {
        this.order = order;
        if(!this.order.getOrderCoffees().contains(order)) {
            this.order.getOrderCoffees().add(this);
        }
    }

    public void addCoffee(Coffee coffee) {
        this.coffee = coffee;
        if(!this.coffee.getOrderCoffees().contains(coffee)) {
            this.coffee.getOrderCoffees().add(this);
        }
    }
}