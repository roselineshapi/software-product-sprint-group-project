
var userEmail;

function onSignIn(googleUser){
    var profile = googleUser.getBasicProfile();
    console.log('Email: ' + profile.getEmail());
    $("#email").text(profile.getEmail());
    userEmail = profile.getEmail();
    console.log(profile);
  }
  
function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
      console.log('User signed out.');
      $("#email").remove();
      userEmail = null;
    });
  }

  function getMessagesJSON(){
    fetch('/data').then(response => response.json()).then((post)=>{
      const volunteerListElement = document.getElementById('Name',);
      console.log(post);
      volunteerListElement.innerHTML = '';
      post.forEach((post) =>{
        volunteerListElement.appendChild(createListElement(post.Names));            
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

const formListElement = document.getElementById('form-list');
const list = document.querySelector('#form-list ul');
  // Load existing forms in database
function loadForms(){
    fetch('/list-forms').then(response => response.json()).then((forms) => {
    forms.forEach((form) => {
        formListElement.appendChild(renderForm(form));
    })
    });
}
// ................remove this section, just for reference....................
function renderForm(form){
    const forms = document.forms;
    // create elements
    const li = document.createElement('li');
    const formName = document.createElement('span');
    const applyBtn = document.createElement('span');

    // add text content
    formName.textContent = form.data().title;
    applyBtn.textContent = 'apply';

    // add classes
    formName.classList.add('name');
    applyBtn.classList.add('apply');

    // append to DOM
    li.appendChild(formName);
    li.appendChild(applyBtn);
    list.appendChild(li);
    
    //TODO add feature to hide forms
}

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
