package com.example.junittest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class TestNewsletterSender2 {
    private SubscribersDatabase subscribersDatabase;
    private MessagingEngine messagingEngine;
    private NewsletterSender newsletterSender;


    @BeforeEach
    public void initMocks() {
        subscribersDatabase = mock(SubscribersDatabase.class);
        messagingEngine = mock(MessagingEngine.class);
        newsletterSender = new NewsletterSender(subscribersDatabase, messagingEngine);

        List<String> subscribersListStub = Arrays.asList("email1", "email2", "email3");
        when(subscribersDatabase.getSubscribers()).thenReturn(subscribersListStub);
    }

    @Test
    public void constructorAssignsDatabase() {
        assertEquals(subscribersDatabase, newsletterSender.getSubscribersDatabase());
    }

    @Test
    public void testNumberOfSubscribers() {
        assertEquals(3, newsletterSender.numberOfSubscribers());
    }

    @Test
    public void testSendNewsletterThrowsZeroSubscribersException() {
        NewsletterSender newsletterSenderSpy = spy(newsletterSender);

        //for spies, better to use the doReturn().when().() syntax instead of when().thenReturn()
        //we can override even the stub declared in @BeforeEach!
        doReturn(0).when(newsletterSenderSpy).numberOfSubscribers();

        assertThrows(ZeroSubscribersException.class, () -> {
            newsletterSenderSpy.sendNewsletter("Mail Subject");
        });
    }
}
