/**
 * Copyright 2019 SourceLab.org https://github.com/SourceLabOrg/activecampaign-java-client
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

package org.sourcelab.activecampaign.client.request.account;

import org.sourcelab.activecampaign.client.response.account.Account;
import org.sourcelab.http.rest.request.Request;
import org.sourcelab.http.rest.request.RequestMethod;

import java.io.IOException;
import java.util.Objects;

/**
 * Represents an account delete request.
 */
public class AccountDeleteRequest implements Request<Boolean> {

    private final long id;

    public AccountDeleteRequest(final long id) {
        this.id = id;
    }

    public AccountDeleteRequest(final Account account) {
        Objects.requireNonNull(account);
        if (account.getId() == null) {
            throw new IllegalArgumentException("Account must have Id property set");
        }
        this.id = account.getId();
    }

    @Override
    public String getApiEndpoint() {
        return "accounts/" + id;
    }

    @Override
    public RequestMethod getRequestMethod() {
        return RequestMethod.DELETE;
    }

    @Override
    public Object getRequestBody() {
        return "";
    }

    @Override
    public Boolean parseResponse(final String response) throws IOException {
        return true;
    }
}