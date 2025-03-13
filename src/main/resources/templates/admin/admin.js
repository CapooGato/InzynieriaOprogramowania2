let users = [];

function addUser(event) {
    event.preventDefault();
    let login = document.getElementById("login").value;
    let role = document.getElementById("role").value;
    let id = Date.now();

    users.push({ id, login, role });
    renderUserList();
    document.getElementById("addUserForm").reset();
}

function renderUserList() {
    let userList = document.getElementById("userList");
    userList.innerHTML = "";

    users.forEach(user => {
        let li = document.createElement("li");
        li.textContent = `${user.login} (${user.role}) `;

        let deleteButton = document.createElement("button");
        deleteButton.textContent = "Usuń";
        deleteButton.onclick = () => deleteUser(user.id);

        li.appendChild(deleteButton);
        userList.appendChild(li);
    });
}

function deleteUser(id) {
    users = users.filter(user => user.id !== id);
    renderUserList();
}

function filterUsers() {
    let searchValue = document.getElementById("search").value.toLowerCase();
    let filteredUsers = users.filter(user => user.login.toLowerCase().includes(searchValue));

    let userList = document.getElementById("userList");
    userList.innerHTML = "";

    filteredUsers.forEach(user => {
        let li = document.createElement("li");
        li.textContent = `${user.login} (${user.role}) `;

        let deleteButton = document.createElement("button");
        deleteButton.textContent = "Usuń";
        deleteButton.onclick = () => deleteUser(user.id);

        li.appendChild(deleteButton);
        userList.appendChild(li);
    });
}