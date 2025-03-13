const apiUrl = 'http://localhost:8080/api/users'; // Zmień adres na swój backend

let users = [];

document.addEventListener("DOMContentLoaded", () => {
    // Pobieranie listy użytkowników z backendu
    fetchUsers();

    // Funkcja do pobierania użytkowników
    async function fetchUsers() {
        try {
            const response = await fetch(apiUrl);
            users = await response.json();
            renderUsers(users);
        } catch (error) {
            console.error('Błąd podczas pobierania użytkowników:', error);
        }
    }

    // Funkcja do renderowania listy użytkowników
    function renderUsers(users) {
        const userList = document.getElementById('userList');
        userList.innerHTML = '';

        users.forEach(user => {
            const userItem = document.createElement('li');
            userItem.textContent = `${user.login} - ${user.role}`;
            userList.appendChild(userItem);
        });
    }

    // Funkcja do filtrowania użytkowników na liście
    window.filterUsers = function() {
        const searchQuery = document.getElementById('search').value.toLowerCase();
        const filteredUsers = users.filter(user => user.login.toLowerCase().includes(searchQuery));
        renderUsers(filteredUsers);
    }

    // Funkcja do dodawania użytkownika
    window.addUser = async function(event) {
        event.preventDefault();

        const login = document.getElementById('login').value;
        const password = document.getElementById('password').value;
        const role = document.getElementById('role').value;

        const newUser = { login, password, role };

        try {
            const response = await fetch(apiUrl, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(newUser)
            });

            if (response.ok) {
                alert('Użytkownik dodany!');
                fetchUsers();  // Odświeżenie listy po dodaniu użytkownika
            } else {
                alert('Błąd podczas dodawania użytkownika');
            }
        } catch (error) {
            console.error('Błąd podczas dodawania użytkownika:', error);
        }
    }
});
