package com.example.junittest;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class TestNewsletterSender {

    @Test
    public void constructorAssignsDatabase() {
        SubscribersDatabase subscribersDatabase = new SubscribersDatabase();
        MessagingEngine messagingEngineMock = mock(MessagingEngine.class);
        NewsletterSender newsletterSender = new NewsletterSender(subscribersDatabase, messagingEngineMock);

        assertEquals(subscribersDatabase, newsletterSender.getSubscribersDatabase());
    }

    @Test
    public void testNumberOfSubscribers() {
        SubscribersDatabase subscribersDatabaseMock = mock(SubscribersDatabase.class);
        MessagingEngine messagingEngineMock = mock(MessagingEngine.class);
        NewsletterSender newsletterSender = new NewsletterSender(subscribersDatabaseMock, messagingEngineMock);

        List<String> subscribersListStub = Arrays.asList("email1", "email2", "email3");
        when(subscribersDatabaseMock.getSubscribers()).thenReturn(subscribersListStub);

        assertEquals(3, newsletterSender.numberOfSubscribers());
    }

    @Test
    public void testSendNewsletterThrowsZeroSubscribersException() {
        //need to create a real instance whose behavior is modified in the spy version
        NewsletterSender newsletterSender = new NewsletterSender(new SubscribersDatabase(), new MessagingEngine());
        NewsletterSender newsletterSenderSpy = spy(newsletterSender);

        when(newsletterSenderSpy.numberOfSubscribers()).thenReturn(0);

        assertThrows(ZeroSubscribersException.class, () -> {
                    newsletterSenderSpy.sendNewsletter("Mail Subject");
                });
    }
}
