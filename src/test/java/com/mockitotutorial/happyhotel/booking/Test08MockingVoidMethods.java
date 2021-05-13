package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class Test08MockingVoidMethods {

    private BookingService bookingService;
    private PaymentService paymentServiceMock;
    private RoomService roomServiceMock;
    private BookingDAO bookingDAOMock;
    private MailSender mailSenderMock;

    @BeforeEach
    void setup() {
        this.paymentServiceMock = mock(PaymentService.class); // 더미 객체 만듬, 실제 처럼 동작하지만 코드나 로직이 동작 하지는 않음
        this.roomServiceMock = mock(RoomService.class);
        this.bookingDAOMock = mock(BookingDAO.class);
        this.mailSenderMock = mock(MailSender.class);

        this.bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock, mailSenderMock);
    }

    @Test
    void should_ThrowException_When_MailNotReady() {
        // given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01),
                LocalDate.of(2020, 01, 05), 2, true);

        doThrow(new BusinessException()).when(mailSenderMock).sendBookingConfirmation(any()); // 예외를 던지고 싶을때 사용

        // when
        Executable executable = () -> bookingService.makeBooking(bookingRequest);


        // then
        assertThrows(BusinessException.class, executable);
    }

    @Test
    void should_NotThrowException_When_NoMailNotReady() {
        // given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01),
                LocalDate.of(2020, 01, 05), 2, true);

        doNothing().when(mailSenderMock).sendBookingConfirmation(any()); // 간혹 void로 선언된 메소드에 when()를 걸고 싶을때 oNothing()을 사용
        // 실제로 스킵해도 동작한다 반환값이 없기 때문에

        // when
        Executable executable = () -> bookingService.makeBooking(bookingRequest);


        // then
        // no exception thrown
    }
}
