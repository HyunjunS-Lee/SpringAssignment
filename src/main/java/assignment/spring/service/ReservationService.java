package assignment.spring.service;

import assignment.spring.expection.ErrorResponse;
import assignment.spring.model.dto.ReservationRequest;
import assignment.spring.model.entity.Reservation;
import assignment.spring.repository.ReservationRepository;
import assignment.spring.repository.StoreRepository;
import assignment.spring.repository.UserRepository;
import lombok.AllArgsConstructor;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static assignment.spring.type.ErrorCode.*;
import static assignment.spring.type.StoreStatus.CONFIRMED;
import static assignment.spring.type.StoreStatus.PENDING;

@Service
@AllArgsConstructor
public class ReservationService {
    private ReservationRepository reservationRepository;
    private StoreRepository storeRepository;
    private UserRepository userRepository;

    public Reservation createReservation(ReservationRequest request) {
        Reservation reservation = new Reservation();
        reservation.setStore(storeRepository.findById(request.getStoreId())
                .orElseThrow(() -> new ErrorResponse(STORE_NOT_FOUND)));
        reservation.setUser(userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ErrorResponse(USER_NOT_FOUND)));
        reservation.setReservationTime(request.getReservationTime());
        reservation.setStatus(PENDING);
        return reservationRepository.save(reservation);
    }

    public Reservation getReservationById(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ErrorResponse(RESERVATION_NOT_FOUND));
    }

    public void confirmReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ErrorResponse(RESERVATION_NOT_FOUND));
        reservation.setStatus(CONFIRMED);
        reservationRepository.save(reservation);
    }

    // 예약 시간 10분 전에 도착한 예약을 처리하는 메서드
    @Scheduled(fixedDelay = 60000)
    public void processArrivingReservations() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tenMinutesBeforeNow = now.minusMinutes(10);

        List<Reservation> arrivingReservations = reservationRepository.findByReservationTimeBetween(tenMinutesBeforeNow, now);

        for (Reservation reservation : arrivingReservations) {
            reservation.setStatus(CONFIRMED);
            reservationRepository.save(reservation);
        }
    }
}