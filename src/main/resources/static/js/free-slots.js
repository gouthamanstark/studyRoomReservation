let freeSlots=document.getElementById("freeSlots");
let numberOfFreeSlotsToday=document.getElementById("numberOfFreeSlotsToday");


let percentageOfFreeSlots=(numberOfFreeSlotsToday.textContent/690)*100;
console.log(percentageOfFreeSlots);
freeSlots.style.width=percentageOfFreeSlots+'%';

