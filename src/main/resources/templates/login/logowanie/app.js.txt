const loginForm = document.getElementById('loginForm');
const errorMessage = document.getElementById('errorMessage');

loginForm.addEventListener('submit', function(event) {
    event.preventDefault(); // Zapobiega wysłaniu formularza w tradycyjny sposób

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    const loginData = {
        username: username,
        password: password
    };

    fetch('http://localhost:8080/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(loginData)
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            // Jeśli odpowiedź zawiera "success"
            window.location.href = '/dashboard'; // Przekierowanie po zalogowaniu
        } else {
            errorMessage.textContent = 'Błędna nazwa użytkownika lub hasło';
        }
    })
    .catch(error => {
        console.error('Błąd:', error);
        errorMessage.textContent = 'Wystąpił błąd podczas logowania. Spróbuj ponownie.';
    });
});
