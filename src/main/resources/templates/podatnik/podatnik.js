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
