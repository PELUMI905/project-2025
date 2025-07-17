function reserveRoom(roomId) {
  // Implement reservation logic here
  alert(`Room ${roomId} reserved!`);
}

function submitReservation() {
  // Implement form submission logic here
  alert('Reservation submitted!');
  return false; // Prevent form submission for demonstration purposes
}

function loginUser() {
  // Implement login logic here
  alert('Login successful!');
  return false; // Prevent form submission for demonstration purposes
}

document.addEventListener("DOMContentLoaded", function() {
  // Switch to the login section if the URL contains "#login"
  if (window.location.hash === "#login") {
    document.getElementById("login").style.display = "block";
  }
});
