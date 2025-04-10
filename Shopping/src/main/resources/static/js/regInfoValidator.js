function error() {
    const phone = document.getElementById('phoneNumber').value;
    const firstName = document.getElementById('firstName').value;
    const lastName = document.getElementById('lastName').value;
    const regex = new RegExp('\\d{10}');

    if (!regex.test(phone)) {
        alert("Phone number must be 10 digits long!");
    }

    if (firstName.length < 2 || firstName.length > 30) {
        alert("First name should be at least 2 letters long");
    }

    if (lastName.length < 2 || lastName.length > 30) {
        alert("Last name should be at least 2 letters long");
    }
}