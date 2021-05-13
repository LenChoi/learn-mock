package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

public class Test07Spies {

    private BookingService bookingService;
    private PaymentService paymentServiceMock;
    private RoomService roomServiceMock;
    private BookingDAO bookingDAOMock;
    private MailSender mailSenderMock;

    @BeforeEach
    void setup() {
        this.paymentServiceMock = mock(PaymentService.class); // 더미 객체 만듬, 실제 처럼 동작하지만 코드나 로직이 동작 하지는 않음
        this.roomServiceMock = mock(RoomService.class);
        this.bookingDAOMock = spy(BookingDAO.class);  // 로직을 가진 실제 객체로 우리가 수정할 수 있다
        this.mailSenderMock = mock(MailSender.class);

        this.bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock, mailSenderMock);
    }

    @Test
    void should_MakeBooking_When_InputOK() {
        // given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01),
                LocalDate.of(2020, 01, 05), 2, true);

        // when
        String bookingId = bookingService.makeBooking(bookingRequest);

        // then
        verify(bookingDAOMock).save(bookingRequest);
        System.out.println("bookingId=" + bookingId);
    }

    @Test
    void should_CancelBooking_When_InputOK() {
        // given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01),
                LocalDate.of(2020, 01, 05), 2, true);
        bookingRequest.setRoomId("1.3");
        String bookingId = "1";

        doReturn(bookingRequest).when(bookingDAOMock).get(bookingId); // spy는 앞에 doReturn 추가

        // mocks: when(mock.method()).thenReturn()
        // spries: doReturn().when(spy).method()
        // 스파이는 부분적 mock, 해당 동작을 수정하여 값을 반환한다. 원하는 값이지만 기본적으로 실제 코드를 사용한다


        // when
        bookingService.cancelBooking(bookingId);

        // then

    }
}
