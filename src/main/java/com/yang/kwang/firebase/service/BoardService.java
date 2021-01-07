package com.yang.kwang.firebase.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.yang.kwang.firebase.model.Board;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class BoardService {

    private final Logger logger = LoggerFactory.getLogger(BoardService.class);
    public static final String COLLECTION_NAME = "member/DATA/BOARD";
    private FirebaseOptions option;
    private Firestore db;


    /*
        Board Document sub collection 조회
     */
    public List<Board> getBoardDocument() throws Exception {
        db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> apiFuture = db.collection("member/DATA/BOARD").get();
        List<QueryDocumentSnapshot> documents = apiFuture.get().getDocuments();

        logger.info("====> " + documents.size());

        Board board = new Board();
        List<Board> boards = new ArrayList<>();
        for (DocumentSnapshot doc : documents) {
            logger.info(doc.getId() + " ==> " + doc.toObject(Board.class));
            board = new Board();
            board.setDocId(doc.getId().toString());
            board.setUserId(doc.getData().get("userId").toString());
            board.setUserName(doc.getData().get("userName").toString());
            board.setTitle(doc.getData().get("title").toString());
            board.setContent(doc.getData().get("content").toString());
            board.setPassword(doc.getData().get("password").toString());
            board.setRegDtm(doc.getData().get("regDtm").toString());

            boards.add(board);
        }
        logger.info("boards ====> " + boards.toString());
        return boards;
    }

    /*
        글쓰기 등록
     */
    public String boardWriteForm(Board board) throws Exception{

        board.setRegDtm(getTime()); // 등록일시

        logger.info("board ====> " + board.toString());
        db = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> apiFuture = db.collection(COLLECTION_NAME)
                .document().set(board);

        return apiFuture.get().getUpdateTime().toString();
    }

    /*
         글쓰기 내용 조회(Document ID로 조회
     */
    public Board getBoardDocument(String docId) throws Exception{
        logger.info("getBoardDocument ====> ");
        db = FirestoreClient.getFirestore();
        DocumentReference documentReference = db.collection(COLLECTION_NAME).document(docId);
        ApiFuture<DocumentSnapshot> apiFuture = documentReference.get();
        DocumentSnapshot documentSnapshot = apiFuture.get();
        Board board = new Board();
        if(documentSnapshot.exists()){
            board = documentSnapshot.toObject(Board.class);

            board.setDocId(docId);
        }

        logger.info("board ====> " + board.toString());
        return board;
    }
    /*
        등록 내용 수정 (Document 내용 수정)
     */
    public String updateBoardDocument(Board board) throws Exception{
        board.setRegDtm(getTime()); // 수정일시
        db = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> apiFuture = db.collection(COLLECTION_NAME)
                .document(board.getDocId())
                .set(board);
        return apiFuture.get().getUpdateTime().toString();
    }

    public String getTime(){
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String now = (new SimpleDateFormat("yyyy.MM.dd HH:mm").format(date));

        return now;
    }

}
