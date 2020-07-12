
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
  