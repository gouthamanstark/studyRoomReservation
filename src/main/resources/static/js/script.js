 var now = new Date();
    var year = now.getFullYear();
    var month = String(now.getMonth() + 1).padStart(2, '0');
    var day = String(now.getDate()).padStart(2, '0');
    

    // Set the 'min' attribute for the input
    var minDate = year + '-' + month + '-' + day;
    var minTime="07:00";
    var maxTime="18:30";
    document.getElementById('reservedDate').min = minDate;
    
    var currentDate=new Date(minDate);
    var reservationDate=new Date(document.getElementById("reservedDate"));
    
    if(reservationDate===currentDate){
    var currentHour=String(now.getHours()).padStart(2, "0");
    var currentMinute=String(now.getMinutes()).padStart(2, "0");
    const currentTime = `${currentHour}:${currentMinute}`;
    
     document.getElementById('startTime').min=currentTime;
    document.getElementById('endTime').min=currentTime;
    document.getElementById('startTime').max=maxTime;
    document.getElementById('endTime').max=maxTime;
    }
    else{

 document.getElementById('startTime').min=minTime;
    document.getElementById('endTime').min=minTime;
    document.getElementById('startTime').max=maxTime;
    document.getElementById('endTime').max=maxTime;
}


    

    
    
   
