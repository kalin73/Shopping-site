document.querySelector('.card-number-input').oninput = () => {
    document.querySelector('.card-number-box').innerText = document.querySelector('.card-number-input').value;
}

document.querySelector('.card-holder-input').oninput = () => {
    document.querySelector('.card-holder-name').innerText = document.querySelector('.card-holder-input').value;
}

document.querySelector('.month-input').oninput = () => {
    document.querySelector('.exp-month').innerText = document.querySelector('.month-input').value;
}

document.querySelector('.year-input').oninput = () => {
    document.querySelector('.exp-year').innerText = document.querySelector('.year-input').value;
}

document.querySelector('.cvv-input').onmouseenter = () => {
    document.querySelector('.front').style.transform = 'perspective(1000px) rotateY(-180deg)';
    document.querySelector('.back').style.transform = 'perspective(1000px) rotateY(0deg)';
}

document.querySelector('.cvv-input').onmouseleave = () => {
    document.querySelector('.front').style.transform = 'perspective(1000px) rotateY(0deg)';
    document.querySelector('.back').style.transform = 'perspective(1000px) rotateY(180deg)';
}

document.querySelector('.cvv-input').oninput = () => {
    document.querySelector('.cvv-box').innerText = document.querySelector('.cvv-input').value;
}


let tColorA = document.getElementById('tColorA'),
    tColorC = document.getElementById('tColorC'),
    iconA = document.querySelector('.fa-credit-card-alt'),
    iconC = document.querySelector('.fa-money'),
    cDetails = document.querySelector('.center')

function doFun() {
    tColorA.style.color = "green";
    tColorC.style.color = "#444";
    iconA.style.color = "green";
    iconC.style.color = "#444";
    cDetails.style.display = "block";
}

function doFunA() {
    tColorA.style.color = "#444";
    tColorC.style.color = "green";
    iconA.style.color = "#aaa";
    iconC.style.color = "green";
    cDetails.style.display = "none";
}

const cardNumber = document.getElementById("cardNumber");
const owner = document.getElementById("ownerName");
const month = document.getElementById("month");
const year = document.getElementById("year");
const cvcCode = document.getElementById("cvc");

function validate() {
    const btn = document.getElementById("submit-btn");
    btn.addEventListener('click', submit);
}

function submit(event) {
    event.preventDefault();

    if (cardNumber.value === "") {
        alert("Please insert card number!");
        return;
    }

    if (owner.value === "") {
        alert("Card holder name should not be empty!");
        return;
    }

    if (month.value === "") {
        alert("Please select expiration month!");
        return;
    }

    if (year.value === "") {
        alert("Please select expiration year!");
        return;
    }

    if (cvcCode.value === "") {
        alert("Please insert the three digit cvc code!");
        return;
    }
    alert("Your order has been approved!");
}