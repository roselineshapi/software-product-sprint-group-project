
var userEmail;

function onSignIn(googleUser){
    var profile = googleUser.getBasicProfile();
    console.log('Email: ' + profile.getEmail());
    $("#email").text(profile.getEmail());
    userEmail = profile.getEmail();
    console.log(profile);
    window.location.href = "choices.html";
}
  
function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
      console.log('User signed out.');
      $("#email").remove();
      userEmail = null;
    });
   window.location.href = "index.html";
    
}

function getMessagesJSON(){
    fetch('/data').then(response => response.json()).then((post)=>{
      const volunteerListElement = document.getElementById('info',);
      console.log(post);
      volunteerListElement.innerHTML = '';
      post.forEach((post) =>{
        volunteerListElement.appendChild(createListElement(post.information));            
      });
    }); 
}


function createListElement(text){
  const liElement = document.createElement('li');
  liElement.innerText = text;
  return liElement;
}

var counter = 1;
var limit = 10;
let today = new Date().toISOString().substr(0,16);
function addInput(divName){
    if (counter == limit)  {
        alert("You have reached the limit of adding " + counter + " time availability inputs");
    }
    else {
        var newdiv = document.createElement('div');
        newdiv.innerHTML = `Entry ${counter+1} <br><input type='datetime-local' id='time_availability' name='time_availability' min='${today}' required>`
        document.getElementById(divName).appendChild(newdiv);
        counter++;
    }
}   

//Form object to store locally, a form retrieved from database.
function Form(title, capacity, postDate, expiryDate){
    this.title = title;
    this.capacity = capacity;
    this.postDate = postDate;
    this.expiryDate = expiryDate
}

const formListElement = document.getElementById('form-list');
const list = document.querySelector('#form-list ul');

var allOrgforms =[];
  // Load all forms for signed in org in database.
function loadOrgForms(){
    var data = {};
    data.email = userEmail;
    console.log(data.email);
    $.get("/list-org-forms", data).then(response => {
        response.forEach((form) => {
            allOrgforms.push(form);
            renderForm(form);
        })
    });
    return false;
}

var allforms = [];
  // Load all forms in database for volunteers.
  function loadVolunteerForms(){
    fetch('/list-all-forms').then(response => response.json()).then((forms) => {
    forms.forEach((form) => {
        allforms.push(form);
        renderForm(form);
    })
    });
}

function renderForm(form){
    const forms = document.forms;
    // create elements
    const li = document.createElement('li');
    const formName = document.createElement('span');
    const acceptBtn = document.createElement('span');

    // add text content
    formName.textContent = form.title;
    acceptBtn.textContent = 'accept';

    // add classes
    formName.classList.add('name');
    acceptBtn.classList.add('accept');

    // append to DOM
    li.appendChild(formName);
    li.appendChild(acceptBtn);
    list.appendChild(li);


    // faceted search logic
const searchBar = forms['search-forms'].querySelector('input');
searchBar.addEventListener('keyup', (e) => {
  const term = e.target.value.toLowerCase();
  const forms = list.getElementsByTagName('li');
  Array.from(forms).forEach((form) => {
    const title = form.firstElementChild.textContent;
    if(title.toLowerCase().indexOf(term) != -1){
      form.style.display = 'block';
    } else {
      form.style.display = 'none';
    }
  });
});
}

//once the accept span is clicked, get the form title and send an accept request.
//list.addEventListener('click', (e) => {
   // if(e.target.className == 'accept'){
    //  const li = e.target.parentElement; 
    //    var formTitle = String(li.textContent).replace('accept','');
    //    console.log(formTitle);
   //     var ownerEmail = getOwner(formTitle);
   //     acceptForm(formTitle, ownerEmail);
  //  }
 // });

function getOwner(formTitle){
    for(form in allforms){
        if(form.title.equals(formTitle)){
            return form.ownerEmail;
        }
    }
}

function acceptForm(formTitle, ownerEmail){
  var data = {};
  data.title = formTitle;
  data.ownerEmail = ownerEmail;
  data.email = userEmail;
  $.post("/accept-form", data).then(response => {
    console.log("SUCCESS: ", response);
  });
  return false;
}

function submitForm(){
  var data = { };
  data.title = document.getElementById("title").value;
  data.ownerName = document.getElementById("ownerName").value;
  data.ownerEmail = document.getElementById("ownerEmail").value;
  data.description = document.getElementById("description").value;
  data.capacity = document.getElementById("capacity").value;
  data.expiryDate = document.getElementById("expiryDate").value;
  data.sessionUrl = document.getElementById("sessionUrl").value;
  console.log(data);
    $.post("/new-form", data).then(response => {
      console.log("SUCCESS: ", response);
    });
}


