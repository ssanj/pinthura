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

import com.googlecode.pinthura.processer.ChainOfResponsibility;
import com.googlecode.pinthura.processer.ProcesserChain;
import com.googlecode.pinthura.processer.ProcesserChainlet;

import java.util.ArrayList;
import java.util.List;

public final class MailFilterRunner {

    private static final int ATTACHMENT_LIMIT_OF_50 = 50;
    private static final int ATTACHMENT_OF_40       = 40;
    private static final int ATTACHMENT_OF_60       = 60;
    private static final int ATTACHMENT_OF_100      = 100;
    private static final boolean SPAM               = true;
    private static final boolean NOT_SPAM           = false;

    private MailFilterRunner() { }

    public static void main(final String[] args) {
        List<ProcesserChainlet<Mail, Boolean>> filters = new ArrayList<ProcesserChainlet<Mail, Boolean>>();
        MailMan mailMan = new MailManImpl();
        filters.add(new SpamFilter(new SpamDetectorImpl(), mailMan));
        filters.add(new LargeAttachmentFilter(ATTACHMENT_LIMIT_OF_50, mailMan));
        filters.add(new InboxFilter(mailMan));

        ChainOfResponsibility<Mail, Boolean> chainOfResp = new ProcesserChain<Mail, Boolean>(filters);

        chainOfResp.process(new MailImpl(ATTACHMENT_OF_100, SPAM));
        chainOfResp.process(new MailImpl(ATTACHMENT_OF_100, NOT_SPAM));
        chainOfResp.process(new MailImpl(ATTACHMENT_OF_40, NOT_SPAM));
        chainOfResp.process(new MailImpl(ATTACHMENT_OF_60, NOT_SPAM));
    }
}
