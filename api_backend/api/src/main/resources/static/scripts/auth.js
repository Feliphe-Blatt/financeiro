document.addEventListener('DOMContentLoaded', () => {
    const token = localStorage.getItem('token');
    if (!token) {
        window.location.href = 'login.html';
        return;
    }

    // Testa se o token é válido
    fetch('http://localhost:8080/api/usuarios', {
        headers: {
            'Authorization': token
        }
    })
    .then(response => {
        if (response.status === 401) {
            localStorage.removeItem('token');
            window.location.href = 'login.html';
        }
    })
    .catch(() => {
        localStorage.removeItem('token');
        window.location.href = 'login.html';
    });
});