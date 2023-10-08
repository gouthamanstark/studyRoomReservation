$(document).ready(function() {
  $("#emailform").submit(function(event) {
    	
    event.preventDefault(); // Prevent the default form submission behavior
    
    var email = $(this).serialize(); // Serialize form data
    
    $.ajax({
      url: '/passwordResetConfirmation', // Replace with your server endpoint URL
      type: 'POST', // HTTP method (GET, POST, etc.)
      dataType: 'json', // Expected data type of the response
      data: email,
      success: function(data) {
        let error = document.getElementById("accountError");
        if (data === true) {
        
          error.textContent="Password reset link sent to your registered email address! Please check your inbox for password reset instructions.";
          error.style.color="green";
        } else {
          error.textContent="User Account not found! Please provide a valid email address.";
          error.style.color="red";
        }
        
        
      }
     
    });
  });
});
