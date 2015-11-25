package fh_ku.taskmaster.models;

/**
 * Created by Benedikt on 25.11.2015.
 */
public class Tag {

    protected int id;
    protected String tag;

    public int getId() {
        return id;
    }

    public Tag setId(int id) {
        this.id = id;
        return this;
    }

    public String getTag() {
        return tag;
    }

    public Tag setTag(String tag) {
        this.tag = tag;
        return this;
    }
}
