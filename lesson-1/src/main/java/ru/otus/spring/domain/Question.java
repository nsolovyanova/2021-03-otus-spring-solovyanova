package ru.otus.spring.domain;

import java.io.Serializable;

public class Question implements Serializable {
    private String id;
    private String text;
    private String answerA;
    private String answerB;
    private String answerC;
    private String answerD;

    public String getId()
    {
        return id;
    }
    public void setId(String id)
    {
        this.id = id;
    }
    public String getText()
    {
        return text;
    }
    public void setText(String text)
    {
        this.text = text;
    }
    public String getAnswerA()
    {
        return answerA;
    }
    public void setAnswerA(String answerA)
    {
        this.answerA = answerA;
    }
    public String getAnswerB()
    {
        return answerB;
    }
    public void setAnswerB(String answerB)
    {
        this.answerB = answerB;
    }
    public String getAnswerC()
    {
        return answerC;
    }
    public void setAnswerC(String answerC)
    {
        this.answerC = answerC;
    }
    public String getAnswerD()
    {
        return answerD;
    }
    public void setAnswerD(String answerD)
    {
        this.answerD = answerD;
    }

    @Override
    public String toString()
    {
        return "Question [id=" + id + ", text=" + text + ", answerA="
                + answerA + ", answerB=" + answerB + ", answerC=" + answerC
                + ", answerD=" + answerD + "]";
    }
}
