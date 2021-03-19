/**
 * Copyright 2021 Unicornify https://github.com/unicornify/activecampaign-java-client
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit
 * persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.sourcelab.activecampaign.apiv3.request.ecomCustomer;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.sourcelab.activecampaign.JacksonFactory;
import org.sourcelab.activecampaign.apiv3.response.ecomCustomer.EcomCustomerResponse;
import org.sourcelab.http.rest.request.Request;
import org.sourcelab.http.rest.request.RequestMethod;
import org.sourcelab.http.rest.request.body.RequestBodyContent;
import org.sourcelab.http.rest.request.body.StringBodyContent;

import java.io.IOException;

public class EcomCustomerPostRequest implements Request<EcomCustomerResponse> {
    private final EcomCustomer ecomCustomer;

    public EcomCustomerPostRequest(EcomCustomer ecomCustomer) {
        this.ecomCustomer = ecomCustomer;
    }

    @Override
    public String getApiEndpoint() {
        return "api/3/ecomCustomers";
    }

    @Override
    public RequestMethod getRequestMethod() {
        return RequestMethod.POST;
    }

    @Override
    public RequestBodyContent getRequestBody() {
        try {
            return new StringBodyContent(
                    JacksonFactory.newInstance().writeValueAsString(ecomCustomer)
            );
        } catch (final JsonProcessingException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public EcomCustomerResponse parseResponse(final String response) throws IOException {
        return JacksonFactory.newInstance().readValue(response, EcomCustomerResponse.class);
    }
}