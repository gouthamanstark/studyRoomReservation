package com.computerpool.library.repository;


import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.computerpool.library.entity.Student;

public interface StudentRepo extends JpaRepository<Student,Integer>{
    
   
  

 @Query("SELECT a FROM Student a JOIN FETCH a.studentDetails b WHERE b.activationToken = :activationToken")
  Optional<Student> findByactivationToken(String activationToken);

@Query("SELECT a.studentId from Student a")
 List<Integer> findAllStudents();
  
  @Query("SELECT a FROM Student a JOIN FETCH a.studentDetails b WHERE b.nfcTagUUID = :nfcTagUUID")
  Optional<Student> findBynfcTagUUID(String nfcTagUUID);

  Optional<Student> findByEmail(String email);

  


  
}
