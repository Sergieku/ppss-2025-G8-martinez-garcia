package ppss.P06;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;


public class NotifyCenterTest {
    LocalDate actualDate, date;
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
        date = actualDate;
        String[] emails = new String[]{"email1","email2","email3","email4"};
        List<String> emailList = Arrays.asList(emails);

        messageExceptExpected = "Failures during sending process";

        EasyMock.expect(notifyCentermock.getServer())
                .andReturn(server);
        EasyMock.expect(notifyCentermock.getLocalDate())
                .andReturn(actualDate);
        EasyMock.expect(server.findMailItemsWithDate(date))
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
                () -> notifyCentermock.notifyUsers(date));

        assertEquals(messageExceptExpected, exception.getMessage());

        crtl.verify();
    }

    @Test
    void C2_notifyUsers_should_return_DateError_when_date_passed_not_actualdate(){
        actualDate = LocalDate.of(2025,03,11);
        date = LocalDate.of(2025,02,11);

        messageExceptExpected = "Date error";

        EasyMock.expect(notifyCentermock.getServer())
                .andReturn(server);
        EasyMock.expect(notifyCentermock.getLocalDate())
                .andReturn(actualDate);

        crtl.replay();

        FailedNotifyException exception = assertThrows(FailedNotifyException.class,
                () -> notifyCentermock.notifyUsers(date));

        assertEquals(messageExceptExpected, exception.getMessage());

        crtl.verify();
    }

    @Test
    void C3_notifyUsers_should_return_DateError_when_date_correct_and_no_mail(){
        actualDate = LocalDate.of(2025,03,23);
        date = actualDate;

        List<String> emailList = new ArrayList<>();

        EasyMock.expect(notifyCentermock.getServer())
                .andReturn(server);
        EasyMock.expect(notifyCentermock.getLocalDate())
                .andReturn(actualDate);
        EasyMock.expect(server.findMailItemsWithDate(date))
                .andReturn(emailList);

        crtl.replay();

        assertDoesNotThrow(
                () -> notifyCentermock.notifyUsers(date));

        crtl.verify();
    }
}
