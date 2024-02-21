window.addEventListener("load", checkInfo);

function checkInfo() {
    const fullName = document.getElementById("fullName");
    const phoneNumber = document.getElementById("phoneNumber");
    const shippingAddress = document.getElementById("shippingAddress");
    const checkCard = document.getElementById("card");
    const checkCash = document.getElementById("cash");

    const allInfo = {
        fullName: fullName,
        phoneNumber: phoneNumber,
        shippingAddress: shippingAddress,
        checkCard: checkCard,
        checkCash: checkCash
    }

    const btn = document.getElementById("btn");
    btn.addEventListener('click', checkInput);

    function checkInput(event) {
        event.preventDefault();

        if (!allInfo.fullName.value) {
            alert("Please input your full name!");
            return;
        }

        if (!allInfo.phoneNumber.value) {
            alert("Please input your phone number!");
            return;
        }

        if (!allInfo.shippingAddress.value) {
            alert("Please input shipping address!");
            return;
        }

        if (!allInfo.checkCard.checked && !allInfo.checkCash.checked) {
            alert("Please check one of method to pay!");
            return;
        }

        checkCashInfo(allInfo);
    }

    function checkCashInfo(fullInformation) {
        let payMethod = allInfo.checkCash.checked ? fullInformation.checkCash.value : fullInformation.checkCard.value

        fetch("http://localhost:8080/order/placeOrder", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                "fullName": fullInformation.fullName.value,
                "phoneNumber": fullInformation.phoneNumber.value,
                "shippingAddress": fullInformation.shippingAddress.value,
                "paymentMethod": payMethod
            })
        })
            .then(response => response.json());

        alert("Your order has been approved!");
        location.replace("http://localhost:8080");

    }

}