package assignment.spring.controller;

import assignment.spring.model.dto.ReservationRequest;
import assignment.spring.model.entity.Reservation;
import assignment.spring.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 예약기능인 컨트롤러입니다
 */
@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private ReservationService reservationService;

    //예약하기
    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationRequest request) {
        Reservation reservation = reservationService.createReservation(request);
        return ResponseEntity.ok(reservation);
    }

    //예약 보기
    @GetMapping("/{reservationId}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('MANAGER')")
    public ResponseEntity<Reservation> getReservationDetails(@PathVariable Long reservationId) {
        Reservation reservation = reservationService.getReservationById(reservationId);
        return ResponseEntity.ok(reservation);
    }

    //예약 보기
    @PutMapping("confirm/{reservationId}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('MANAGER')")
    public ResponseEntity<String> confirmReservation(@PathVariable Long reservationId) {
        reservationService.confirmReservation(reservationId);
        return ResponseEntity.ok("Reservation confirmed");
    }
}