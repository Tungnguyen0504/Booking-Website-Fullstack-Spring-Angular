package com.springboot.booking.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.springboot.booking.common.DatetimeUtil;
import com.springboot.booking.common.ExceptionResult;
import com.springboot.booking.common.paging.BasePagingRequest;
import com.springboot.booking.common.paging.BasePagingResponse;
import com.springboot.booking.dto.request.BookingRequest;
import com.springboot.booking.dto.request.ChangeBookingStatusRequest;
import com.springboot.booking.dto.response.BookingDetailResponse;
import com.springboot.booking.dto.response.BookingResponse;
import com.springboot.booking.dto.response.RoomResponse;
import com.springboot.booking.exeption.GlobalException;
import com.springboot.booking.model.EBookingStatus;
import com.springboot.booking.model.entity.Booking;
import com.springboot.booking.model.entity.BookingDetail;
import com.springboot.booking.model.entity.Room;
import com.springboot.booking.model.entity.User;
import com.springboot.booking.repository.BookingDetailRepository;
import com.springboot.booking.repository.BookingRepository;
import com.springboot.booking.repository.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {

    @Value("${currency.rate.usd}")
    private String currencyRate;

    DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    private final RoomService roomService;
    private final UserService userService;
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;
    private final BookingDetailRepository bookingDetailRepository;
    private final PagingService pagingService;
    private final PaymentService paymentService;

    @Transactional
    public Map<String, Object> createBookingInfo(BookingRequest request) throws JsonProcessingException {
        User user = userService.getCurrentUser();

        Optional<Booking> lastBooking = bookingRepository.findLastBookingAwaitingPayment(user.getId());
        lastBooking.ifPresent(bookingRepository::delete);

        Booking booking = Booking.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .guestNumber(Integer.valueOf(request.getGuestNumber()))
                .note(request.getNote())
                .estCheckinTime(request.getEstCheckinTime())
                .paymentMethod(request.getPaymentMethod())
                .totalAmount(calculateTotalAmount(request))
                .fromDate(DatetimeUtil.parseDateDefault(request.getFromDate()))
                .toDate(DatetimeUtil.parseDateDefault(request.getToDate()))
                .status(EBookingStatus.WAITING_PAYMENT)
                .user(user)
                .build();
        List<BookingDetail> bookingDetails = request.getCartItems()
                .stream()
                .map(cart -> {
                            Room room = roomRepository.findById(cart.getRoomId())
                                    .orElseThrow(() -> new GlobalException(ExceptionResult.CUSTOM_FIELD_NOT_FOUND, "phòng"));
                            room.setQuantity(room.getQuantity() - cart.getQuantity());
                            roomRepository.save(room);
                            return BookingDetail.builder()
                                    .quantity(cart.getQuantity())
                                    .room(roomRepository.findById(cart.getRoomId())
                                            .orElseThrow(() -> new GlobalException(ExceptionResult.CUSTOM_FIELD_NOT_FOUND, "phòng")))
                                    .booking(booking)
                                    .build();
                        }
                )
                .toList();
        bookingDetailRepository.saveAll(bookingDetails);

        booking.setBookingDetails(bookingDetails);
        bookingRepository.save(booking);

        Map<String, Object> response = paymentService.createOrder(request);
        response.put("bookingId", booking.getId());

        return response;
    }

    public BasePagingResponse getBookings(BasePagingRequest request) {
        Sort sort = pagingService.buildOrders(request);
        List<Specification<Booking>> specifications = pagingService.buildSpecifications(request);

        Pageable pageable = PageRequest.of(request.getCurrentPage(), request.getTotalPage(), sort);
        Page<Booking> bookingPage = bookingRepository.findAll(
                Specification.allOf(specifications), pageable);

        return BasePagingResponse.builder()
                .data(bookingPage.getContent().stream().map(this::transferToDto).collect(Collectors.toList()))
                .currentPage(bookingPage.getPageable().getPageNumber())
                .totalItem(bookingPage.getTotalElements())
                .totalPage(bookingPage.getPageable().getPageSize())
                .build();
    }

    public void changeStatus(ChangeBookingStatusRequest request) {
        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new GlobalException(ExceptionResult.CUSTOM_FIELD_NOT_FOUND, "đơn đặt hàng"));
        booking.setStatus(request.getStatus());
        booking.setReason(request.getReason());
        bookingRepository.save(booking);
    }

    public double calculateTotalAmount(BookingRequest request) {
        int subDate = DatetimeUtil.subLocalDate(request.getFromDate(), request.getToDate());
        return request.getCartItems()
                .stream()
                .map(req -> {
                    RoomResponse room = roomService.getById(req.getRoomId());
                    double subPrice = room.getPrice() * ((100 - room.getDiscountPercent()) / 100) * req.getQuantity() * subDate;
                    return Double.parseDouble(decimalFormat.format(subPrice));
                })
                .reduce(0.0, Double::sum);
    }

    public BookingResponse transferToDto(Booking booking) {
        return BookingResponse.builder()
                .id(booking.getId())
                .accommodationId(booking.getBookingDetails().get(0).getRoom().getAccommodation().getId())
                .firstName(booking.getFirstName())
                .lastName(booking.getLastName())
                .email(booking.getEmail())
                .phoneNumber(booking.getPhoneNumber())
                .guestNumber(booking.getGuestNumber())
                .note(booking.getNote())
                .estCheckinTime(booking.getEstCheckinTime())
                .paymentMethod(booking.getPaymentMethod())
                .totalAmount(booking.getTotalAmount())
                .fromDate(booking.getFromDate())
                .toDate(booking.getToDate())
                .status(booking.getStatus())
                .userId(booking.getUser().getId())
                .bookingDetails(booking.getBookingDetails()
                        .stream()
                        .map(bookingDetail -> BookingDetailResponse.builder()
                                .id(bookingDetail.getId())
                                .quantity(bookingDetail.getQuantity())
                                .roomId(bookingDetail.getRoom().getId())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
