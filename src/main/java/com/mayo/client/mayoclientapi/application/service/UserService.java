package com.mayo.client.mayoclientapi.application.service;

import com.google.cloud.firestore.DocumentReference;
import com.mayo.client.mayoclientapi.common.annotation.FirestoreTransactional;
import com.mayo.client.mayoclientapi.common.exception.ApplicationException;
import com.mayo.client.mayoclientapi.common.exception.payload.ErrorStatus;
import com.mayo.client.mayoclientapi.persistence.domain.Store;
import com.mayo.client.mayoclientapi.persistence.domain.User;
import com.mayo.client.mayoclientapi.persistence.domain.type.DeviceType;
import com.mayo.client.mayoclientapi.persistence.repository.StoreRepository;
import com.mayo.client.mayoclientapi.persistence.repository.UserRepository;
import com.mayo.client.mayoclientapi.presentation.dto.request.CreateUserRequest;
import com.mayo.client.mayoclientapi.presentation.dto.request.UpdateFavoriteStoreRequest;
import com.mayo.client.mayoclientapi.presentation.dto.request.UpdateNoticeStoreRequest;
import com.mayo.client.mayoclientapi.presentation.dto.response.ReadStoreResponse;
import com.mayo.client.mayoclientapi.presentation.dto.response.ReadUserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@FirestoreTransactional
public class UserService {

    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final StorageService storageService;

    public void createUser(CreateUserRequest request, String uid) {
        userRepository.save(request.toEntity(uid));
    }

    public void deleteUser(String uid) {
        userRepository.deleteUser(uid);
    }

    public ReadUserResponse getUser(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당하는 유저가 없습니다.", 404, LocalDateTime.now())
                ));

        return ReadUserResponse.from(user);
    }

    public List<ReadStoreResponse> getUserNoticeStores(String userId) {

        List<ReadStoreResponse> responses = new ArrayList<>();

        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당하는 유저가 없습니다.", 404, LocalDateTime.now())
                ));

        for(DocumentReference docRef : user.getNoticeStores()) {

            Store store = storeRepository.findByDocRef(docRef)
                            .orElseThrow(() -> new ApplicationException(
                                    ErrorStatus.toErrorStatus("해당하는 가게가 없습니다.", 404, LocalDateTime.now())
                            ));

            responses.add(ReadStoreResponse.from(store));
        }

        return responses;
    }

    public List<ReadStoreResponse> getUserFavoriteStores(String userId) {

        List<ReadStoreResponse> responses = new ArrayList<>();

        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당하는 유저가 없습니다.", 404, LocalDateTime.now())
                ));

        for(DocumentReference docRef : user.getFavoriteStores()) {

            Store store = storeRepository.findByDocRef(docRef)
                    .orElseThrow(() -> new ApplicationException(
                            ErrorStatus.toErrorStatus("해당하는 가게가 없습니다.", 404, LocalDateTime.now())
                    ));

            responses.add(ReadStoreResponse.from(store));
        }

        return responses;
    }

    public void updateNoticeStore(UpdateNoticeStoreRequest request, String userId) {

        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당하는 유저가 없습니다.", 404, LocalDateTime.now())
                ));

        DocumentReference target = storeRepository.findDocRefById(request.storeId())
                .orElse(null);

        List<DocumentReference> noticeStores = user.getNoticeStores();

        if(noticeStores.contains(target)) {
            userRepository.removeNoticeStore(user, target);
        } else {
            userRepository.addNoticeStore(user, target);
        }
    }

    public void updateFavoriteStore(UpdateFavoriteStoreRequest request, String userId) {

        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당하는 유저가 없습니다.", 404, LocalDateTime.now())
                ));

        DocumentReference target = storeRepository.findDocRefById(request.storeId())
                .orElse(null);

        List<DocumentReference> favoriteStores = user.getFavoriteStores();

        if(favoriteStores.contains(target)) {
            userRepository.removeFavoriteStore(user, target);
        } else {
            userRepository.addFavoriteStore(user, target);
        }
    }

    public void updateNickName(String userId, String nickName) {

        userRepository.findByUserId(userId)
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당하는 유저가 없습니다.", 404, LocalDateTime.now())
                ));

        userRepository.updateUserNickName(userId, nickName);
    }

    public void updateEmail(String userId, String email) {

        userRepository.findByUserId(userId)
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당하는 유저가 없습니다.", 404, LocalDateTime.now())
                ));

        userRepository.updateUserEmail(userId, email);
    }

    public void updateAgreeMarketing(String userId, Boolean agreeMarketing) {

        userRepository.findByUserId(userId)
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당하는 유저가 없습니다.", 404, LocalDateTime.now())
                ));

        userRepository.updateAgreeMarketing(userId, agreeMarketing);
    }

    public void updateUserImage(String userId, MultipartFile file) {

        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당하는 유저가 없습니다.", 404, LocalDateTime.now())
                ));

        if(user.getPhotoUrl() != null && storageService.isFirebaseStorageUrl(user.getPhotoUrl())) {
            storageService.deleteFirebaseBucket(user.getPhotoUrl());
        }

        String imageUrl = storageService.uploadFirebaseBucket(file, user.getName() + "profile" + UUID.randomUUID());

        userRepository.updateUserImage(userId, imageUrl);
    }

    public void createFCMToken(String userId, String fcmToken, DeviceType deviceType) {
        userRepository.createFCMTokenById(userId, fcmToken, deviceType);
    }
}
