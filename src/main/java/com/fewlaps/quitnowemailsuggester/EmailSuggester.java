package com.fewlaps.quitnowemailsuggester;

import com.fewlaps.quitnowemailsuggester.bean.EmailCorrection;
import com.fewlaps.quitnowemailsuggester.exception.InvalidEmailException;

import java.util.Arrays;
import java.util.List;

public class EmailSuggester {

    public static final String YAHOO = "yahoo";
    public static final String GMAIL = "gmail";
    public static final String HOTMAIL = "hotmail";
    public static final String OUTLOOK = "outlook";
    public static final String AOL = "aol";

    public static final String COM = "com";

    public static final String DOTCOM = ".com";
    public static final String DOTNET = ".net";
    public static final String DOTFR = ".fr";
    public static final String DOTEDU = ".edu";

    public List<EmailCorrection> tldCorrections = Arrays.asList(
            new EmailCorrection(".cpm", DOTCOM),
            new EmailCorrection(".cpn", DOTCOM),
            new EmailCorrection(".con", DOTCOM),
            new EmailCorrection(".col", DOTCOM),
            new EmailCorrection(".comm", DOTCOM),
            new EmailCorrection(".cxom", DOTCOM),
            new EmailCorrection(".coml", DOTCOM),
            new EmailCorrection(".vom", DOTCOM),
            new EmailCorrection(".ney", DOTNET),
            new EmailCorrection(".nte", DOTNET),
            new EmailCorrection(".ft", DOTFR),
            new EmailCorrection(".eduu", DOTEDU)
    );

    public List<EmailCorrection> domainCorrections = Arrays.asList(
            new EmailCorrection("gnail", GMAIL),
            new EmailCorrection("gmial", GMAIL),
            new EmailCorrection("gmaail", GMAIL),
            new EmailCorrection("gnail", GMAIL),
            new EmailCorrection("gamil", GMAIL),
            new EmailCorrection("gmal", GMAIL),
            new EmailCorrection("ygmail", GMAIL),
            new EmailCorrection("ymail", GMAIL),
            new EmailCorrection("yamil", GMAIL),
            new EmailCorrection("gmai", GMAIL),
            new EmailCorrection("gimail", GMAIL),
            new EmailCorrection("gmaik", GMAIL),
            new EmailCorrection("gimal", GMAIL),
            new EmailCorrection("gmaill", GMAIL),
            new EmailCorrection("gmil", GMAIL),
            new EmailCorrection("gmsil", GMAIL),
            new EmailCorrection("gmaol", GMAIL),
            new EmailCorrection("gmaul", GMAIL),
            new EmailCorrection("gail", GMAIL),
            new EmailCorrection("hmail", GMAIL),

            new EmailCorrection("hotmaail", HOTMAIL),
            new EmailCorrection("hotmal", HOTMAIL),
            new EmailCorrection("hotmai", HOTMAIL),
            new EmailCorrection("hotmali", HOTMAIL),
            new EmailCorrection("hitmail", HOTMAIL),
            new EmailCorrection("hotmial", HOTMAIL),
            new EmailCorrection("hotmale", HOTMAIL),
            new EmailCorrection("homtail", HOTMAIL),
            new EmailCorrection("hotnail", HOTMAIL),
            new EmailCorrection("hormail", HOTMAIL),
            new EmailCorrection("hotmsil", HOTMAIL),
            new EmailCorrection("jotmail", HOTMAIL),

            new EmailCorrection("ya", YAHOO),
            new EmailCorrection("yaho", YAHOO),
            new EmailCorrection("yaoo", YAHOO),
            new EmailCorrection("yaboo", YAHOO),
            new EmailCorrection("yahou", YAHOO),
            new EmailCorrection("yshoo", YAHOO),
            new EmailCorrection("yayoo", YAHOO),
            new EmailCorrection("hahoo", YAHOO),
            new EmailCorrection("yahoi", YAHOO),
            new EmailCorrection("yzhoo", YAHOO),
            new EmailCorrection("yajoo", YAHOO),

            new EmailCorrection("outllok", OUTLOOK),
            new EmailCorrection("outilook", OUTLOOK),
            new EmailCorrection("outloo", OUTLOOK),

            new EmailCorrection("ail", AOL)
    );

    public List<EmailCorrection> domainAndTldCorrections = Arrays.asList(
            new EmailCorrection("gmail.co", GMAIL + DOTCOM),
            new EmailCorrection("gmail.om", GMAIL + DOTCOM),
            new EmailCorrection("gmail.cim", GMAIL + DOTCOM),
            new EmailCorrection("gmail.clm", GMAIL + DOTCOM),
            new EmailCorrection("gmail.fom", GMAIL + DOTCOM),
            new EmailCorrection("g.mailcom", GMAIL + DOTCOM),
            new EmailCorrection("g.mail.com", GMAIL + DOTCOM),
            new EmailCorrection("gmail.comes", GMAIL + DOTCOM),
            new EmailCorrection("gmail.vom", GMAIL + DOTCOM),

            new EmailCorrection("yahoo.om", YAHOO + DOTCOM),
            new EmailCorrection("yahoo.cm", YAHOO + DOTCOM),

            new EmailCorrection("hotmail.om", HOTMAIL+ DOTCOM),
            new EmailCorrection("hotmail.clm", HOTMAIL+ DOTCOM),

            new EmailCorrection("outlook.co", OUTLOOK+ DOTCOM),
            new EmailCorrection("outlook.om", OUTLOOK+ DOTCOM),

            new EmailCorrection("aol.comcom", AOL + DOTCOM)
    );

    public String getSuggestedEmail(String email) throws InvalidEmailException {
        if (email == null) {
            throw new InvalidEmailException();
        }

        email = fixComWithAnotherChar(email);
        for (EmailCorrection correction : tldCorrections) {
            email = fixTld(email, correction.getBadEnd(), correction.getGoodEnd());
        }
        for (EmailCorrection correction : domainCorrections) {
            email = fixDomain(email, correction.getBadEnd(), correction.getGoodEnd());
        }
        for (EmailCorrection correction : domainAndTldCorrections) {
            email = fixDomainAndTld(email, correction.getBadEnd(), correction.getGoodEnd());
        }

        return email;
    }

    private String fixComWithAnotherChar(String email) throws InvalidEmailException {
        EmailPartsSplitter ep = new EmailPartsSplitter();
        String tld = ep.getTld(email);
        if (tld.contains(COM) && tld.length() == COM.length() + 1) {//if it's coma, comb, comc, acom, bcom, ccom...
            return fixTld(email, tld, COM);
        } else {
            return email;
        }
    }

    private String fixTld(String email, String badTld, String goodTld) {
        if (email.endsWith(badTld)) {
            email = email.substring(0, email.length() - badTld.length()).concat(goodTld);
        }
        return email;
    }

    private String fixDomain(String email, String badDomain, String goodDomain) throws InvalidEmailException {
        EmailPartsSplitter ep = new EmailPartsSplitter();
        String domain = ep.getDomainWithoutTld(email);
        if (domain.equals(badDomain)) {
            email = email.replaceAll(domain, goodDomain);
        }
        return email;
    }

    private String fixDomainAndTld(String email, String badDomainAndTld, String goodDomainAndTld) {
        if (email.endsWith(badDomainAndTld)) {
            email = email.substring(0, email.length() - badDomainAndTld.length()).concat(goodDomainAndTld);
        }
        return email;
    }
}
