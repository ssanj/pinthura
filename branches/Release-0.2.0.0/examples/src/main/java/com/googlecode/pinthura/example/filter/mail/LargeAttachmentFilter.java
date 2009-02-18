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

import com.googlecode.pinthura.processer.CouldNotProcessInputException;
import com.googlecode.pinthura.processer.ProcesserChainlet;

public final class LargeAttachmentFilter implements ProcesserChainlet<Mail, Boolean> {

    private final MailMan mailMan;
    private final long attachmentLimit;

    public LargeAttachmentFilter(final long attachmentLimit, final MailMan mailMan) {
        this.mailMan = mailMan;
        this.attachmentLimit = attachmentLimit;
    }

    public Boolean process(final Mail mail) throws CouldNotProcessInputException {
        if (mail.getAttachmentSize() > attachmentLimit) {
            mailMan.moveMail(MailLocationEnum.LARGE_ATTACHMENTS);
            return true;
        }

        throw new CouldNotProcessInputException();
    }

    public String getProcesserName() {
        return "Large Attachment Filter";
    }
}
