package com.computerpool.library.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.computerpool.library.entity.CurrentReservations;

public interface ReservationRepo extends JpaRepository<CurrentReservations,Long> {


    @Query("SELECT a FROM CurrentReservations a JOIN FETCH a.student b WHERE b.studentId = :studentId")
    Page<CurrentReservations> findByStudentId(int studentId,Pageable pageable);
    
    @Query(value = "SELECT room_number from rooms where room_number not in(SELECT room_number FROM current_reservations r " +
    "WHERE r.reserved_date = :reservedDate " +
    "AND r.start_time BETWEEN :startTime AND :endTime)",
nativeQuery = true)
    List<Integer> findAvailableRoomsList(Date reservedDate,String startTime,String endTime);


    @Query("SELECT r.startTime,r.endTime from CurrentReservations r JOIN r.student a where a.studentId=:studentID AND r.reservedDate=:date")
    List<Object[]> findStudentReservationsByDate(int studentID,Date date);

    Page<CurrentReservations> findByreservedDate(Date date,Pageable pageable);

    List<CurrentReservations> findByreservedDate(Date date);

    
}
 