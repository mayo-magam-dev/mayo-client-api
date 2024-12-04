package com.mayo.client.mayoclientapi.application.service;

import com.mayo.client.mayoclientapi.persistance.repository.ItemRepository;
import com.mayo.client.mayoclientapi.presentation.dto.response.ReadItemResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class ItemService {

    private final ItemRepository itemRepository;

}
