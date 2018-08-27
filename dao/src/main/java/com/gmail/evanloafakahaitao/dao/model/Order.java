package com.gmail.evanloafakahaitao.dao.model;

public class Order {

    private Item item;
    private User user;
    private Long id;
    private String orderUuid;

    private Order(Builder builder) {
        item = builder.itemsAndQuantity;
        user = builder.user;
        id = builder.id;
        orderUuid = builder.orderUuid;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Item getItem() {
        return item;
    }

    public User getUser() {
        return user;
    }

    public Long getId() {
        return id;
    }

    public String getOrderUuid() {
        return orderUuid;
    }

    public static final class Builder {
        private Item itemsAndQuantity;
        private User user;
        private Long id;
        private String orderUuid;

        private Builder() {
        }

        public Builder withItemsAndQuantity(Item val) {
            itemsAndQuantity = val;
            return this;
        }

        public Builder withUser(User val) {
            user = val;
            return this;
        }

        public Builder withId(Long val) {
            id = val;
            return this;
        }

        public Builder withOrderUuid(String val) {
            orderUuid = val;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
