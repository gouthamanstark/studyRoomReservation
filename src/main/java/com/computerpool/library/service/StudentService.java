package com.computerpool.library.service;

import java.util.Date;
import java.util.List;
import com.computerpool.library.entity.CurrentReservations;


public interface StudentService {

  public String reserveStudyRoom(CurrentReservations reservation);
  public CurrentReservations getReservationByID(long reservationID);
  public String cancelReservationByID(long reservationID);
  public List<Integer> checkRoomAvailability( Date reservedDate,String startTime,String endTime);
  public int checkStudentAllowanceForTheDay(int studentID,Date date);

    
}
