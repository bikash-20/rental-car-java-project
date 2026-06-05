// Mobile nav toggle
function toggleNav() {
    document.getElementById('navLinks').classList.toggle('open');
}

// Live price preview on rent form
function updatePrice() {
    const select = document.getElementById('carId');
    const daysInput = document.getElementById('days');
    const preview = document.getElementById('pricePreview');
    const amount = document.getElementById('previewAmount');

    if (!select || !daysInput || !preview) return;

    const selected = select.options[select.selectedIndex];
    const price = parseFloat(selected?.dataset?.price || 0);
    const days = parseInt(daysInput.value || 0);

    if (price > 0 && days > 0) {
        amount.textContent = '$' + (price * days).toLocaleString('en-US', {minimumFractionDigits: 2});
        preview.style.display = 'flex';
    } else {
        preview.style.display = 'none';
    }
}

// Click car item in sidebar → auto-select in dropdown
function selectCar(carId, price) {
    const select = document.getElementById('carId');
    if (!select) return;
    select.value = carId;
    updatePrice();
    // scroll to form on mobile
    select.scrollIntoView({ behavior: 'smooth', block: 'center' });
}

// Pre-select carId from URL param (e.g. /rent?carId=C001)
document.addEventListener('DOMContentLoaded', () => {
    const params = new URLSearchParams(window.location.search);
    const carId = params.get('carId');
    if (carId) {
        const select = document.getElementById('carId');
        if (select) { select.value = carId; updatePrice(); }
    }
});
