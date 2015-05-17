package org.repetti.samples.spring.web;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created on 17/05/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    protected String id;
    protected String about;
    protected String birthday;
    protected String email;
    protected String first_name;
    protected String gender;
    protected String last_name;
    protected String link;
    protected MyCoverPhoto cover;
    protected String updated_time;
    protected float timezone;
    protected List<String> devices;

    public String getId() {
        return id;
    }

    public String getAbout() {
        return about;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getEmail() {
        return email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getGender() {
        return gender;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getLink() {
        return link;
    }

    public MyCoverPhoto getCover() {
        return cover;
    }

    public String getUpdated_time() {
        return updated_time;
    }

    public float getTimezone() {
        return timezone;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", about='" + about + '\'' +
                ", birthday='" + birthday + '\'' +
                ", email='" + email + '\'' +
                ", first_name='" + first_name + '\'' +
                ", gender='" + gender + '\'' +
                ", last_name='" + last_name + '\'' +
                ", link='" + link + '\'' +
                ", cover=" + cover +
                ", updated_time='" + updated_time + '\'' +
                ", timezone=" + timezone +
                ", devices=" + devices +
                '}';
    }

    public List<String> getDevices() {
        return devices;
    }
}
