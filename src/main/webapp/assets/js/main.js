// Auto-dismiss alerts after 5 seconds
document.addEventListener('DOMContentLoaded', function() {
    const alerts = document.querySelectorAll('.alert');
    alerts.forEach(function(alert) {
        setTimeout(function() {
            const bsAlert = new bootstrap.Alert(alert);
            bsAlert.close();
        }, 5000);
    });
});

// Donor Form Validation
function validateDonorForm() {
    const weight = parseFloat(document.getElementById('donorWeight').value);

    if (weight < 50) {
        alert('The minimum required weight is 50 kg.');
        return false;
    }

    if (weight > 200) {
        alert('The entered weight seems incorrect (max 200 kg).');
        return false;
    }

    // Check age
    const birthDate = new Date(document.getElementById('donorBirthDate').value);
    const today = new Date();
    const age = today.getFullYear() - birthDate.getFullYear();

    if (age < 18) {
        alert('The donor must be at least 18 years old.');
        return false;
    }

    if (age > 65) {
        alert('The donor must be under 65 years old.');
        return false;
    }

    return true;
}

// Recipient Form Validation
function validateRecipientForm() {
    // Check age
    const birthDate = new Date(document.getElementById('recipientBirthDate').value);
    const today = new Date();
    const age = today.getFullYear() - birthDate.getFullYear();

    if (age < 0 || age > 150) {
        alert('The entered birth date seems incorrect.');
        return false;
    }

    return true;
}

// Confirmation dialogs
function confirmDelete(name) {
    return confirm(`Are you sure you want to delete ${name}?`);
}

function confirmDissociate() {
    return confirm('Are you sure you want to dissociate this donor from the recipient?');
}

// Table Search (optional)
function searchTable(inputId, tableId) {
    const input = document.getElementById(inputId);
    const filter = input.value.toUpperCase();
    const table = document.getElementById(tableId);
    const tr = table.getElementsByTagName('tr');

    for (let i = 1; i < tr.length; i++) {
        let txtValue = tr[i].textContent || tr[i].innerText;
        if (txtValue.toUpperCase().indexOf(filter) > -1) {
            tr[i].style.display = '';
        } else {
            tr[i].style.display = 'none';
        }
    }
}

// Print functionality (optional)
function printTable() {
    window.print();
}
