package org.repetti.samples.yandex.disk;

import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Helper runnable class to obtain application token
 * <p/>
 * Created on 16/05/15.
 */
public class OAuthHelper {
    private static Properties getProperties() throws IOException {
        Properties props = new Properties();
//        props.load(OAuthHelper.class.getClassLoader().getResourceAsStream("secret.properties"));
        props.load(new FileInputStream(new File("yandex-disk/secret.properties")));
        return props;
    }

    public static void main(String[] args) throws IOException, OAuthSystemException, OAuthProblemException {
        Properties p = getProperties();

        // Go to this URL
        System.out.println(OAuth.getUrl(p.getProperty("client_id"), "code"));

        // For development needs use:
        // System.out.println(OAuth.getUrl(p.getProperty("client_id"), "token"));
        // it will return token

        //p.setProperty("code", "your code");

        // Second step, after getting code (and putting it to properties file)
        OAuthJSONAccessTokenResponse oAuthResponse = OAuth.getToken(
                p.getProperty("client_id"),
                p.getProperty("client_secret"),
                p.getProperty("code"));
        System.out.println("# response");
        System.out.println("token=" + oAuthResponse.getAccessToken());
        System.out.println("expiresIn=" + oAuthResponse.getExpiresIn());

    }
}
