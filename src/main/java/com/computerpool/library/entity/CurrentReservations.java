package com.computerpool.library.entity;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;


@Entity
@Table(name="current_reservations")
@Data
@NoArgsConstructor

public class CurrentReservations {

    @Id
    @Column(name="reservation_id")
    private long reservationId;

    @Column(name="room_number")
    private int roomNumber;

    @Column(name="number_of_people")
    private int numberOfPeople;
    
    @Column(name="reserved_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date reservedDate;

    @Column(name="booking_date")
    private LocalDateTime bookingDate;

    @Column(name="start_time")
    private String startTime;

   
    @Column(name="end_time")
    private String endTime;

    @ManyToOne(cascade = {})
    @JoinColumn(name="student_id")
    private Student student;
}
