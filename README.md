---
마요 Manager API 서버 입니다.
---

> 개발 기간 : 2025.12.4 ~ 2024.12.11 <br/>
> - 마감할인 플랫폼 mayo(마감해요)의 클라이언트용 api 서버입니다. <br/>

## 개발 환경
- 개발도구: Intellij IDEA - Ultimate
- 언어: Java 17<br>
- 빌드도구: Gradle
- 개발
  - Spring Framework: 6.1.1
  - Spring Boot: 3.4.0

- 데이터베이스
  - Firestore Database
- Storage
  - Firebase Storage
- AWS
  - EC2
- 기타
  - FCM
  - NGINX
  - Caffeine (in-memory-cache)
  - Prometheus / Grafana
  <br/>

## 아키텍처

<img width="700" alt="스크린샷 2025-03-25 오후 2 43 28" src="https://github.com/user-attachments/assets/ab8cf055-d155-4b42-a8f3-f9641915868c" />

## 기능목록

- [x] 인가
    - [x] Firebase Authentication으로 Interceptor를 통해 인가처리를 진행합니다.<br>
  

- [x] 트랜잭션
    - [x] FirestoreTransactional 어노테이션으로 transaction AOP를 생성하여 처리합니다.<br>
  

- [x] 게시판
    - [x] 약관 및 정책의 모든 글을 제공합니다.
    - [x] 공지사항 게시판의 모든 글을 제공합니다.
    - [x] FAQ 관련 글을 제공합니다.
    - [x] 게시판의 상세 정보를 제공합니다.<br>
  

- [x] 아이템 / 배너
    - [x] storeId 값으로 해당 가게의 item들을 모두 불러옵니다. 
    - [x] 배너를 제공합니다. <br>
  

- [x] 예약
    - [x] 장바구니를 통해 예약을 진행합니다.
    - [x] 유저 정보를 통해 해당 유저의 예약 내역을 불러옵니다.<br>
  

- [x] 장바구니
  - [x] 장바구니를 생성합니다.
  - [x] 장바구니를 삭제합니다.<br>
  

- [x] 가게
    - [x] 할인중인 가게를 제공합니다.
    - [x] 카테고리 별 가게를 제공합니다.
    - [x] 할인 중인 가게 중 랜덤값으로 제공합니다.
    - [x] 가게 상세 정보를 제공합니다.
    - [x] 최근 주문한 가게를 제공합니다.
  

- [x] 알림
    - [x] 예약한 주문의 상태 변경 시 알림을 발송합니다.
    - [x] 알림설정한 가게 오픈 시 알림을 제공합니다.<br>
  

- [x] 유저
  - [x] 회원가입 기능을 제공합니다.
  - [x] 유저 정보를 변경합니다.
  - [x] 유저 탈퇴를 제공합니다.
  - [x] 좋아요 및 알림설정한 가게 표시를 제공합니다.