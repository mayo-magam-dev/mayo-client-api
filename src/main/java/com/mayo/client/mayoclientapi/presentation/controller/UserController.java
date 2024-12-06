package com.mayo.client.mayoclientapi.presentation.controller;

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
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @Authenticated
    @GetMapping
    public ResponseEntity<ReadUserResponse> getUser(HttpServletRequest req) {
        return ResponseEntity.ok(userService.getUser(req.getAttribute("uid").toString()));
    }

    @Operation(summary = "좋아요 표시한 가게 조회", description = "좋아요 표시한 가게를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "좋아요 표시한 가게 조회", content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @Authenticated
    @GetMapping("/favorite-stores")
    public ResponseEntity<List<ReadStoreResponse>> favoriteStores(HttpServletRequest req) {
        return ResponseEntity.ok(userService.getUserFavoriteStores(req.getAttribute("uid").toString()));
    }

    @Operation(summary = "알림설정 표시한 가게 조회", description = "알림설정 표시한 가게를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "알림설정 표시한 가게 조회", content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @Authenticated
    @GetMapping("/notice-stores")
    public ResponseEntity<List<ReadStoreResponse>> noticeStores(HttpServletRequest req) {
        return ResponseEntity.ok(userService.getUserNoticeStores(req.getAttribute("uid").toString()));
    }

    @Operation(summary = "유저 회원 가입", description = "유저 회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 가입 성공", content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @Authenticated
    @PostMapping
    public ResponseEntity<Void> createUser(HttpServletRequest req, @RequestBody CreateUserRequest request) {
        userService.createUser(request, req.getAttribute("uid").toString());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "알림 설정한 가게 업데이트", description = "알림 설정한 가게를 업데이트합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "알림 설정한 가게를 업데이트합니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @Authenticated
    @PutMapping("/notice-store")
    public ResponseEntity<Void> updateNoticeStore(HttpServletRequest req, @RequestBody UpdateNoticeStoreRequest request) {
        userService.updateNoticeStore(request, req.getAttribute("uid").toString());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "좋아요 표시한 가게 업데이트", description = "좋아요 표시한 가게를 업데이트합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "좋아요 표시한 가게를 업데이트합니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @Authenticated
    @PutMapping("/favorite-store")
    public ResponseEntity<Void> updatefavoriteStore(HttpServletRequest req, @RequestBody UpdateFavoriteStoreRequest request) {
        userService.updateFavoriteStore(request, req.getAttribute("uid").toString());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴를 진행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 탈퇴 성공."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @Authenticated
    @DeleteMapping
    public ResponseEntity<Void> deleteUser(HttpServletRequest req) {
        userService.deleteUser(req.getAttribute("uid").toString());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "닉네임 변경", description = "닉네임을 업데이트합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "닉네임을 업데이트합니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @Authenticated
    @PutMapping("/nickname")
    public ResponseEntity<Void> updateNickName(HttpServletRequest req, @RequestBody UpdateNickNameRequest request) {
        userService.updateNickName(req.getAttribute("uid").toString(), request.nickName());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "이메일 업데이트", description = "이메일을 업데이트합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이메일을 업데이트합니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @Authenticated
    @PutMapping("/email")
    public ResponseEntity<Void> updateEmail(HttpServletRequest req, @RequestBody UpdateEmailRequest request) {
        userService.updateEmail(req.getAttribute("uid").toString(), request.email());
        return ResponseEntity.noContent().build();
    }
}
