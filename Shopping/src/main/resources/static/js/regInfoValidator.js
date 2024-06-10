function error() {
    const phone = document.getElementById('phoneNumber').value;
    const firstName = document.getElementById('firstName').value;
    const lastName = document.getElementById('lastName').value;
    const regex = new RegExp('^[0-9]{10}$');

    if (!regex.test(phone)) {
        alert("Error! Phone must be length 10 digits!");
    }

    if (firstName.length < 3 || firstName.length > 10) {
        alert("Error! Wrong first name!");
    }

    if (lastName.length < 3 || lastName.length > 10) {
        alert("Error! Wrong last name!");
    }
}