package org.repetti.samples.spring.web;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * You cannot get list of user friends anymore:
 * https://stackoverflow.com/questions/23417356/facebook-graph-api-v2-0-me-friends-returns-empty-or-only-friends-who-also-u
 * <p/>
 * https://developers.facebook.com/tools/explorer/
 * https://developers.facebook.com/docs/graph-api
 * <p/>
 * Created on 17/05/15.
 */
public class MainSimple {
    private static final Logger log = LoggerFactory.getLogger(MainSimple.class);
    private static final RestTemplate restTemplate = new RestTemplate();

    public static void main(String args[]) throws IOException {
        Properties props = new Properties();
        props.load(new FileInputStream("data.ignore"));
        restTemplate.setErrorHandler(new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) throws IOException {
                return false;
            }

            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                System.out.println(response);
            }
        });

        final String pagename = props.getProperty("fb_pagename");
        final String username = props.getProperty("fb_username");
        final String userid = props.getProperty("fb_userid");
        final String access_token = props.getProperty("fb_access_token");
        log.info("username={}\ntoken={}", username, access_token);

        getPage(pagename);
        getUser(username);
        getUser(userid, access_token);
        getUserSimple(userid, access_token);
        getUserSimple(userid);
        findSimple(username, "user", access_token);
//        getfriends(userid, access_token);
    }

    public static Page getPage(String name) {
        Page page = restTemplate.getForObject("https://graph.facebook.com/{name}", Page.class, name);
        System.out.println(page.toString());
        System.out.println("Name:    " + page.getName());
        System.out.println("About:   " + page.getAbout());
        System.out.println("Phone:   " + page.getPhone());
        System.out.println("Website: " + page.getWebsite());
        return page;
    }

    public static User getUser(String name) {
        User user = restTemplate.getForObject("https://graph.facebook.com/{name}", User.class, name);
        System.out.println(user.toString());
        return user;
    }

    public static JsonNode getUserSimple(String name) {
        JsonNode user = restTemplate.getForObject("https://graph.facebook.com/{name}", JsonNode.class, name);
        System.out.println(user.toString());
        return user;
    }

    /**
     * https://developers.facebook.com/docs/graph-api/using-graph-api/v2.3
     *
     * @param query query
     * @param type  user, page, event, group...
     * @return query result
     */
    public static JsonNode findSimple(String query, String type, String token) {
        JsonNode result = restTemplate.getForObject("https://graph.facebook.com/search?q={name}&type={type}&debug=all&access_token={access-token}",
                JsonNode.class, query, type, token);
        System.out.println(result.toString());

        return result;
    }

    public static User getUser(String name, String access_token) {
        User user = restTemplate.getForObject(
                "https://graph.facebook.com/v2.3/{name}?access_token={access-token}&debug=all",
                User.class,
                name,
                access_token);
        System.out.println(user.toString());
        return user;
    }

    public static JsonNode getUserSimple(String name, String access_token) {
        JsonNode user = restTemplate.getForObject(
                "https://graph.facebook.com/v2.3/{name}?access_token={access-token}&debug=all",
                JsonNode.class,
                name,
                access_token);
        System.out.println(user.toString());
        return user;
    }

    public static JsonNode getfriends(String name, String access_token) {
        JsonNode node = restTemplate.getForObject(
                "https://graph.facebook.com/v2.3/{name}/taggable_friends?access_token={access-token}&debug=all",
                JsonNode.class,
                name,
                access_token);
        System.out.println(node);
        return node;
    }
}
