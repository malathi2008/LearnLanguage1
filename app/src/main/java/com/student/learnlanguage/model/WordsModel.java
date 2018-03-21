package com.student.learnlanguage.model;

public class WordsModel {
    private String firstword,secoundword;

    public WordsModel(String firstword, String secoundword){

       this.setFirstword(firstword);
       this.setSecoundword(secoundword);
    }

    public String getFirstword() {
        return firstword;
    }

    public void setFirstword(String firstword) {
        this.firstword = firstword;
    }

    public String getSecoundword() {
        return secoundword;
    }

    public void setSecoundword(String secoundword) {
        this.secoundword = secoundword;
    }
}
