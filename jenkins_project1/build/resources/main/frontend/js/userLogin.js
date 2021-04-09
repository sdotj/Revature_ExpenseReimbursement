window.onload = function () {
    getLoginStatus();
}

async function getLoginStatus() {
    const responsePayload = await fetch(`http://localhost:9001/api/get/users/feedback`);
    console.log(responsePayload);

    let ourJSON = await responsePayload.json();
    console.log(ourJSON);

    if(ourJSON == "invalidPassword") {
        invalidPass();
    }

    if(ourJSON == "invalidUser") {
        invalidUser();
    }

}

function invalidPass() {
    let loginInfo = document.getElementById("loginInfo");
    let loginText = document.createElement("p");
    let loginDesc = document.createTextNode("Incorrect Password. Try again.");
    loginText.appendChild(loginDesc);
    loginInfo.appendChild(loginText);
}

function invalidUser() {
    let loginInfo = document.getElementById("loginInfo");
    let loginText = document.createElement("p");
    let loginDesc = document.createTextNode("User does not exist. Retry or register as a new user.");
    loginText.appendChild(loginDesc);
    loginInfo.appendChild(loginText);
}