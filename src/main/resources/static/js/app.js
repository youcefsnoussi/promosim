
// ========== Vehicle Deletion Modal ==========
let vehicleToDeleteId = null;

function showVehicleDeleteModal(id) {
    vehicleToDeleteId = id;
    fetch(`/vehicles/${id}/delete-confirmation`)
        .then(response => {
            if (!response.ok) throw new Error("Erreur de chargement du contenu du modal");
            return response.text();
        })
        .then(html => {
            document.getElementById('dynamicModalContainer').innerHTML = html;
            const deleteModal = new bootstrap.Modal(document.getElementById('deleteConfirmModal'));
            deleteModal.show();
        })
        .catch(error => console.error("Erreur lors du chargement du modal :", error));
}

function toggleVehicleSearchPanel() {
    const panel = document.getElementById('vehicleSearchPanel');
    panel.style.display = (panel.style.display === 'none') ? 'block' : 'none';
}

// ========== Driver Deletion Modal ==========
let driverToDeleteId = null;

function showDriverDeleteModal(id) {
    driverToDeleteId = id;
    fetch(`/drivers/${id}/delete-confirmation`)
        .then(response => {
            if (!response.ok) throw new Error("Erreur de chargement du contenu du modal");
            return response.text();
        })
        .then(html => {
            document.getElementById('dynamicModalContainer').innerHTML = html;
            const deleteModal = new bootstrap.Modal(document.getElementById('deleteDriverModal'));
            deleteModal.show();
        })
        .catch(error => console.error("Erreur lors du chargement du modal :", error));
}

function toggleDriverSearchPanel() {
    const panel = document.getElementById('driverSearchPanel');
    panel.style.display = (panel.style.display === 'none') ? 'block' : 'none';
}

// ========== Mission Deletion Modal ==========
let missionToDeleteId = null;

function showMissionDeleteModal(id) {
    missionToDeleteId = id;
    fetch(`/missions/${id}/delete-confirmation`)
        .then(response => {
            if (!response.ok) throw new Error("Erreur de chargement du contenu du modal");
            return response.text();
        })
        .then(html => {
            document.getElementById('dynamicModalContainer').innerHTML = html;
            const deleteModal = new bootstrap.Modal(document.getElementById('deleteMissionModal'));
            deleteModal.show();
        })
        .catch(error => console.error("Erreur lors du chargement du modal :", error));
}

function toggleMissionSearchPanel() {
    const panel = document.getElementById('missionSearchPanel');
    panel.style.display = (panel.style.display === 'none') ? 'block' : 'none';
}

// ========== Global Delete Confirmation ==========
document.addEventListener('DOMContentLoaded', () => {
    document.addEventListener('click', function (event) {
        if (event.target && event.target.id === 'confirmDeleteBtn') {
            if (vehicleToDeleteId !== null) {
                window.location.href = `/vehicles/delete/${vehicleToDeleteId}`;
            } else if (driverToDeleteId !== null) {
                window.location.href = `/drivers/delete/${driverToDeleteId}`;
            } else if (missionToDeleteId !== null) {
                window.location.href = `/missions/delete/${missionToDeleteId}`;
            }
        }
    });
});
