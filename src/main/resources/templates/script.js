const API_BASE = "http://localhost:8080/api";

async function fetchInvoices() {
  const res = await fetch(`${API_BASE}/invoices`);
  const invoices = await res.json();

  const table = `
    <table class="table table-bordered">
      <thead>
        <tr>
          <th>ID</th>
          <th>Client</th>
          <th>Date</th>
          <th>Total</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        ${invoices.map(inv => `
          <tr>
            <td>${inv.id}</td>
            <td>${inv.client.name}</td>
            <td>${inv.date}</td>
            <td>₹${inv.total}</td>
            <td>
              <a href="${API_BASE}/invoices/${inv.id}/pdf" target="_blank" class="btn btn-sm btn-outline-secondary">PDF</a>
              <button onclick="sendInvoice(${inv.id})" class="btn btn-sm btn-outline-success">Send Email</button>
            </td>
          </tr>
        `).join('')}
      </tbody>
    </table>
  `;

  document.getElementById("invoiceList").innerHTML = table;
}

async function sendInvoice(id) {
  const res = await fetch(`${API_BASE}/invoices/${id}/send`, { method: "POST" });
  const msg = await res.text();
  alert(msg);
}
