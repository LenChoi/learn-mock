package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class Test05ThrowingException {

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
    void should_NotCompleteBooking_When_PriceTooHigh() {
        // given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01),
                LocalDate.of(2020, 01, 05), 2, true);

        when(this.paymentServiceMock.pay(any(), eq(400.0))).thenThrow(BusinessException.class); // 타입에 맞는 값을 벋움, null이 되는 String은 any() 사용


        // when
        Executable executable = () -> bookingService.makeBooking(bookingRequest);


        // then
        assertThrows(BusinessException.class, executable);
    }
}
