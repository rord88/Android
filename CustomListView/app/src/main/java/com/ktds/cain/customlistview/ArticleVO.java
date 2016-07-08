package com.ktds.cain.customlistview;

import java.io.Serializable;

/**
 * Created by 206-013 on 2016-06-14.
 */
public class ArticleVO implements Serializable{
    private String subject;
    private String author;
    private String hitCount;

    public ArticleVO(String subject, String author, String hitCount) {
        this.subject = subject;
        this.author = author;
        this.hitCount = hitCount;
    }

    public String getSubject(){
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getHitCount() {
        return hitCount;
    }

    public void setHitCount(String hitCount) {
        this.hitCount = hitCount;
    }
}
