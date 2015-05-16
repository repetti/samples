package org.repetti.samples.yandex.disk;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;

/**
 * Created on 16/05/15.
 */
public class OAuth {

    /**
     * Generates an URL, using which use can log in and get a token or a code
     *
     * @param client_id     client_id from an application
     * @param response_type 'token', 'code'
     * @return url for the request
     * @throws OAuthSystemException
     */
    public static String getUrl(String client_id, String response_type) throws OAuthSystemException {
        OAuthClientRequest request = OAuthClientRequest
                .authorizationLocation("https://oauth.yandex.ru/authorize")
                .setClientId(client_id)
                .setResponseType(response_type)
//                .setRedirectURI("https://oauth.yandex.ru/verification_code")
                .buildQueryMessage();

        return request.getLocationUri();
    }

    /**
     * Transforms obtained code to a valid token
     *
     * @param client_id     application id
     * @param client_secret application password
     * @param code
     * @return
     * @throws OAuthSystemException
     * @throws OAuthProblemException
     */
    public static OAuthJSONAccessTokenResponse getToken(String client_id, String client_secret, String code) throws OAuthSystemException, OAuthProblemException {
        OAuthClientRequest request = OAuthClientRequest
                .tokenLocation("https://oauth.yandex.ru/token")
                .setGrantType(GrantType.AUTHORIZATION_CODE)
                .setClientId(client_id)
                .setClientSecret(client_secret)
//                .setRedirectURI("https://oauth.yandex.ru/verification_code")
                .setCode(code)
                        // we have to build body instead of query, for our POST request
                .buildBodyMessage();

        System.out.println(request.getLocationUri());
        System.out.println(request.getBody());

        //create OAuth client that uses custom http client under the hood
        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());

        return oAuthClient.accessToken(request,
                org.apache.oltu.oauth2.common.OAuth.HttpMethod.POST);
    }
}
