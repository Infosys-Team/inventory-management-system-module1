// ================= LOGIN =================
document.getElementById("loginBtn")?.addEventListener("click", () => {

  const user = document.getElementById("user");
  const pass = document.getElementById("pass");
  const role = document.getElementById("role");
  const msg  = document.getElementById("msg");

  if (!user.value || !pass.value || !role.value) {
    msg.innerHTML = "❌ All fields are required";
    return;
  }

  fetch("http://localhost:8080/api/auth/login", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      email: user.value,
      password: pass.value,
      role: role.value
    })
  })
  .then(async res => {
    if (!res.ok) {
      const err = await res.text();
      throw new Error(err);
    }
    return res.json();
  })
  .then(data => {
    localStorage.setItem(
      "currentUser",
      JSON.stringify({ email: data.username, role: data.role })
    );

    window.location =
      data.role === "admin" ? "admin.html" : "employee.html";
  })
  .catch(err => {
    msg.innerHTML = "❌ " + err.message;
  });
});


// ================= REGISTER =================
document.getElementById("regBtn")?.addEventListener("click", () => {

  const ruser = document.getElementById("ruser");
  const rpass = document.getElementById("rpass");
  const rrole = document.getElementById("rrole");
  const rmsg  = document.getElementById("rmsg");

  if (!ruser.value || !rpass.value || !rrole.value) {
    rmsg.innerHTML = "❌ All fields are required";
    return;
  }

  fetch("http://localhost:8080/api/auth/register", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      email: ruser.value,
      password: rpass.value,
      role: rrole.value
    })
  })
  .then(async res => {
    const msg = await res.text();
    if (!res.ok) throw new Error(msg);
    rmsg.innerHTML = "✅ " + msg;
  })
  .catch(err => {
    rmsg.innerHTML = "❌ " + err.message;
  });
});


// ================= LOGOUT =================
function logout() {
  localStorage.removeItem("currentUser");
  window.location = "login.html";
}
// ================= FORGOT PASSWORD =================

// SEND OTP
document.getElementById("otpBtn")?.addEventListener("click", () => {

  const email = document.getElementById("fuser").value;
  const info  = document.getElementById("info");

  if (!email) {
    info.innerHTML = "❌ Please enter email";
    return;
  }

  fetch("http://localhost:8080/api/auth/send-otp", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ email: email })
  })
  .then(async res => {
    const msg = await res.text();
    if (!res.ok) throw new Error(msg);

    info.innerHTML = "📩 OTP sent (check backend console)";
    document.getElementById("otpBox").style.display = "block";
  })
  .catch(err => {
    info.innerHTML = "❌ " + err.message;
  });
});


// RESET PASSWORD
document.getElementById("verifyBtn")?.addEventListener("click", () => {

  const email    = document.getElementById("fuser").value;
  const otp      = document.getElementById("otp").value;
  const newPass  = document.getElementById("newpass").value;
  const confPass = document.getElementById("confpass").value;
  const fmsg     = document.getElementById("fmsg");

  if (!otp || !newPass || !confPass) {
    fmsg.innerHTML = "❌ All fields are required";
    return;
  }

  if (newPass !== confPass) {
    fmsg.innerHTML = "❌ Passwords do not match";
    return;
  }

  fetch("http://localhost:8080/api/auth/reset-password", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      email: email,
      otp: otp,
      newPassword: newPass
    })
  })
  .then(async res => {
    const msg = await res.text();
    if (!res.ok) throw new Error(msg);

    fmsg.innerHTML = "✅ Password reset successful";
  })
  .catch(err => {
    fmsg.innerHTML = "❌ " + err.message;
  });
});
