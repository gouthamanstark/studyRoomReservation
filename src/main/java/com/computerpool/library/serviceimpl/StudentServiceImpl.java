package com.computerpool.library.serviceimpl;



import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import com.computerpool.library.entity.CurrentReservations;
import com.computerpool.library.repository.ReservationRepo;
import com.computerpool.library.repository.StudentRepo;
import com.computerpool.library.service.StudentService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private StudentRepo studentRepository;
    private ReservationRepo reservationRespository;
    

    @Override
    public String reserveStudyRoom(CurrentReservations reservation) {

        
       if(reservation.getReservationId()==0){


        List<Object[]> reservations=reservationRespository.findStudentReservationsByDate(reservation.getStudent().getStudentId(), reservation.getReservedDate());
        int count=0;
        for (Object[] reserve : reservations) {
        if((checkTimeWithInLimits(reservation.getStartTime(), reservation.getEndTime(), (String)reserve[0]) && checkTimeWithInLimits(reservation.getStartTime(), reservation.getEndTime(), (String)reserve[1]) )) {
            count+=1;
        }
    }
        if(count==0){
            List<Integer>availableRooms=reservationRespository.findAvailableRoomsList(reservation.getReservedDate(), reservation.getStartTime(), reservation.getEndTime());
            for (Integer room : availableRooms) {
                if(room==reservation.getRoomNumber()){
                    count+=1;
                }
            }
            if(count==1){

        int currentAllowanceUsage=checkStudentAllowanceForTheDay(reservation.getStudent().getStudentId(),reservation.getReservedDate());

        if(currentAllowanceUsage<120){
            int requiredAllowance=timeDiffereneceCalculator(reservation.getStartTime(),reservation.getEndTime());
            if((requiredAllowance+currentAllowanceUsage)<=120){
                


                
                reservation.setReservationId(RandomNumberGenerator.generateRandomNumber());
                reservation.setBookingDate(LocalDateTime.now());
                reservation.setStudent(studentRepository.findById(reservation.getStudent().getStudentId()).get());
                reservationRespository.save(reservation);
                return "Room reservation Successful !";
            }
            else{
                return "You have already used "+currentAllowanceUsage+" minutes in your daily 2 hrs allowance."+"You can now use only "+(120-currentAllowanceUsage)+" more minutes for today.";
            }
            
        }
        else{
            return "You have already used your daily allowance ! Please try to register for someother day";
        }
            }
        
            else{
                return "The requested room is unavailable! Please choose a different room";
            }
        }
        else{
            return "You already have a room reserved for the given date and time";
        }

        
       }
        
       else{
       
        reservation.setBookingDate(LocalDateTime.now());
        reservationRespository.save(reservation);
        return "Room reservation Updated Successfully !";
       }
        

    
    }

   

    @Override
    public CurrentReservations getReservationByID(long reservationID) {

       return reservationRespository.findById(reservationID).get();
         }

    @Override
    public String cancelReservationByID(long reservationID) {
       reservationRespository.deleteById(reservationID);
       return "Reservation Deleted Successfully !";
    
    }

    @Override
    public List<Integer> checkRoomAvailability(Date reservedDate, String startTime, String endTime) {
        
       
        return reservationRespository.findAvailableRoomsList(reservedDate, startTime, endTime);
    
    }

    @Override
    public int checkStudentAllowanceForTheDay(int studentID,Date date) {
    int usedAllowance=0; 
    List<Object[]> reservations=reservationRespository.findStudentReservationsByDate(studentID, date);
    
    for (Object[] reservation : reservations) {
        usedAllowance+=timeDiffereneceCalculator((String)reservation[0], (String)reservation[1]); 
    }

    return usedAllowance;
    }

    public int timeDiffereneceCalculator(String startTime,String endTime){
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime start=LocalTime.parse(startTime, formatter);
        LocalTime end=LocalTime.parse(endTime, formatter);
        Duration duration=Duration.between(start, end);
        
        return (int)duration.toMinutes();
    }

     public boolean checkTimeWithInLimits(String startTime,String endTime,String givenTime){
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime start=LocalTime.parse(startTime, formatter);
        LocalTime end=LocalTime.parse(endTime, formatter);
        LocalTime given=LocalTime.parse(givenTime, formatter);
        return !given.isBefore(start) && !given.isAfter(end);
        
        
    }

  
    
}
