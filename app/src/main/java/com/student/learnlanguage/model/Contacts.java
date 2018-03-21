package com.student.learnlanguage.model;

public class Contacts {
    private String quiz_name,total;

    public Contacts( String quiz_name, String total) {
         this.quiz_name = quiz_name;
        this.total = total;
    }


    public String getQuiz_name() {
        return quiz_name;
    }

    public String getTotal() {
        return total;
    }
}
