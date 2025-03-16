document.addEventListener("DOMContentLoaded", function () {
    const wnioskiList = document.getElementById("wnioskiList");

    // Przykładowe dane wniosków
    const wnioski = [
        { imie: "Jan", nazwisko: "Kowalski", nazwa: "Wniosek o zwrot podatku" },
        { imie: "Anna", nazwisko: "Nowak", nazwa: "Wniosek o ulgę podatkową" },
        { imie: "Piotr", nazwisko: "Wiśniewski", nazwa: "Wniosek o rozłożenie na raty" }
    ];

    // Funkcja do renderowania wniosków
    function renderWnioski() {
        wnioskiList.innerHTML = "";
        wnioski.forEach((wniosek, index) => {
            const row = document.createElement("tr");
            row.innerHTML = `
                <td>${wniosek.imie}</td>
                <td>${wniosek.nazwisko}</td>
                <td>${wniosek.nazwa}</td>
                <td>
                    <button class="accept" onclick="przyjmijWniosek(${index})">Przyjmij</button>
                    <button class="reject" onclick="odrzucWniosek(${index})">Odrzuć</button>
                </td>
            `;
            wnioskiList.appendChild(row);
        });
    }

    // Funkcja do przyjęcia wniosku
    window.przyjmijWniosek = function (index) {
        alert(`Wniosek ${wnioski[index].nazwa} został przyjęty!`);
        wnioski.splice(index, 1);
        renderWnioski();
    };

    // Funkcja do odrzucenia wniosku
    window.odrzucWniosek = function (index) {
        alert(`Wniosek ${wnioski[index].nazwa} został odrzucony!`);
        wnioski.splice(index, 1);
        renderWnioski();
    };

    // Renderowanie wniosków na starcie
    renderWnioski();
});