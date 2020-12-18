package org.vkbot.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "news")
public class News
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Timestamp date;

    private String head;

    private String link;

    @Type(type = "text")
    private String content;

    public News() {}

    public News(Timestamp date, String head, String link, String content) {
        this.date = date;
        this.head = head;
        this.link = link;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public void setDate(String content) {
        this.content = content;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.head = link;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return String.format("%s[%tc]: %s", id, date, head);
    }
}
