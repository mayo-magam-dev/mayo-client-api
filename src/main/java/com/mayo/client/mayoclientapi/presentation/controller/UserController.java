package com.mayo.client.mayoclientapi.presentation.controller;

import com.mayo.client.mayoclientapi.application.service.FCMService;
import com.mayo.client.mayoclientapi.application.service.UserService;
import com.mayo.client.mayoclientapi.common.annotation.Authenticated;
import com.mayo.client.mayoclientapi.presentation.dto.request.*;
import com.mayo.client.mayoclientapi.presentation.dto.response.ReadStoreResponse;
import com.mayo.client.mayoclientapi.presentation.dto.response.ReadUserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "유저 API", description = "유저 API")
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Operation(summary = "유저 정보 조회", description = "유저 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "전체 가게 조회", content = @Content(schema = @Schema(implementation = ReadUserResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @Authenticated
    @GetMapping
    public ResponseEntity<ReadUserResponse> getUser(@RequestAttribute("uid") String uid) {
        return ResponseEntity.ok(userService.getUser(uid));
    }

    @Operation(summary = "좋아요 표시한 가게 조회", description = "좋아요 표시한 가게를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "좋아요 표시한 가게 조회", content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @Authenticated
    @GetMapping("/favorite-stores")
    public ResponseEntity<List<ReadStoreResponse>> favoriteStores(@RequestAttribute("uid") String uid) {
        return ResponseEntity.ok(userService.getUserFavoriteStores(uid));
    }

    @Operation(summary = "알림설정 표시한 가게 조회", description = "알림설정 표시한 가게를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "알림설정 표시한 가게 조회", content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @Authenticated
    @GetMapping("/notice-stores")
    public ResponseEntity<List<ReadStoreResponse>> noticeStores(@RequestAttribute("uid") String uid) {
        return ResponseEntity.ok(userService.getUserNoticeStores(uid));
    }

    @Operation(summary = "유저 회원 가입", description = "유저 회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 가입 성공", content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @Authenticated
    @PostMapping
    public ResponseEntity<Void> createUser(@RequestAttribute("uid") String uid, @RequestBody CreateUserRequest request) {
        userService.createUser(request, uid);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "알림 설정한 가게 업데이트", description = "알림 설정한 가게를 업데이트합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "알림 설정한 가게를 업데이트합니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @Authenticated
    @PutMapping("/notice-store")
    public ResponseEntity<Void> updateNoticeStore(@RequestAttribute("uid") String uid, @RequestBody UpdateNoticeStoreRequest request) {
        userService.updateNoticeStore(request, uid);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/notice-store/test")
    public ResponseEntity<Void> updateNoticeStore(@RequestBody UpdateNoticeStoreRequest request) {
        userService.updateNoticeStore(request, "1ttsC591higWm8sVaZ887acfaTV2");
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "좋아요 표시한 가게 업데이트", description = "좋아요 표시한 가게를 업데이트합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "좋아요 표시한 가게를 업데이트합니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @Authenticated
    @PutMapping("/favorite-store")
    public ResponseEntity<Void> updateFavoriteStore(@RequestAttribute("uid") String uid, @RequestBody UpdateFavoriteStoreRequest request) {
        userService.updateFavoriteStore(request, uid);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴를 진행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 탈퇴 성공."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @Authenticated
    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestAttribute("uid") String uid) {
        userService.deleteUser(uid);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "닉네임 변경", description = "닉네임을 업데이트합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "닉네임을 업데이트합니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @Authenticated
    @PutMapping("/nickname")
    public ResponseEntity<Void> updateNickName(@RequestAttribute("uid") String uid, @RequestBody UpdateNickNameRequest request) {
        userService.updateNickName(uid, request.nickName());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "이메일 업데이트", description = "이메일을 업데이트합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이메일을 업데이트합니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @Authenticated
    @PutMapping("/email")
    public ResponseEntity<Void> updateEmail(@RequestAttribute("uid") String uid, @RequestBody UpdateEmailRequest request) {
        userService.updateEmail(uid, request.email());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "마케팅 수신동의 업데이트", description = "마케팅 수신동의를 업데이트합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "마케팅 수신동의을 업데이트합니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @Authenticated
    @PutMapping("/userImage")
    public ResponseEntity<Void> updateUserImage(@RequestAttribute("uid") String uid, @RequestParam MultipartFile file) {
        userService.updateUserImage(uid, file);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "마케팅 수신동의 업데이트", description = "마케팅 수신동의를 업데이트합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "마케팅 수신동의을 업데이트합니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @Authenticated
    @PutMapping("/agreeMarketing")
    public ResponseEntity<Void> updateAgreeMarketing(@RequestAttribute("uid") String uid, @RequestBody UpdateAgreeMarketingRequest request) {
        userService.updateAgreeMarketing(uid, request.agreeMarketing());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "fcm token 생성", description = "fcm token을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "fcm token을 생성합니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @Authenticated
    @PostMapping("/fcm")
    public ResponseEntity<Void> createFcmToken(@RequestAttribute("uid") String uid, @RequestBody CreateFCMTokenRequest request) {
        userService.createFCMToken(uid, request.fcmToken(), request.deviceType());
        return ResponseEntity.noContent().build();
    }
}
