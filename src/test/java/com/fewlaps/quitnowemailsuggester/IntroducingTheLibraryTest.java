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
        // In addition, it will never suggest a bad domain. All the suggestions are written one by one,
        // based on the QuitNow! users and their e-mail bounces. If it doesn't know nothing
        // better than the input, it will return the same email.

        // Well! Something else? Yes: this library hosts some more email related things
        EmailValidator validator = new EmailValidator();
        validator.hasGoodSyntax(goodEmail); //It matches the email regex?
        validator.hasValidTld(goodEmail); //It has a valid TLD?
        validator.isAlias(goodEmail); //Is it an alias?
        validator.isDisposable(goodEmail); //Is it listed as a disposable domain?

        // To finish up: if you work with Android, you'll find roc@fewlaps.com:WhatEVER emails
        // If you want to clean them, here it goes:
        AndroidAccountEmailCleaner cleaner = new AndroidAccountEmailCleaner();
        String androidAccountEmail = "roc@fewlaps.com:WhatEVER";
        String cleanedEmail = cleaner.cleanEmail(androidAccountEmail);
        // cleanedEmail will contain roc@fewlaps.com

        assertEquals("roc@gmail.com", goodEmail);
        assertEquals("roc@fewlaps.com", cleanedEmail);
    }
}
