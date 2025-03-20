package com.mayo.client.mayoclientapi.application.service;


import com.mayo.client.mayoclientapi.persistence.repository.BannerRepository;
import com.mayo.client.mayoclientapi.presentation.dto.response.ReadBannerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BannerService {

    private final BannerRepository bannerRepository;

    @Cacheable(value = "banners")
    public List<ReadBannerResponse> getBanners() {
        return bannerRepository.getBanners().stream().map(ReadBannerResponse::from).toList();
    }
}
