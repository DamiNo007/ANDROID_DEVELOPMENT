package com.example.social_network;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

public class News implements Parcelable, Cloneable{

    private int id;
    private int avaURL;

    private int contentImgURL;
    private String author;
    private String date;
    private String text;
    private boolean liked;
    private int likesNo;
    private int commentsNo;
    private int viewsNo;

    public News() {
    }

    public News(int id, int avaURL, int contentImgURL, String author, String date, String text, boolean liked, int likesNo, int commentsNo, int viewsNo) {
        this.id = id;
        this.avaURL = avaURL;
        this.contentImgURL = contentImgURL;
        this.author = author;
        this.date = date;
        this.text = text;
        this.liked = liked;
        this.likesNo = likesNo;
        this.commentsNo = commentsNo;
        this.viewsNo = viewsNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAvaURL() {
        return avaURL;
    }

    public void setAvaURL(int avaURL) {
        this.avaURL = avaURL;
    }


    public int getContentImgURL() {
        return contentImgURL;
    }

    public void setContentImgURL(int contentImgURL) {
        this.contentImgURL = contentImgURL;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public int getLikesNo() {
        return likesNo;
    }

    public void setLikesNo(int likesNo) {
        this.likesNo = likesNo;
    }

    public int getCommentsNo() {
        return commentsNo;
    }

    public void setCommentsNo(int commentsNo) {
        this.commentsNo = commentsNo;
    }

    public int getViewsNo() {
        return viewsNo;
    }

    public void setViewsNo(int viewsNo) {
        this.viewsNo = viewsNo;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }


    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", avaURL=" + avaURL +
                ", contentImgURL=" + contentImgURL +
                ", author='" + author + '\'' +
                ", date='" + date + '\'' +
                ", text='" + text + '\'' +
                ", liked=" + liked +
                ", likesNo=" + likesNo +
                ", commentsNo=" + commentsNo +
                ", viewsNo=" + viewsNo +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.avaURL);
        dest.writeInt(this.contentImgURL);
        dest.writeString(this.author);
        dest.writeString(this.date);
        dest.writeString(this.text);
        dest.writeBoolean(this.liked);
        dest.writeInt(this.likesNo);
        dest.writeInt(commentsNo);
        dest.writeInt(viewsNo);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    protected News(Parcel in){
        this.id= in.readInt();
        this.avaURL = in.readInt();
        this.contentImgURL = in.readInt();
        this.author = in.readString();
        this.date = in.readString();
        this.text = in.readString();
        this.liked = in.readBoolean();
        this.likesNo = in.readInt();
        this.commentsNo = in.readInt();
        this.viewsNo = in.readInt();
    }

    public static final Creator<News> CREATOR = new Creator<News>(){
        @RequiresApi(api = Build.VERSION_CODES.Q)
        @Override
        public News createFromParcel(Parcel source){
            return new News(source);
        }

        @Override
        public News[] newArray(int size){
            return new News[size];
        }
    };
}
