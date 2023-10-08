$(document).ready(function() {

	$("#endTime").on("change", function() {
	 
	 var startTime=$("#startTime").val();
   	 var endTime = $(this).val();
   	 var reservedDate=$("#reservedDate").val();
   	 if(endTime<startTime){
   	 let endTimeElement=document.getElementById("endTime");
   	 endTimeElement.value=startTime+1;
   	 }
   	   $.ajax({
      url: '/student/checkRoomAvailability', // Replace with your server endpoint URL
      type: 'GET', // HTTP method (GET, POST, etc.)
      dataType: 'json', // Expected data type of the response
      data:{startTime: startTime,endTime: endTime,reservedDate: reservedDate },
      success: function(data) {
        
      for (const room of data) {
  	let roomNo=document.getElementById(room);
  	console.log(roomNo);
  	roomNo.disabled=false;
	}
       
        
        
        
      }
     
    });
    });




  $("#reservationform").submit(function(event) {
    	
    event.preventDefault(); // Prevent the default form submission behavior
    
    var reservation = $(this).serialize(); // Serialize form data
    
    $.ajax({
      url: '/student/reserve', // Replace with your server endpoint URL
      type: 'POST', // HTTP method (GET, POST, etc.)
      dataType: 'text', // Expected data type of the response
      data: reservation,
      success: function(data) {
        let reservationMessage = document.getElementById("reservationMessage");
        reservationMessage.textContent=data;
        
        if(data==="Room reservation Successful !"){        
        reservationMessage.style.color="green";
        }
        else{
        reservationMessage.style.color="red";
        }
        
        
        
      }
     
    });
  });
});
