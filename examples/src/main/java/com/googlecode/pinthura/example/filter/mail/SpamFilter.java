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

public final class SpamFilter implements ProcesserChainlet<Mail, Boolean> {

    private final SpamDetector spamDetector;
    private final MailMan mailMan;

    public SpamFilter(final SpamDetector spamDetector, final MailMan mailMan) {
        this.spamDetector = spamDetector;
        this.mailMan = mailMan;
    }

    public Boolean process(final Mail mail) throws CouldNotProcessInputException {
        if (spamDetector.isSpam(mail)) {
            mailMan.moveMail(MailLocationEnum.SPAM);
            return true;
        }

        throw new CouldNotProcessInputException();
    }

    public String getProcesserName() {
        return "Spam Filter";
    }
}
