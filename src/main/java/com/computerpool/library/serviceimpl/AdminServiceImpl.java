package com.computerpool.library.serviceimpl;



import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.io.FileOutputStream;
import java.sql.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.computerpool.library.entity.CurrentReservations;
import com.computerpool.library.entity.Student;
import com.computerpool.library.nfcReader.NfcReader;
import com.computerpool.library.repository.ReservationRepo;
import com.computerpool.library.repository.StudentRepo;
import com.computerpool.library.service.AdminService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    private StudentRepo studentRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private ReservationRepo reservationRepository;

    public Student registerNewStudent(Student student){

       String nfcUUID=NfcReader.nfcReader();
       if (nfcUUID.length()==20){
            student.getStudentDetails().setNfcTagUUID(nfcUUID);
            student.getStudentDetails().setEnabled(false);
            student.getStudentDetails().setActivationToken(RandomNumberGenerator.generateRandomActivationCode());
            student.getStudentDetails().setPassword(RandomNumberGenerator.generateRandomPassword());
            studentRepository.save(student);
            mailService.sendConfirmationMail(student);
        }
        
        else{
            System.out.println(nfcUUID);
        }

        
        return student;
    }


    public List<Student> getStudentsList(){

        return studentRepository.findAll();
        
    }

    public String deleteStudent(int universityEnrollmentNumber){

         studentRepository.deleteById(universityEnrollmentNumber);
         return "Student data deleted successfully ";

    }



    @Override
    public List<CurrentReservations> getReservationsByDate(Date date) {

        return reservationRepository.findByreservedDate(date);
        
    }


    @Override
    public String blockAndUnBlockStudentAccount(int studentId) {

        Student student=studentRepository.findById(studentId).get();

        if(student.getStudentDetails().getEnabled()==false){
            student.getStudentDetails().setEnabled(true);
            
        }
        else{
            student.getStudentDetails().setEnabled(false);
            
        }
        
        studentRepository.save(student);
        return "Student account status changed";
         }


    @Override
    public int getNumberOfStudentAccounts() {

        List<Integer> allStudents=studentRepository.findAllStudents();
        
        return allStudents.size();
        }


    @Override
    public int getNumberOfReservedSlotsForToday() {

        int reservedDuration=0;
        Date currentDate=Date.valueOf(LocalDate.now());
        List<CurrentReservations> reservations=reservationRepository.findByreservedDate(currentDate);

        for(CurrentReservations reservation:reservations){
            reservedDuration+=timeDiffereneceCalculator(reservation.getStartTime(), reservation.getEndTime());
        }

        return reservedDuration/30;
          }


    public int timeDiffereneceCalculator(String startTime,String endTime){
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime start=LocalTime.parse(startTime, formatter);
        LocalTime end=LocalTime.parse(endTime, formatter);
        Duration duration=Duration.between(start, end);
        
        return (int)duration.toMinutes();
    }


     public void generatePdfReport(Date reportDate) {
        
        List<CurrentReservations> reservations=reservationRepository.findByreservedDate(reportDate);
        

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream("report "+dateFormat.format(reportDate)+".pdf"));
            document.open();
            
            Font headerFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            Paragraph header = new Paragraph("Study Room Reservation System Report",headerFont);
            header.setAlignment(Element.ALIGN_CENTER);
            Paragraph date=new Paragraph("Date: "+dateFormat.format(reportDate));
            date.setAlignment(Element.ALIGN_RIGHT);
            document.add(header);
            document.add(new Paragraph("\n"));
            document.add(date);
            document.add(new Paragraph("\n"));
            PdfPTable table = new PdfPTable(5); // Number of columns
            table.addCell("Reservation ID");
            table.addCell("Date");
            table.addCell("Room Number");
            table.addCell("Student ID");
            table.addCell("Start & End Time");

            
            for(CurrentReservations reservation:reservations){
                table.addCell(String.valueOf(reservation.getReservationId()));
                table.addCell(dateFormat.format(reservation.getReservedDate()));
                table.addCell(String.valueOf(reservation.getRoomNumber()));
                table.addCell(String.valueOf(reservation.getStudent().getStudentId()));
                table.addCell(reservation.getStartTime()+" - "+reservation.getEndTime());
            }

            
            document.add(table);
            
            Date currentDate=Date.valueOf(LocalDate.now());
            Paragraph footer=new Paragraph("Report Generated on "+dateFormat.format(currentDate));
            footer.setAlignment(Element.ALIGN_BOTTOM);
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   
    
}


