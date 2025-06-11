// document.addEventListener("DOMContentLoaded", function() {
//     // Przykładowe dane wniosków
//     const wnioski = [
//         { id: 1, uzytkownik: { login: "JanKowalski" }, fileUri: "https://example.com/file1", fileName: "Wniosek1", status: "Oczekujący", komentarzDoWniosku: "Brak" },
//         { id: 2, uzytkownik: { login: "AnnaNowak" }, fileUri: "https://example.com/file2", fileName: "Wniosek2", status: "Zaakceptowany", komentarzDoWniosku: "Wszystko w porządku" },
//         { id: 3, uzytkownik: { login: "PiotrWiśniewski" }, fileUri: "https://example.com/file3", fileName: "Wniosek3", status: "Odrzucony", komentarzDoWniosku: "Nie spełnia wymagań" }
//     ];
//
//     const wnioskiList = document.getElementById("wnioskiList");
//
//     // Funkcja do renderowania wniosków
//     function renderWnioski() {
//         // Wyczyść tabelę przed renderowaniem
//         wnioskiList.innerHTML = "";
//
//         if (wnioski.length === 0) {
//             // Jeśli brak wniosków
//             const emptyRow = document.createElement("tr");
//             const emptyCell = document.createElement("td");
//             emptyCell.setAttribute("colspan", "5");
//             emptyCell.textContent = "Brak wniosków do wyświetlenia.";
//             emptyRow.appendChild(emptyCell);
//             wnioskiList.appendChild(emptyRow);
//             return;
//         }
//
//         wnioski.forEach((wniosek, index) => {
//             // Tworzenie wiersza w tabeli
//             const row = document.createElement("tr");
//
//             // Kolumna z loginem
//             const loginCell = document.createElement("td");
//             loginCell.textContent = wniosek.uzytkownik.login || 'Brak imienia';
//             row.appendChild(loginCell);
//
//             // Kolumna z nazwą pliku
//             const fileCell = document.createElement("td");
//             const fileLink = document.createElement("a");
//             fileLink.setAttribute("href", wniosek.fileUri);
//             fileLink.textContent = wniosek.fileName;
//             fileCell.appendChild(fileLink);
//             row.appendChild(fileCell);
//
//             // Kolumna ze statusem
//             const statusCell = document.createElement("td");
//             statusCell.textContent = wniosek.status;
//             row.appendChild(statusCell);
//
//             // Kolumna z komentarzem
//             const commentCell = document.createElement("td");
//             commentCell.textContent = wniosek.komentarzDoWniosku || 'Brak komentarza';
//             row.appendChild(commentCell);
//
//             // Kolumna z przyciskami
//             const buttonCell = document.createElement("td");
//             const acceptButton = document.createElement("button");
//             acceptButton.textContent = "Zaakceptuj";
//             acceptButton.onclick = () => przyjmijWniosek(index); // Przekazujemy index
//             const rejectButton = document.createElement("button");
//             rejectButton.textContent = "Odrzuć";
//             rejectButton.onclick = () => odrzucWniosek(index); // Przekazujemy index
//             buttonCell.appendChild(acceptButton);
//             buttonCell.appendChild(rejectButton);
//             row.appendChild(buttonCell);
//
//             // Dodanie wiersza do tabeli
//             wnioskiList.appendChild(row);
//         });
//     }
//
//     // Funkcja przyjmująca wniosek
//     function przyjmijWniosek(index) {
//         alert(`Wniosek ${wnioski[index].fileName} został przyjęty!`);
//         wnioski.splice(index, 1); // Usuwamy przyjęty wniosek
//         renderWnioski(); // Przeładowujemy widok
//     }
//
//     // Funkcja odrzucająca wniosek
//     function odrzucWniosek(index) {
//         alert(`Wniosek ${wnioski[index].fileName} został odrzucony!`);
//         wnioski.splice(index, 1); // Usuwamy odrzucony wniosek
//         renderWnioski(); // Przeładowujemy widok
//     }
//
//     // Na początek renderujemy wnioski
//     renderWnioski();
// });
//
const acceptBtn = document.querySelectorAll(".accept-btn")
const rejectBtn = document.querySelectorAll(".reject-btn")


console.log(acceptBtn)

acceptBtn.forEach(e=>{
    e.addEventListener('click',()=>{

        fetch(`http://localhost:5555/api/wnioski/${e.dataset.wniosekId}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'}
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Błąd pobierania wniosku: ${response.status}`);
                }
                return response.json();
            })
            .then(wniosekData => {

                wniosekData.status = "ZAAKCEPTOWANY";


                return fetch(`http://localhost:5555/api/wnioski/${e.dataset.wniosekId}/update`, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(wniosekData)
                });
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Błąd aktualizacji wniosku: ${response.status}`);
                }
                return response.json();
            })
            .then(updatedWniosek => {
                alert("Udalo sie zaaktualizowac wniosek")
                e.closest('tr').children[2].textContent = "ZAAKCEPTOWANY"
            })
            .catch(error => {
                console.error('Błąd:', error);
                alert('Nie udało się zaktualizować wniosku. Spróbuj ponownie.');
            });
    })
})

rejectBtn.forEach(e=>{
    e.addEventListener('click',()=>{

        fetch(`http://localhost:5555/api/wnioski/${e.dataset.wniosekId}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'}
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Błąd pobierania wniosku: ${response.status}`);
                }
                return response.json();
            })
            .then(wniosekData => {

                wniosekData.status = "ODRZUCONY";


                return fetch(`http://localhost:5555/api/wnioski/${e.dataset.wniosekId}/update`, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(wniosekData)
                });
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Błąd aktualizacji wniosku: ${response.status}`);
                }
                return response.json();
            })
            .then(updatedWniosek => {
                alert("Udalo sie zaaktualizowac wniosek")
                e.closest('tr').children[2].textContent = "ODRZUCONY"
            })
            .catch(error => {
                console.error('Błąd:', error);
                alert('Nie udało się zaktualizować wniosku. Spróbuj ponownie.');
            });
    })
})