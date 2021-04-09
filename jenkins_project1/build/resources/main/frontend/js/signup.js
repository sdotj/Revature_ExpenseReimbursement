window.onload = function () {
    getSignupStatus();
}

async function getSignupStatus() {
    const responsePayload = await fetch(`http://localhost:9001/api/get/users/feedback`);
    console.log(responsePayload);

    let ourJSON = await responsePayload.json();
    console.log(ourJSON);

    if(ourJSON == "passwordMismatch") {
        invalidPass();
    }

    if(ourJSON == "userExists") {
        invalidUser();
    }
}

function invalidPass() {
    let loginInfo = document.getElementById("loginInfo");
    let loginText = document.createElement("p");
    let loginDesc = document.createTextNode("Passwords don't match. Try again.");
    loginText.appendChild(loginDesc);
    loginInfo.appendChild(loginText);

    let form = document.createElement("signUpform");
    let height = form.offsetHeight;
    let newHeight = height + 770;
    form.style.height = newHeight + 'px';
}

function invalidUser() {
    let loginInfo = document.getElementById("loginInfo");
    let loginText = document.createElement("p");
    let loginDesc = document.createTextNode("User already exists in system. Try again.");
    loginText.appendChild(loginDesc);
    loginInfo.appendChild(loginText);

    let form = document.createElement("signUpform");
    let height = form.offsetHeight;
    console.log(height);
    let newHeight = height + 770;
    form.style.height = newHeight + 'px';
}