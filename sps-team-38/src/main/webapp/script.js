
var userEmail;

function onSignIn(googleUser){
    console.log("here");
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

