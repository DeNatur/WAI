package com.erunetimeterror.wai.fragments;

public  class TileContent{
    String title=null;
    String content=null;
    String data=null;

    public TileContent(String title,String content,String data){
        this.title = title;
        this.content = content;
        this.data = data;
    }

    public TileContent(String title,String content){
        this.title = title;
        this.content = content;
    }
}
