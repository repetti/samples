package org.repetti.samples.spring.web;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created on 17/05/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MyCoverPhoto {

    protected String id;
    protected int offsetX;
    protected int offsetY;
    protected String source;

    public String getId() {
        return id;
    }

    public String getSource() {
        return source;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    @Override
    public String toString() {
        return "MyCoverPhoto{" +
                "id='" + id + '\'' +
                ", offsetX=" + offsetX +
                ", offsetY=" + offsetY +
                ", source='" + source + '\'' +
                '}';
    }
}
