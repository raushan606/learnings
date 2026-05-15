package com.raushan.stock;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Stock {

    private final List<Item> items;
    private Map<Integer, Integer> itemQuantities; // Map of item ID to quantity

    Stock() {
        this.items = new ArrayList<>();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItemQuantities(Map<Integer, Integer> itemQuantities) {
        this.itemQuantities = itemQuantities;
    }

    public Map<Integer, Integer> getItemQuantities() {
        return itemQuantities;
    }

    public boolean isItemAvailable(int itemId) {
        return itemQuantities.getOrDefault(itemId, 0) > 0;
    }

    public void reduceItemQuantity(int itemId) {
        if (isItemAvailable(itemId)) {
            itemQuantities.put(itemId, itemQuantities.get(itemId) - 1);
        }
    }

    public Item getItemById(int itemId) {
        return items.stream()
                .filter(item -> item.id() == itemId)
                .findFirst()
                .orElse(null);
    }


}
