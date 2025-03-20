package ppss.P06;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;


public class NotifyCenterTest {
    LocalDate actualDate, sentDate;
    IMocksControl crtl;
    MailServer server;
    NotifyCenter notifyCentermock;
    String messageExceptExpected;

    @BeforeEach
    void setUp() {
        crtl = EasyMock.createStrictControl();
        server = crtl.mock(MailServer.class);
        notifyCentermock = EasyMock.partialMockBuilder(NotifyCenter.class)
                .addMockedMethods("getServer","sendNotify","getLocalDate")
                .mock(crtl);
    }

    @Test
    void C1_notifyUsers_should_return_failures_when_2_emails_fail(){
        actualDate = LocalDate.of(2025,03,11);
        sentDate = actualDate;
        String[] emails = new String[]{"email1","email2","email3","email4"};
        List<String> emailList = Arrays.asList(emails);

        messageExceptExpected = "Failures during sending process";

        EasyMock.expect(notifyCentermock.getServer())
                .andReturn(server);
        EasyMock.expect(notifyCentermock.getLocalDate())
                .andReturn(actualDate);
        EasyMock.expect(server.findMailItemsWithDate(sentDate))
                        .andReturn(emailList);

        // Seguramente eli no lo quiere así, pero a saber cómo lo quiere
        assertDoesNotThrow(
                () -> {
                    notifyCentermock.sendNotify(emails[0]);
                    notifyCentermock.sendNotify(emails[1]);
                    EasyMock.expectLastCall().andThrow(new FailedNotifyException(emails[1]));
                    notifyCentermock.sendNotify(emails[2]);
                    EasyMock.expectLastCall().andThrow(new FailedNotifyException(emails[2]));
                    notifyCentermock.sendNotify(emails[3]);
                });

        crtl.replay();

        FailedNotifyException exception = assertThrows(FailedNotifyException.class,
                () -> notifyCentermock.notifyUsers(actualDate));

        assertEquals(messageExceptExpected, exception.getMessage());

        crtl.verify();
    }
}
