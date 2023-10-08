const username=document.getElementById("username");
const password=document.getElementById("password");
const loginForm=document.getElementById("loginForm");
const error=document.getElementById("error");

loginForm.addEventListener("submit",formValidate)

function formValidate(event){


if(username.value.length>=6 && /^\d+$/.test(username.value) && password.value.length>=8 ){

return true;
}
else{

error.innerHTML="Please enter a valid username or password !";
error.style.color="red";
event.preventDefault();
return false;
}


}




