package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class Test04MultipleThenReturnCalls {

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
    void should_CountAvailablePlaces_When_OneRoomAvailable() {
        // given
        when(this.roomServiceMock.getAvailableRooms()) // 이 메소드가 실행될때 아래와 같은 값을 리턴하게 설정
                .thenReturn(Collections.singletonList((new Room("Room 1", 5))));
        int expected = 5;

        // when
        int actual = bookingService.getAvailablePlaceCount();

        // then
        assertEquals(expected, actual);
    }

    @Test
    void should_CountAvailablePlaces_When_CalledMultipleTimes() {
        // given
        when(this.roomServiceMock.getAvailableRooms()) // 이 메소드가 실행될때 아래와 같은 값을 리턴하게 설정
                .thenReturn(Collections.singletonList((new Room("Room 1", 5)))) // 처음에는 이것을 반환
                .thenReturn(Collections.emptyList()); // 두번째는 이것을 반환
        int expectedFirstCall = 5;
        int expectedSecondCall = 0;

        // when
        int actualFirst = bookingService.getAvailablePlaceCount();
        int actualSecond = bookingService.getAvailablePlaceCount();

        // then
        assertAll(
                () -> assertEquals(expectedFirstCall, actualFirst),
                () -> assertEquals(expectedSecondCall, actualSecond)
        );
    }
}
