window.onload = function () {
    getCurrentUser();
}

document.getElementById("logout").addEventListener("click", async function() {
    console.log("logout clicked");
    const responsePayload = await fetch(`http://localhost:9001/api/get/users/logout`);
    //console.log(responsePayload);
    location.reload();
});

async function getCurrentUser() {
    const responsePayload = await fetch(`http://localhost:9001/api/get/users/currentUser`);
    //console.log(responsePayload);

    let ourJSON = await responsePayload.json();
    console.log(ourJSON);
}