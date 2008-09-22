/*
 * Copyright 2008 Sanjiv Sahayam
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.pinthura.example.filter.mail;

import com.googlecode.pinthura.filter.FilterChainImpl;
import com.googlecode.pinthura.filter.FilterLink;

import java.util.ArrayList;
import java.util.List;

public final class MailFilterRunner {

    private static final int ATTACHMENT_LIMIT = 50;

    public static void main(String[] args) {
        List<FilterLink<Mail,Boolean>> filters = new ArrayList<FilterLink<Mail, Boolean>>();
        MailManImpl mailMan = new MailManImpl();
        filters.add(new SpamFilter(new SpamDetectorImpl(), mailMan));
        filters.add(new LargeAttachmentFilter(ATTACHMENT_LIMIT, mailMan));
        filters.add(new InboxFilter(mailMan));

        FilterLink<Mail,Boolean> chainOfResp = new FilterChainImpl<Mail, Boolean>(filters);
        
        chainOfResp.filter(new MailImpl(100, true));//caught by the spam detector.
        chainOfResp.filter(new MailImpl(100, false));//caught by the large attachment detector.
        chainOfResp.filter(new MailImpl(40, false));//goes into the inbox.
        chainOfResp.filter(new MailImpl(60, false));//caught by the large attachment detector.
    }
}
