package com.yang.kwang.firebase.service;

import com.google.api.core.ApiFunction;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.yang.kwang.firebase.model.Member;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TestService {

    private final Logger logger = LoggerFactory.getLogger(TestService.class);
    public static final String COLLECTION_NAME = "member";
    private FirebaseOptions option;
    private Firestore db;


    /*
        ID값으로 데이터 조회
     */
    public Member getMemberDetail(String id) throws Exception{
        db = FirestoreClient.getFirestore();
        DocumentReference documentReference = db.collection(COLLECTION_NAME).document(id);
        ApiFuture<DocumentSnapshot> apiFuture = documentReference.get();
        DocumentSnapshot documentSnapshot = apiFuture.get();
        Member member = null;
        if(documentSnapshot.exists()){
            member = documentSnapshot.toObject(Member.class);
            return member;
        }else{
            return null;
        }
    }

    /*
        등록
     */
    public String insertMember(Member member) throws Exception{
        db = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> apiFuture = db.collection(COLLECTION_NAME)
                .document(member.getDocId())
                .set(member);
        return apiFuture.get().getUpdateTime().toString();

    }

    /*
        수정
     */
    public String updateMember(Member member) throws Exception{
        db = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> apiFuture = db.collection(COLLECTION_NAME)
                .document(member.getDocId())
                .set(member);
        return apiFuture.get().getUpdateTime().toString();
    }

    /*
        삭제
     */
    public String deleteMember(String id) throws Exception{
        db = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> apiFuture = db.collection(COLLECTION_NAME)
                .document(id).delete();
        return "Document id : " + id + " delete(" + apiFuture.get().getUpdateTime().toString() + ")";
    }

    /*
        DOCUMENT 조회
     */
    public String getCollection() throws Exception{
        db = FirestoreClient.getFirestore();
        db.collection(COLLECTION_NAME).addSnapshotListener((target,exception) ->{
            logger.info(" - select start - ");
            target.forEach(item->{
                logger.info("primary id : " + item.getId() + " || value : " + item.getData());
            });
            logger.info(" - select end - ");
        });
        return "ok!!!";
    }

    /*
        SUB-COLLECTION 조회
     */
    public String getSubCollection() throws Exception{
        db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> apiFuture = db.collection(COLLECTION_NAME)
                .whereEqualTo("Board",true).get();
        List<QueryDocumentSnapshot> documents = apiFuture.get().getDocuments();

        logger.info("====> " + documents.size());

        for(DocumentSnapshot doc : documents){
            logger.info(doc.getId() + " ==> " + doc.toObject(Member.class));
        }

        return "ok!!";
    }
}
