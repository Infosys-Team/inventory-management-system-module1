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
  .then(res => {
    if (!res.ok) throw new Error("Invalid credentials");
    return res.json();
  })
  .then(data => {
    localStorage.setItem(
      "currentUser",
      JSON.stringify({ email: data.username, role: data.role })
    );
    window.location = data.role === "admin"
      ? "admin.html"
      : "employee.html";
  })
  .catch(err => msg.innerHTML = "❌ " + err.message);
});


// ================= REGISTER =================
document.getElementById("regBtn")?.addEventListener("click", () => {

  fetch("http://localhost:8080/api/auth/register", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      email: ruser.value,
      password: rpass.value,
      role: rrole.value
    })
  })
  .then(res => res.text())
  .then(msg => rmsg.innerHTML = "✅ " + msg)
  .catch(() => rmsg.innerHTML = "❌ Registration failed");
});


// ================= LOGOUT =================
function logout() {
  localStorage.removeItem("currentUser");
  window.location = "login.html";
}


// ================= PRODUCT MODULE =================

// Load products on page load
window.addEventListener("load", loadProducts);

function loadProducts() {

  const table = document.getElementById("ptable");
  if (!table) return;

  fetch("http://localhost:8080/api/products/all")
    .then(res => res.json())
    .then(products => {
      table.innerHTML = "";

      products.forEach(p => {
        table.innerHTML += `
          <tr
            data-id="${p.id}"
            data-sku="${p.sku}"
            data-name="${p.name}"
            data-supplier="${p.supplier}"
            data-category="${p.category}"
            data-price="${p.price}"
            data-quantity="${p.quantity}"
          >
            <td>${p.sku}</td>
            <td>${p.name}</td>
            <td>${p.supplier}</td>
            <td>${p.category}</td>
            <td>${p.price}</td>
            <td>${p.quantity}</td>
            <td>
              <button onclick="editProduct(this)">Edit</button>
              <button onclick="deleteProduct(${p.id})">Delete</button>
              <button onclick="stockIn('${p.sku}')">Stock In</button>
              <button onclick="stockOut('${p.sku}', ${p.quantity})">Stock Out</button>
            </td>
          </tr>
        `;
      });
    });
}




// Add product
document.getElementById("addBtn")?.addEventListener("click", () => {

  const pmsg = document.getElementById("pmsg");

  const product = {
    sku: psku.value,
    name: pname.value,
    supplier: psupplier.value,
    category: pcat.value,
    price: pprice.value,
    quantity: pqty.value
  };

  fetch("http://localhost:8080/api/products/add", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(product)
  })
  .then(res => {
    if (!res.ok) throw new Error();
    return res.json();
  })
  .then(() => {
    pmsg.innerHTML = "✅ Product added successfully";
    loadProducts();
  })
  .catch(() => {
    pmsg.innerHTML = "❌ Failed to add product";
  });
});

// DELETE PRODUCT
function deleteProduct(id) {

  if (!confirm("Delete this product?")) return;

  fetch(`http://localhost:8080/api/products/delete/${id}`, {
    method: "DELETE"
  })
  .then(() => {
    loadProducts();
  })
  .catch(() => alert("Delete failed"));
}


// EDIT PRODUCT (simple prompt-based)
function editProduct(btn) {

  const row = btn.closest("tr");

  // safely read existing values
  const id = row.dataset.id;
  const sku = row.dataset.sku;

  const oldName = row.dataset.name || "";
  const oldSupplier = row.dataset.supplier || "";
  const oldCategory = row.dataset.category || "";
  const oldPrice = row.dataset.price || "";
  const oldQuantity = row.dataset.quantity || "";

  // prompts (one by one, stable)
  const name = prompt("Product Name:", oldName);
  if (name === null) return;

  const supplier = prompt("Supplier:", oldSupplier);
  if (supplier === null) return;

  const category = prompt("Category:", oldCategory);
  if (category === null) return;

  const price = prompt("Price:", oldPrice);
  if (price === null) return;

  const quantity = prompt("Quantity:", oldQuantity);
  if (quantity === null) return;

  fetch("http://localhost:8080/api/products/update", {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      id: id,
      sku: sku,
      name: name,
      supplier: supplier,
      category: category,
      price: price,
      quantity: quantity
    })
  })
  .then(res => {
    if (!res.ok) throw new Error();
    loadProducts();
  })
  .catch(() => alert("❌ Update failed"));
}

function stockIn(sku) {

  const qty = prompt("Enter quantity to ADD:");
  if (!qty || qty <= 0) return;

  const user = JSON.parse(localStorage.getItem("currentUser"));

  fetch("http://localhost:8080/api/products/stock-in", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      sku: sku,
      qty: qty,
      role: user.role
    })
  })
  .then(res => {
    if (!res.ok) throw new Error();
    loadProducts();
  })
  .catch(() => alert("❌ Stock-In failed"));
}

function stockOut(sku, currentQty) {

  const qty = prompt("Enter quantity to REMOVE:");
  if (!qty || qty <= 0) return;

  if (parseInt(qty) > currentQty) {
    alert("❌ Not enough stock");
    return;
  }

  const user = JSON.parse(localStorage.getItem("currentUser"));

  fetch("http://localhost:8080/api/products/stock-out", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      sku: sku,
      qty: qty,
      role: user.role
    })
  })
  .then(res => {
    if (!res.ok) throw new Error();
    loadProducts();
  })
  .catch(() => alert("❌ Stock-Out failed"));
}


