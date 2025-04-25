document.addEventListener("DOMContentLoaded", function () {
    const uploadForm = document.getElementById("uploadForm");

    uploadForm.addEventListener("submit", function (event) {
        event.preventDefault();

        const imie = document.getElementById("imie").value;
        const nazwisko = document.getElementById("nazwisko").value;
        const plikInput = document.getElementById("wniosekPlik");
        const plik = plikInput.files[0];

        if (!plik) {
            alert("Wybierz plik PDF przed wysłaniem!");
            return;
        }

        const formData = new FormData();
        formData.append("imie", imie);
        formData.append("nazwisko", nazwisko);
        formData.append("wniosekPlik", plik);

        // Symulacja wysyłki - tutaj można dodać fetch do serwera
        console.log("Wysłano wniosek:", {
            imie,
            nazwisko,
            plik: plik.name
        });

        alert("Wniosek został wysłany!");
        uploadForm.reset();
    });
});

document.getElementById('uploadForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const formData = new FormData();
    const fileInput = document.getElementById('wniosekPlik');
    const uzytkownikId = document.getElementById('uzytkownikId').value;
    const komentarz = document.getElementById('komentarz').value;

    formData.append('file', fileInput.files[0]);
    formData.append('uzytkownikId', uzytkownikId);
    formData.append('komentarz', komentarz);

    try {
        const response = await fetch('/api/wnioski/create', {
            method: 'POST',
            body: formData
        });

        if (response.ok) {
            const data = await response.json();
            alert(`Wniosek nr ${data.wniosekId} został złożony!`);
            window.location.reload();
        } else {
            const error = await response.text();
            alert(`Błąd: ${error}`);
        }
    } catch (error) {
        alert('Błąd połączenia z serwerem');
    }
});