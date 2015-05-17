package org.repetti.samples.spring.web;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created on 17/05/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Page {

    protected String name;
    protected String about;
    protected String phone;
    protected String website;

    public String getName() {
        return name;
    }

    public String getAbout() {
        return about;
    }

    public String getPhone() {
        return phone;
    }

    public String getWebsite() {
        return website;
    }

    @Override
    public String toString() {
        return "Page{" +
                "name='" + name + '\'' +
                ", about='" + about + '\'' +
                ", phone='" + phone + '\'' +
                ", website='" + website + '\'' +
                '}';
    }
}