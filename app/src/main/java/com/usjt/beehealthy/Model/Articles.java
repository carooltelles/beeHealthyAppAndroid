package com.usjt.beehealthy.Model;

import java.io.Serializable;

public class Articles implements Serializable {

    public Long idarticle;
    public Nutritionist nutritionist;
    public String title;
    public String text;

    public Articles (String title, String text, Nutritionist nutritionist) {
        this.idarticle = getIdarticle();
        this.title = title;
        this.text = text;
        this.nutritionist = nutritionist;
    }

    public Articles (Long idarticle, String title, String text, Nutritionist nutritionist) {
        this.idarticle = idarticle;
        this.title = title;
        this.text = text;
        this.nutritionist = nutritionist;
    }

    public Articles() {

    }


    public Long getIdarticle() {
        return idarticle;
    }
    public void setIdarticle(Long idarticle) {
        this.idarticle = idarticle;
    }
    public Nutritionist getNutritionist() {
        return nutritionist;
    }
    public void setNutritionist(Nutritionist nutritionist) {
        this.nutritionist = nutritionist;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

}
