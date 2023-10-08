$.ajax({
    url: '/admin/checkNFCDeviceStatus', // Replace with your server endpoint URL
    type: 'GET', // HTTP method (GET, POST, etc.)
    dataType: 'json', // Expected data type of the response
    success: function(data) {
        // Handle the response data
        let error=document.getElementById("nfcError");
        if(data==false){
        error.textContent="NFC Device is not connected ! Please connect it and refresh the page to register the student !!";
        error.style.color="red";
        }
        else{
        error.textContent="NFC Device connected ! Please proceed with the registration !!";
        error.style.color="green";
        }
        
        console.log(data);
    }
});



const studentId=document.getElementById("studentId");
const firstName=document.getElementById("firstName"); 
const lastName=document.getElementById("lastName");
const email=document.getElementById("email");
const numberOfSemester=document.getElementById("studentDetails.numberOfSemester");
const department=document.getElementById("studentDetails.department");
const courseName=document.getElementById("studentDetails.courseName");
const roles=document.getElementById("studentDetails.roles");
const phoneNumber=document.getElementById("studentDetails.phoneNumber");
const address=document.getElementById("studentDetails.address");
const gender=document.getElementById("studentDetails.gender");
const nationality=document.getElementById("studentDetails.nationality");
const studentDetailsForm=document.getElementById("studentDetails");

let numberOfConditionsMet=0;
//Validates the studentID
studentId.addEventListener("input", function () {
           
if (/^\d+$/.test(studentId.value) && studentId.value.length>=6) {
            	
   studentId.style.backgroundColor="lightgreen";
   numberOfConditionsMet+=1;
   }
else{
   studentId.style.backgroundColor="white";
   }
            
});

//Validates the numberOfSemester
numberOfSemester.addEventListener("input", function () {
           
   if (/^\d+$/.test(numberOfSemester.value) && numberOfSemester.value.length==1) {
                  
      numberOfSemester.style.backgroundColor="lightgreen";
      numberOfConditionsMet+=1;
      }
   else{
      numberOfSemester.style.backgroundColor="white";
      }
               
   });

//Validates the phone number
phoneNumber.addEventListener("input", function () {
   
           
   if (/^(?:\+49|0)(?:\d{2,4})?\s?\d{1,5}[-\s]?\d{1,5}$/.test(phoneNumber.value) ) {
                  
      phoneNumber.style.backgroundColor="lightgreen";
      numberOfConditionsMet+=1;
      }
   else{
      phoneNumber.style.backgroundColor="white";
      }
               
   });




   //Validate the entered email address
email.addEventListener("input", function () {
           
   if (/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(email.value) && email.value.length>=6) {
                     
      email.style.backgroundColor="lightgreen";
      numberOfConditionsMet+=1;
      }
   else{
      email.style.backgroundColor="white";
      }
                  
   });



function validateString(id){
   
   if (/^[a-zA-Z\s]{3,}$/.test(id.value)) {
                  
      id.style.backgroundColor="lightgreen";
      numberOfConditionsMet+=1;
      }
   else{
      id.style.backgroundColor="white";
      }
};


//Validates the firstname 
firstName.addEventListener("input",function(){
   validateString(firstName);
} );
        
//Validates the lastName 
lastName.addEventListener("input", function(){
   validateString(lastName);
} );

//Validates the Department
department.addEventListener("input", function(){
   validateString(department);
} );

//Validates the Course Name
courseName.addEventListener("input", function(){
   validateString(courseName);
} );

//Validates the roles
roles.addEventListener("input",function(){
   validateString(roles);
} );

//Validates the address
address.addEventListener("input",function(){
   validateString(address);
} );

//Validates the gender
gender.addEventListener("input",function(){
   validateString(gender);
} );

//Validates the nationality
nationality.addEventListener("input",function(){
   validateString(nationality);
} );




