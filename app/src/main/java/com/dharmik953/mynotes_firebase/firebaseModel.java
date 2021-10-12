package com.dharmik953.mynotes_firebase;

import java.io.Serializable;

public class  firebaseModel implements Serializable{

    public static String title;
    public static String content;

    public firebaseModel(){ }

    public firebaseModel(String title, String content) {
        firebaseModel.title = title;
        firebaseModel.content = content;
    }

    public static String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        firebaseModel.title = title;
    }

    public static String getContent() {
        return content;
    }

    public void setContent(String content) {
        firebaseModel.content = content;
    }
}
