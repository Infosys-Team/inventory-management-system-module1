// LOGIN
document.getElementById("loginBtn")?.addEventListener("click", () => {
  fetch("http://localhost:8080/api/auth/login", {
    method: "POST",
    headers: {"Content-Type":"application/json"},
    body: JSON.stringify({
      username: user.value,
      password: pass.value,
      role: role.value
    })
  })
  .then(r => r.json())
  .then(d => {
    localStorage.setItem("currentUser",
      JSON.stringify({ email: d.username, role: d.role })
    );
    window.location = d.role === "admin" ? "admin.html" : "employee.html";
  })
  .catch(() => msg.innerHTML="❌ Invalid credentials");
});

// REGISTER
document.getElementById("regBtn")?.addEventListener("click", () => {
  fetch("http://localhost:8080/api/auth/register", {
    method: "POST",
    headers: {"Content-Type":"application/json"},
    body: JSON.stringify({
      email: ruser.value,
      password: rpass.value,
      role: rrole.value
    })
  })
  .then(() => rmsg.innerHTML="✅ Registered")
  .catch(() => rmsg.innerHTML="❌ Failed");
});

// LOGOUT
function logout(){
  localStorage.removeItem("currentUser");
  window.location="login.html";
}
