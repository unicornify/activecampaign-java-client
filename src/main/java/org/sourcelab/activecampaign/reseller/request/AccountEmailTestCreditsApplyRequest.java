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

package org.sourcelab.activecampaign.reseller.request;

import org.sourcelab.activecampaign.JacksonFactory;
import org.sourcelab.activecampaign.reseller.response.AccountEmailTestCreditsApplyResponse;
import org.sourcelab.http.rest.request.RequestMethod;

import java.io.IOException;

/**
 * Add reseller's email testing credits to an account.
 */
public class AccountEmailTestCreditsApplyRequest extends AbstractRequest<AccountEmailTestCreditsApplyRequest, AccountEmailTestCreditsApplyResponse> {

    public AccountEmailTestCreditsApplyRequest() {
        super("account_emailtest_credits_apply");
    }

    public AccountEmailTestCreditsApplyRequest withAccount(final String account) {
        return setParam("account", account);
    }

    public AccountEmailTestCreditsApplyRequest withCredits(final int numberOfCredits) {
        return setParam("credits", Integer.toString(numberOfCredits));
    }

    @Override
    public AccountEmailTestCreditsApplyResponse parseResponse(final String response) throws IOException {
        return JacksonFactory.newInstance().readValue(response, AccountEmailTestCreditsApplyResponse.class);
    }

    @Override
    public RequestMethod getRequestMethod() {
        return RequestMethod.POST;
    }
}