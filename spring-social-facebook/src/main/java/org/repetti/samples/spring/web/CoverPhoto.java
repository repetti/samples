package org.repetti.samples.spring.web;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created on 17/05/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CoverPhoto extends org.springframework.social.facebook.api.CoverPhoto {

    @Override
    public String getId() {
        return super.getId();
    }

    @Override
    public String getSource() {
        return super.getSource();
    }

    @Override
    public int getOffsetX() {
        return super.getOffsetX();
    }

    @Override
    public int getOffsetY() {
        return super.getOffsetY();
    }

    @Override
    public String toString() {
        return "CoverPhoto{" +
                "id='" + getId() + '\'' +
                ", source='" + getSource() + '\'' +
                '}';
    }
}
