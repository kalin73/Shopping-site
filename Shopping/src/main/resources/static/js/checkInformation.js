window.addEventListener("load", checkInfo);

function checkInfo(){
    const fullName = document.getElementById("fullName");
    const phoneNumber = document.getElementById("phoneNumber");
    const address = document.getElementById("shippingAddress");
    const checkCard = document.getElementById("card");
    const checkCash = document.getElementById("cash");

    const allInfo = {
        fullName: fullName,
        phoneNumber: phoneNumber,
        address: address,
        checkCard: checkCard,
        checkCash: checkCash
    }

    const btn = document.getElementById("btn");
    btn.addEventListener('click', checkInput);

    function checkInput(event){
        event.preventDefault();

        if(!allInfo.fullName.value){
            alert("Please input your full name!");
        }

        if(!allInfo.phoneNumber.value){
            alert("Please input your phone number!");
        }

        if(!allInfo.address.value){
            alert("Please input shipping address!");
        }

        if(!allInfo.checkCard.checked && !allInfo.checkCash.checked){
            alert("Please check one of method to pay!");
        }
    }
}