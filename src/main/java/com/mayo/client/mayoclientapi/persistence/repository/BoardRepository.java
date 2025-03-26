package com.mayo.client.mayoclientapi.persistence.repository;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.mayo.client.mayoclientapi.common.annotation.FirestoreTransactional;
import com.mayo.client.mayoclientapi.common.exception.ApplicationException;
import com.mayo.client.mayoclientapi.common.exception.payload.ErrorStatus;
import com.mayo.client.mayoclientapi.persistence.domain.Board;
import com.mayo.client.mayoclientapi.persistence.domain.type.BoardCategory;
import com.mayo.client.mayoclientapi.persistence.domain.type.FAQType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Repository
@RequiredArgsConstructor
public class BoardRepository {

    private final Firestore firestore;

    public List<Board> getTermsBoard() {

        List<Board> boards = new ArrayList<>();

        CollectionReference boardRef = firestore.collection("board");
        Query query = boardRef.whereEqualTo("category", BoardCategory.TERMSDETAIL.getState());
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = query.get();

        QuerySnapshot querySnapshot = null;

        try {
            querySnapshot = querySnapshotApiFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new ApplicationException(ErrorStatus.toErrorStatus("약관 및 정책을 가져오는데 에러가 발생하였습니다.", 400, LocalDateTime.now()));
        }

        for (QueryDocumentSnapshot boardDocument : querySnapshot.getDocuments()) {
            Board board = fromDocument(boardDocument);
            boards.add(board);
        }

        return boards;
    }


    public List<Board> getNoticeBoard() {

        List<Board> boards = new ArrayList<>();

        CollectionReference boardRef = firestore.collection("board");
        Query query = boardRef.whereEqualTo("category", BoardCategory.NOTICE.getState());
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = query.get();
        QuerySnapshot querySnapshot = null;

        try {
            querySnapshot = querySnapshotApiFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new ApplicationException(ErrorStatus.toErrorStatus("공지사항을 가져오는 도중 에러가 발생하였습니다.", 400, LocalDateTime.now()));
        }

        for (QueryDocumentSnapshot boardDocument : querySnapshot.getDocuments()) {
            Board Board = fromDocument(boardDocument);
            boards.add(Board);
        }
        return boards;
    }

    public List<Board> getEventBoard() {

        List<Board> boards = new ArrayList<>();

        CollectionReference boardRef = firestore.collection("board");
        Query query = boardRef
                .whereEqualTo("category", BoardCategory.EVENT.getState())
                .orderBy("write_time", Query.Direction.DESCENDING);
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = query.get();
        QuerySnapshot querySnapshot = null;

        try {
            querySnapshot = querySnapshotApiFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new ApplicationException(ErrorStatus.toErrorStatus("이벤트를 가져오는 도중 에러가 발생하였습니다.", 400, LocalDateTime.now()));
        }

        for (QueryDocumentSnapshot boardDocument : querySnapshot.getDocuments()) {
            Board Board = fromDocument(boardDocument);
            boards.add(Board);
        }
        return boards;
    }

    public List<Board> findUpdateInfo() {

        List<Board> boards = new ArrayList<>();

        CollectionReference boardRef = firestore.collection("board");
        Query query = boardRef
                .whereEqualTo("category", FAQType.UPDATE_INFO.getCode());
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = query.get();
        QuerySnapshot querySnapshot = null;

        try {
            querySnapshot = querySnapshotApiFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new ApplicationException(ErrorStatus.toErrorStatus("정보 수정 가져오는 도중 에러가 발생하였습니다.", 400, LocalDateTime.now()));
        }

        for (QueryDocumentSnapshot boardDocument : querySnapshot.getDocuments()) {
            Board Board = fromDocument(boardDocument);
            boards.add(Board);
        }
        return boards;
    }

    public List<Board> findDeleteAccount() {

        List<Board> boards = new ArrayList<>();

        CollectionReference boardRef = firestore.collection("board");
        Query query = boardRef
                .whereEqualTo("category", FAQType.DELETE_ACCOUNT.getCode());
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = query.get();
        QuerySnapshot querySnapshot = null;

        try {
            querySnapshot = querySnapshotApiFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new ApplicationException(ErrorStatus.toErrorStatus("회원 탈퇴를 가져오는 도중 에러가 발생하였습니다.", 400, LocalDateTime.now()));
        }

        for (QueryDocumentSnapshot boardDocument : querySnapshot.getDocuments()) {
            Board Board = fromDocument(boardDocument);
            boards.add(Board);
        }
        return boards;
    }

    public List<Board> findStorePickUp() {

        List<Board> boards = new ArrayList<>();

        CollectionReference boardRef = firestore.collection("board");
        Query query = boardRef
                .whereEqualTo("category", FAQType.STORE_PICKUP.getCode());
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = query.get();
        QuerySnapshot querySnapshot = null;

        try {
            querySnapshot = querySnapshotApiFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new ApplicationException(ErrorStatus.toErrorStatus("매장 픽업 가져오는 도중 에러가 발생하였습니다.", 400, LocalDateTime.now()));
        }

        for (QueryDocumentSnapshot boardDocument : querySnapshot.getDocuments()) {
            Board Board = fromDocument(boardDocument);
            boards.add(Board);
        }
        return boards;
    }

    public List<Board> findOrderHistory() {

        List<Board> boards = new ArrayList<>();

        CollectionReference boardRef = firestore.collection("board");
        Query query = boardRef
                .whereEqualTo("category", FAQType.ORDER_HISTORY.getCode());
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = query.get();
        QuerySnapshot querySnapshot = null;

        try {
            querySnapshot = querySnapshotApiFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new ApplicationException(ErrorStatus.toErrorStatus("주문 내역 FAQ 가져오는 도중 에러가 발생하였습니다.", 400, LocalDateTime.now()));
        }

        for (QueryDocumentSnapshot boardDocument : querySnapshot.getDocuments()) {
            Board Board = fromDocument(boardDocument);
            boards.add(Board);
        }
        return boards;
    }

    public List<Board> findOrderNotification() {

        List<Board> boards = new ArrayList<>();

        CollectionReference boardRef = firestore.collection("board");
        Query query = boardRef
                .whereEqualTo("category", FAQType.ORDER_NOTIFICATION.getCode());
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = query.get();
        QuerySnapshot querySnapshot = null;

        try {
            querySnapshot = querySnapshotApiFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new ApplicationException(ErrorStatus.toErrorStatus("주문 알림 FAQ를 가져오는 도중 에러가 발생하였습니다.", 400, LocalDateTime.now()));
        }

        for (QueryDocumentSnapshot boardDocument : querySnapshot.getDocuments()) {
            Board Board = fromDocument(boardDocument);
            boards.add(Board);
        }
        return boards;
    }

    public List<Board> findMainFeature() {

        List<Board> boards = new ArrayList<>();

        CollectionReference boardRef = firestore.collection("board");
        Query query = boardRef
                .whereEqualTo("category", FAQType.MAIN_FEATURES.getCode());
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = query.get();
        QuerySnapshot querySnapshot = null;

        try {
            querySnapshot = querySnapshotApiFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new ApplicationException(ErrorStatus.toErrorStatus("주요 기능 FAQ 가져오는 도중 에러가 발생하였습니다.", 400, LocalDateTime.now()));
        }

        for (QueryDocumentSnapshot boardDocument : querySnapshot.getDocuments()) {
            Board Board = fromDocument(boardDocument);
            boards.add(Board);
        }
        return boards;
    }

    public List<Board> findHowToUse() {

        List<Board> boards = new ArrayList<>();

        CollectionReference boardRef = firestore.collection("board");
        Query query = boardRef
                .whereEqualTo("category", FAQType.HOW_TO_USE.getCode())
                .orderBy("write_time", Query.Direction.DESCENDING);
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = query.get();
        QuerySnapshot querySnapshot = null;

        try {
            querySnapshot = querySnapshotApiFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new ApplicationException(ErrorStatus.toErrorStatus("정보 수정 가져오는 도중 에러가 발생하였습니다.", 400, LocalDateTime.now()));
        }

        for (QueryDocumentSnapshot boardDocument : querySnapshot.getDocuments()) {
            Board Board = fromDocument(boardDocument);
            boards.add(Board);
        }
        return boards;
    }

    public List<Board> findPayment() {

        List<Board> boards = new ArrayList<>();

        CollectionReference boardRef = firestore.collection("board");
        Query query = boardRef
                .whereEqualTo("category", FAQType.PAYMENT.getCode());
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = query.get();
        QuerySnapshot querySnapshot = null;

        try {
            querySnapshot = querySnapshotApiFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new ApplicationException(ErrorStatus.toErrorStatus("결제 FAQ를 가져오는 도중 에러가 발생하였습니다.", 400, LocalDateTime.now()));
        }

        for (QueryDocumentSnapshot boardDocument : querySnapshot.getDocuments()) {
            Board Board = fromDocument(boardDocument);
            boards.add(Board);
        }
        return boards;
    }

    public List<Board> findCoupon() {

        List<Board> boards = new ArrayList<>();

        CollectionReference boardRef = firestore.collection("board");
        Query query = boardRef
                .whereEqualTo("category", FAQType.COUPON.getCode());
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = query.get();
        QuerySnapshot querySnapshot = null;

        try {
            querySnapshot = querySnapshotApiFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new ApplicationException(ErrorStatus.toErrorStatus("쿠폰 FAQ를 가져오는 도중 에러가 발생하였습니다.", 400, LocalDateTime.now()));
        }

        for (QueryDocumentSnapshot boardDocument : querySnapshot.getDocuments()) {
            Board Board = fromDocument(boardDocument);
            boards.add(Board);
        }
        return boards;
    }

    public List<Board> findEventFAQ() {

        List<Board> boards = new ArrayList<>();

        CollectionReference boardRef = firestore.collection("board");
        Query query = boardRef
                .whereEqualTo("category", FAQType.EVENT.getCode())
                .orderBy("write_time", Query.Direction.DESCENDING);
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = query.get();
        QuerySnapshot querySnapshot = null;

        try {
            querySnapshot = querySnapshotApiFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new ApplicationException(ErrorStatus.toErrorStatus("이벤트 FAQ를 가져오는 도중 에러가 발생하였습니다.", 400, LocalDateTime.now()));
        }

        for (QueryDocumentSnapshot boardDocument : querySnapshot.getDocuments()) {
            Board Board = fromDocument(boardDocument);
            boards.add(Board);
        }
        return boards;
    }

    public List<Board> findETC() {

        List<Board> boards = new ArrayList<>();

        CollectionReference boardRef = firestore.collection("board");
        Query query = boardRef
                .whereEqualTo("category", FAQType.ETC.getCode())
                .orderBy("write_time", Query.Direction.DESCENDING);
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = query.get();
        QuerySnapshot querySnapshot = null;

        try {
            querySnapshot = querySnapshotApiFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new ApplicationException(ErrorStatus.toErrorStatus("기타 FAQ를 가져오는 도중 에러가 발생하였습니다.", 400, LocalDateTime.now()));
        }

        for (QueryDocumentSnapshot boardDocument : querySnapshot.getDocuments()) {
            Board Board = fromDocument(boardDocument);
            boards.add(Board);
        }
        return boards;
    }

    public Optional<Board> getBoardById(String boardId) {

        DocumentReference documentReference = firestore.collection("board").document(boardId);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = null;

        try {
            document = future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new ApplicationException(ErrorStatus.toErrorStatus("해당 게시글을 가져오는데 오류가 발생하였습니다.", 400, LocalDateTime.now()));
        }

        return Optional.ofNullable(fromDocument(document));
    }

    private Board fromDocument(DocumentSnapshot document) {
        return Board.builder()
                .boardId(document.getId())
                .title(document.getString("title"))
                .content(document.getString("content"))
                .image(document.getString("image"))
                .writer(document.getString("writer"))
                .writeTime(document.getTimestamp("write_time"))
                .build();
    }
}
