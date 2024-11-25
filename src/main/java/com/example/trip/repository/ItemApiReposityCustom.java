package com.example.trip.repository;

import com.example.trip.dto.Item;

import java.util.List;

public interface ItemApiReposityCustom {
    List<Item> findByCategory(String category);
}
