package org.sourcelab.activecampaign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sourcelab.activecampaign.exception.InvalidCredentialsException;
import org.sourcelab.activecampaign.request.LoginRequest;
import org.sourcelab.http.rest.HttpClientRestClient;
import org.sourcelab.http.rest.RestClient;
import org.sourcelab.http.rest.RestResponse;
import org.sourcelab.http.rest.exceptions.InvalidRequestException;
import org.sourcelab.http.rest.exceptions.ResourceNotFoundException;
import org.sourcelab.http.rest.exceptions.UnauthorizedRequestException;
import org.sourcelab.http.rest.request.Request;
import org.sourcelab.http.rest.request.RequestMethod;

import java.io.IOException;

/**
 * ActiveCampaign API client.
 */
public class ActiveCampaignClient {
    private static final Logger logger = LoggerFactory.getLogger(ActiveCampaignClient.class);

    /**
     * Our API Configuration.
     */
    private final ApiConfig apiConfig;

    /**
     * Underlying RestClient to use.
     */
    private final RestClient restClient;

    /**
     * Internal State flag.
     */
    private boolean isInitialized = false;

    /**
     * Constructor.
     * @param apiConfig API Configuration properties.
     */
    public ActiveCampaignClient(final ApiConfig apiConfig) {
        this.apiConfig = apiConfig;
        this.restClient = new HttpClientRestClient();
    }

    /**
     * Method to validate if the APi credentials provided are valid.
     *
     * @return true if the credentials are accepted, false if not.
     */
    public boolean loginTest() {
        try {
            return submitRequest(new LoginRequest());
        } catch (final InvalidCredentialsException exception) {
            return false;
        }
    }

    private <T> T submitRequest(final Request<T> request) {
        // Submit request
        final RestResponse restResponse = getRestClient().submitRequest(request);
        final int responseCode = restResponse.getHttpCode();
        String responseStr = restResponse.getResponseStr();

        // If we have a valid response
        logger.debug("Response: {}", restResponse);

        // Check for invalid http status codes
        if (responseCode >= 200 && responseCode < 300) {
            // These response codes have no values
            if ((responseCode == 204 || responseCode == 205) && responseStr == null) {
                // Avoid NPE
                responseStr = "";
            }

            try {
                return request.parseResponse(responseStr);
            } catch (final IOException exception) {
                throw new RuntimeException(exception.getMessage(), exception);
            }
        }

        // Server reject's client's authentication.
        if (responseCode == 403) {
            // Invalid credentials
            throw new InvalidCredentialsException("The ActiveCampaign API token and/or account name is not valid.", 403);
        } else if (responseCode == 404) {
            // Invalid end point requested.
            throw new ResourceNotFoundException("The requested resource does not exist. (" + request.getApiEndpoint() + ")");
        } else if (responseCode == 401) {
            // Typically thrown if proxy requires authentication
            // Throw contextual error msg based on if credentials are configured or not.
            String errorMsg;
            if (apiConfig.getBasicAuthUsername() == null) {
                errorMsg = "Server required authentication credentials but none were provided in client configuration.";
            } else {
                errorMsg = "Client authentication credentials (username=" + apiConfig.getBasicAuthUsername() + ") was rejected by server.";
            }
            errorMsg = errorMsg + " Server responded with: \"" + responseStr + "\"";
            throw new UnauthorizedRequestException(errorMsg, responseCode);
        }

//        // Attempt to parse error response
//        try {
//            final RequestErrorResponse errorResponse = JacksonFactory.newInstance().readValue(responseStr, RequestErrorResponse.class);
//            throw InvalidRequestException.factory(errorResponse);
//        } catch (final IOException e) {
//            // swallow
//        }
        throw new InvalidRequestException("Invalid response from server: " + responseStr, restResponse.getHttpCode());
    }

    private RestClient getRestClient() {
        // If we haven't initialized.
        if (!isInitialized) {
            // Call Init.
            restClient.init(apiConfig);

            // Flip state flag
            isInitialized = true;
        }

        // return our rest client.
        return restClient;
    }
}