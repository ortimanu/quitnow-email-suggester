package com.fewlaps.quitnowemailsuggester;

import com.fewlaps.quitnowemailsuggester.exception.InvalidEmailException;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class IntroducingTheLibraryTest {

    @Test
    public void introducingTheLibrary() throws InvalidEmailException {
        // Hello! Let me show the magic of this library: the email suggester
        EmailSuggester suggester = new EmailSuggester();

        // It is a little thing that has an algorithm to fix human typos.
        // Let's say your user mistyped his email address:
        String badEmail = "roc@gmial.com";
        String goodEmail = suggester.getSuggestedEmail(badEmail);
        // goodEmail will contain the fixed email address. WOW!
        // And there are tons of email fixes. They're listed at https://goo.gl/IF52EV

        // Well! Something else? Yes: this library hosts some more email related things
        EmailValidator validator = new EmailValidator();
        validator.hasGoodSyntax(goodEmail); //It matches the email regex?
        validator.hasValidTld(goodEmail); //It has a valid TLD?
        validator.isAlias(goodEmail); //Is it an alias?
        validator.isDisposable(goodEmail); //Is it listed as a disposable domain?

        // To finish up: if you work with Android, you'll find roc@fewlaps.com:something emails
        // If you want to clean them, here it goes:
        AndroidAccountEmailCleaner cleaner = new AndroidAccountEmailCleaner();
        String androidAccountEmail = "roc@fewlaps.com:something";
        String cleanedEmail = cleaner.cleanEmail(androidAccountEmail);
        // cleanedEmail will contain roc@fewlaps.com

        assertEquals("roc@gmail.com", goodEmail);
        assertEquals("roc@fewlaps.com", cleanedEmail);
    }
}
