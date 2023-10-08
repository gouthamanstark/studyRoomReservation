 const passwordInput = document.getElementById("password");
        const confirmPasswordInput = document.getElementById("confirmPassword");
        const passwordMismatchDiv = document.getElementById("passwordMismatch");
        const hasProperLength = document.getElementById("hasProperLength");
        const hasUppercase = document.getElementById("hasUppercase");
        const hasLowercase = document.getElementById("hasLowercase");
        const hasNumber = document.getElementById("hasNumber");
        const hasSpecialCharacter = document.getElementById("hasSpecialCharacter");
        const submitButton = document.getElementById("submitButton");
        submitButton.disabled = true;
        let numberOfConditionsMet=0;


        confirmPasswordInput.addEventListener("input", function () {
            const passwordsMatch = passwordInput.value === confirmPasswordInput.value;
            if (passwordsMatch) {
                passwordMismatchDiv.textContent = "Passwords Match";
                passwordMismatchDiv.style.color = "green";
                if(numberOfConditionsMet==5){
                
                submitButton.disabled = false;
                }
            } else {
                passwordMismatchDiv.textContent = "Passwords do not match!";
                passwordMismatchDiv.style.color = "red";
                submitButton.disabled = true;
            }
        });


        passwordInput.addEventListener("input", function () {
            if (passwordInput.value.length >= 8) {
               
                hasProperLength.style.color = "green";
                numberOfConditionsMet+=1;
            } 
            else{
            	hasProperLength.style.color="black";
            	numberOfConditionsMet-=1;
            }
            
            if(/[A-Z]/.test(passwordInput.value)){
            	hasUppercase.style.color="green";
            	numberOfConditionsMet+=1;
            }
            else{
            	hasUppercase.style.color="black";
            	numberOfConditionsMet-=1;
            }
            
            if(/[a-z]/.test(passwordInput.value)){
            	hasLowercase.style.color="green";
            	numberOfConditionsMet+=1;
            }
            else{
            	hasLowercase.style.color="black";
            	numberOfConditionsMet-=1;
            }
            
            if(/\d/.test(passwordInput.value)){
            	hasNumber.style.color="green";
            	numberOfConditionsMet+=1;
            }
            else{
            	hasNumber.style.color="black";
            	numberOfConditionsMet-=1;
            }
            
            if(/[!@#$%^&*()_+{}\[\]:;<>,.?~\\/\-="']/.test(passwordInput.value)){
            	hasSpecialCharacter.style.color="green";
            	numberOfConditionsMet+=1;
            }
            else{
            	hasSpecialCharacter.style.color="black";
            	numberOfConditionsMet-=1;
            }
            
        });
