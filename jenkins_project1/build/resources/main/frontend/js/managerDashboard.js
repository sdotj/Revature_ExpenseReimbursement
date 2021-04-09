window.onload = function () {
    getCurrentUser();
    getAllReimbursements();
}

let allReimbursements = null;
let filteredReims = [];
let approveFormList = [];
let currentUserID = 0;

document.getElementById("logout").addEventListener("click", async function() {
    console.log("logging out");
    const responsePayload = await fetch(`http://localhost:9001/api/get/users/logout`);
    //console.log(responsePayload);
    location.reload();
});

async function getCurrentUser() {
    const responsePayload = await fetch(`http://localhost:9001/api/get/users/currentUser`);
    //console.log(responsePayload);

    let ourJSON = await responsePayload.json();
    console.log(ourJSON);
    let username = ourJSON.username;
    let welcome = document.getElementById("userWelcome");
    currentUserID = ourJSON.userId;
    welcome.innerText = "Welcome, " + username + "!";
}

async function getAllReimbursements() {
    const responsePayload = await fetch(`http://localhost:9001/api/get/reimbursements/getAllManager`);
    //console.log(responsePayload);

    ourJSON = await responsePayload.json();
    console.log(ourJSON);
    allReimbursements = ourJSON;
    appendReimbursements(ourJSON);
}

function appendReimbursements(reimbursementList) {
    let ourTable = document.getElementById("tableData");
    for(var i = 0; i<reimbursementList.length; i++) {
        //console.log(i);
        //console.log(reimbursementList[i].reimbursementId);
        let newRow = ourTable.insertRow(i);

        let col1 = newRow.insertCell(0);
        col1.innerHTML = ("Reim0" + reimbursementList[i].reimbursementId);

        let col2 = newRow.insertCell(1);
        let amount = reimbursementList[i].amount.toFixed(2);
        col2.innerHTML = ("$" + amount);

        let col3 = newRow.insertCell(2);
        let typeString = getType(reimbursementList[i].type);
        col3.innerHTML = typeString;

        let col4 = newRow.insertCell(3);
        col4.innerHTML = reimbursementList[i].submitDate;

        let col5 = newRow.insertCell(4);
        if(reimbursementList[i].resolveDate == null)
            col5.innerHTML = "-";
        else
            col5.innerHTML = reimbursementList[i].resolveDate;

        let col6 = newRow.insertCell(5);
        col6.innerHTML = reimbursementList[i].description;

        let col7 = newRow.insertCell(6);
        let statusString = getStatus(reimbursementList[i].status);
        col7.innerHTML = statusString;

        let col8 = newRow.insertCell(7);
        if(reimbursementList[i].status == 1) {
            
            col8.innerHTML = `<form id='approve${i}' method='POST' style='display: inline;' action='http://localhost:9001/api/post/reimbursements/approve'><button class='btn btn-success' type='submit'>Approve</button></form><form id='deny${i}' method='POST' style='display: inline; margin-left: 8px;' action='http://localhost:9001/api/post/reimbursements/deny'><button class='btn btn-danger'>Deny</button></form>`;
            
            let currentForm = document.getElementById(`approve${i}`);
            let idField = document.createElement("input");
            idField.type = "text";
            idField.value = reimbursementList[i].reimbursementId;
            idField.style.display = "none";
            idField.name = "ID"; 

            let resolverField = document.createElement("input");
            resolverField.type = "text";
            resolverField.value = currentUserID;
            resolverField.style.display = "none";
            resolverField.name = "resolver";
            
            currentForm.appendChild(idField);
            currentForm.appendChild(resolverField);

            let currentForm2 = document.getElementById(`deny${i}`);
            let idField2 = document.createElement("input");
            idField2.type = "text";
            idField2.value = reimbursementList[i].reimbursementId;
            idField2.style.display = "none";
            idField2.name = "ID"; 

            let resolverField2 = document.createElement("input");
            resolverField2.type = "text";
            resolverField2.value = currentUserID;
            resolverField2.style.display = "none";
            resolverField2.name = "resolver";

            currentForm2.appendChild(idField2);
            currentForm2.appendChild(resolverField2);
        }
        else{
            col8.innerHTML = "-";
        }
        //tableBody.appendChild(newRow);
    }
}

function getStatus(status) {
    if(status == 1)
        return "Pending";
    if(status == 2)
        return "Approved";
    if(status == 3)
        return "Denied";
}

function getType(type) {
    if(type == 1)
        return "Lodging";
    else if(type == 2)
        return "Travel";
    else if(type == 3)
        return "Food";
    else
        return "Other";
}

function openTable(evt, tableName) {
    // Declare all variables
    let ourTable = document.getElementById("tableData");
    ourTable.innerHTML = "";
    let checkboxDiv = document.getElementById("checkboxes");
    checkboxDiv.innerHTML = "";

    if(tableName == "all") {
        appendReimbursements(allReimbursements);
    }
    else if(tableName == "type") {
        applyTypeFilter();
    }
    else {
        applyStatusFilter();
    }
}

function applyTypeFilter() {
    appendReimbursements(allReimbursements);

    let container = document.getElementById("checkboxes");
    let filterForm = document.createElement("form");
    filterForm.id = "filterForm";

    let label1 = document.createElement("label");
    let input1 = document.createElement("input");
    input1.id = "lodging";
    input1.setAttribute("type", "checkbox");
    label1.innerHTML = "Lodging";
    label1.setAttribute("style", "margin:0 5px 0 10px;");
    filterForm.appendChild(label1);
    filterForm.appendChild(input1);

    let label2 = document.createElement("label");
    let input2 = document.createElement("input");
    input2.id = "travel";
    input2.setAttribute("type", "checkbox");
    label2.innerHTML = "Travel";
    label2.setAttribute("style", "margin:0 5px 0 10px");
    filterForm.appendChild(label2);
    filterForm.appendChild(input2);

    let label3 = document.createElement("label");
    let input3 = document.createElement("input");
    input3.id = "food";
    input3.setAttribute("type", "checkbox");
    label3.innerHTML = "Food";
    label3.setAttribute("style", "margin:0 5px 0 10px");
    filterForm.appendChild(label3);
    filterForm.appendChild(input3);

    let label4 = document.createElement("label");
    let input4 = document.createElement("input");
    input4.id = "other";
    input4.setAttribute("type", "checkbox");
    label4.innerHTML = "Other";
    label4.setAttribute("style", "margin:0 5px 0 10px");
    filterForm.appendChild(label4);
    filterForm.appendChild(input4);

    filterForm.addEventListener('change', typeChange);

    container.appendChild(filterForm);
    
}

function typeChange() {
    filteredReims = [];
    let ourTable = document.getElementById("tableData");
    ourTable.innerHTML = "";
    //console.log(document.getElementById("lodging").checked);
    let lodgingValue = document.getElementById("lodging").checked;
    let travelValue = document.getElementById("travel").checked;
    let foodValue = document.getElementById("food").checked;
    let otherValue = document.getElementById("other").checked;
    for(var i = 0; i < allReimbursements.length; i ++) {
        if(lodgingValue) {
            if(allReimbursements[i].type == 1) {
                filteredReims.push(allReimbursements[i]);
            }
        }
        if(travelValue) {
            if(allReimbursements[i].type == 2) {
                filteredReims.push(allReimbursements[i]);
            }
        }
        if(foodValue) {
            if(allReimbursements[i].type == 3) {
                filteredReims.push(allReimbursements[i]);
            }
        }
        if(otherValue) {
            if(allReimbursements[i].type == 4) {
                filteredReims.push(allReimbursements[i]);
            }
        }
    }

    for(var j = 0; j<filteredReims.length; j++) {
        if(!lodgingValue) {
            if(filteredReims.type == 1){
                filteredReims.splice(j, 1);
            }
        }
        if(!travelValue) {
            if(filteredReims.type == 2){
                filteredReims.splice(j, 1);
            }
        }
        if(!foodValue) {
            if(filteredReims.type == 3){
                filteredReims.splice(j, 1);
            }
        }
        if(!otherValue) {
            if(filteredReims.type == 4){
                filteredReims.splice(j, 1);
            }
        }
    }

    if(!lodgingValue && !travelValue && !foodValue && !otherValue) {
        filteredReims = allReimbursements;
    }

    appendReimbursements(filteredReims);
}

function applyStatusFilter() {
    appendReimbursements(allReimbursements);

    let container = document.getElementById("checkboxes");
    let filterForm = document.createElement("form");
    filterForm.id = "filterForm";

    let label1 = document.createElement("label");
    let input1 = document.createElement("input");
    input1.id = "pending";
    input1.setAttribute("type", "checkbox");
    label1.innerHTML = "Pending";
    label1.setAttribute("style", "margin:0 5px 0 10px;");
    filterForm.appendChild(label1);
    filterForm.appendChild(input1);

    let label2 = document.createElement("label");
    let input2 = document.createElement("input");
    input2.id = "approved";
    input2.setAttribute("type", "checkbox");
    label2.innerHTML = "Approved";
    label2.setAttribute("style", "margin:0 5px 0 10px");
    filterForm.appendChild(label2);
    filterForm.appendChild(input2);

    let label3 = document.createElement("label");
    let input3 = document.createElement("input");
    input3.id = "denied";
    input3.setAttribute("type", "checkbox");
    label3.innerHTML = "Denied";
    label3.setAttribute("style", "margin:0 5px 0 10px");
    filterForm.appendChild(label3);
    filterForm.appendChild(input3);

    filterForm.addEventListener('change', statusChange);

    container.appendChild(filterForm); 
}

function statusChange() {
    filteredReims = [];
    let ourTable = document.getElementById("tableData");
    ourTable.innerHTML = "";
    //console.log(document.getElementById("lodging").checked);
    let pendingValue = document.getElementById("pending").checked;
    let approvedValue = document.getElementById("approved").checked;
    let deniedValue = document.getElementById("denied").checked;
    for(var i = 0; i < allReimbursements.length; i ++) {
        if(pendingValue) {
            if(allReimbursements[i].status == 1) {
                filteredReims.push(allReimbursements[i]);
            }
        }
        if(approvedValue) {
            if(allReimbursements[i].status == 2) {
                filteredReims.push(allReimbursements[i]);
            }
        }
        if(deniedValue) {
            if(allReimbursements[i].status == 3) {
                filteredReims.push(allReimbursements[i]);
            }
        }
    }

    for(var j = 0; j<filteredReims.length; j++) {
        if(!pendingValue) {
            if(filteredReims.status == 1){
                filteredReims.splice(j, 1);
            }
        }
        if(!approvedValue) {
            if(filteredReims.status == 2){
                filteredReims.splice(j, 1);
            }
        }
        if(!deniedValue) {
            if(filteredReims.status == 3){
                filteredReims.splice(j, 1);
            }
        }
    }

    if(!pendingValue && !approvedValue && !deniedValue) {
        filteredReims = allReimbursements;
    }

    appendReimbursements(filteredReims);
}



