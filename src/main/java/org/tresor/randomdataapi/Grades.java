package org.tresor.randomdataapi;

import org.bson.Document;

import java.util.List;

public class Grades {

    private double student_id;
    private double class_id;
    private List<Document> scores;

    public Grades(double student_id, double class_id, List<Document> scores){
        this.student_id = student_id;
        this.class_id = class_id;
        this.scores = scores;
    }

    public Grades(){}

    public double getStudent_id() {
        return student_id;
    }

    public double getClass_id() {
        return class_id;
    }

    public List<Document> getScores() {
        return scores;
    }
}
