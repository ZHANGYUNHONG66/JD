package com.jerry.jingdong.entity;

import java.util.List;


/**
 * 评论封装类
 */
public class CommentBean {


    private String              response;

    private List<CommentEntity> comment;

    public void setResponse(String response) {
        this.response = response;
    }

    public void setComment(List<CommentEntity> comment) {
        this.comment = comment;
    }

    public String getResponse() {
        return response;
    }

    public List<CommentEntity> getComment() {
        return comment;
    }

    public static class CommentEntity {
        private String content;
        private int    time;
        private String title;
        private String username;

        public void setContent(String content) {
            this.content = content;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getContent() {
            return content;
        }

        public int getTime() {
            return time;
        }

        public String getTitle() {
            return title;
        }

        public String getUsername() {
            return username;
        }
    }
}
