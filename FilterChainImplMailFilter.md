# A Mail Filter Example #

This example models a simple mail filtering system that filters mail based on:

  1. Whether it has marketing information. (spam)
  1. If it has large attachments.
  1. If it has neither of the above (Inbox)

**Each `FilterLink` accepts a `Mail` instance as Input and returns a `Boolean` as Output.**

The `SpamFilter`:

```
public final class SpamFilter implements FilterLink<Mail, Boolean> {

    private final SpamDetector spamDetector;
    private final MailMan mailMan;

    public SpamFilter(final SpamDetector spamDetector, final MailMan mailMan) {
        this.spamDetector = spamDetector;
        this.mailMan = mailMan;
    }

    public Boolean filter(final Mail mail) throws MatchNotFoundException {
        if (spamDetector.isSpam(mail)) {
            mailMan.moveMail(MailLocationEnum.SPAM);
            return true;
        }

        throw new MatchNotFoundException();
    }

    public String getFilterName() {
        return "Spam Filter";
    }
}
```

The `LargeAttachmentFilter`:

```
public final class LargeAttachmentFilter implements FilterLink<Mail, Boolean> {

    private final MailMan mailMan;
    private final long attachmentLimit;

    public LargeAttachmentFilter(final long attachmentLimit, final MailMan mailMan) {
        this.mailMan = mailMan;
        this.attachmentLimit = attachmentLimit;
    }

    public Boolean filter(final Mail mail) throws MatchNotFoundException {
        if (mail.getAttachmentSize() > attachmentLimit) {
            mailMan.moveMail(MailLocationEnum.LARGE_ATTACHMENTS);
            return true;
        }

        throw new MatchNotFoundException();
    }

    public String getFilterName() {
        return "Large Attachment Filter";
    }
}
```

The `InboxFilter`:

```
public final class InboxFilter implements FilterLink<Mail, Boolean> {

    private final MailMan mailMan;

    public InboxFilter(final MailMan mailMan) {
        this.mailMan = mailMan;
    }

    public Boolean filter(final Mail mail) throws MatchNotFoundException {
        mailMan.moveMail(MailLocationEnum.INBOX);
        return true;
    }

    public String getFilterName() {
        return "Inbox Filter";
    }
}
```

The `MailFilterRunner`:

```
public final class MailFilterRunner {

    private static final int ATTACHMENT_LIMIT_OF_50 = 50;
    private static final int ATTACHMENT_OF_40       = 40;
    private static final int ATTACHMENT_OF_60       = 60;
    private static final int ATTACHMENT_OF_100      = 100;
    private static final boolean SPAM               = true;
    private static final boolean NOT_SPAM           = false;

    private MailFilterRunner() { }

    public static void main(final String[] args) {
        List<FilterLink<Mail, Boolean>> filters = new ArrayList<FilterLink<Mail, Boolean>>();
        MailMan mailMan = new MailManImpl();
        filters.add(new SpamFilter(new SpamDetectorImpl(), mailMan));
        filters.add(new LargeAttachmentFilter(ATTACHMENT_LIMIT_OF_50, mailMan));
        filters.add(new InboxFilter(mailMan));

        FilterLink<Mail, Boolean> chainOfResp = new FilterChainImpl<Mail, Boolean>(filters);

        chainOfResp.filter(new MailImpl(ATTACHMENT_OF_100, SPAM));
        chainOfResp.filter(new MailImpl(ATTACHMENT_OF_100, NOT_SPAM));
        chainOfResp.filter(new MailImpl(ATTACHMENT_OF_40, NOT_SPAM));
        chainOfResp.filter(new MailImpl(ATTACHMENT_OF_60, NOT_SPAM));
    }
}
```

The output of `MailFilterRunner` is:

```
moving mail to SPAM folder
moving mail to LARGE_ATTACHMENTS folder
moving mail to INBOX folder
moving mail to LARGE_ATTACHMENTS folder

Process finished with exit code 0
```