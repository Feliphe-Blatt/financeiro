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
        if (response.status === 403) {
                    localStorage.removeItem('token');
                    window.location.href = 'login.html';
        }
    })
    .catch(() => {
        localStorage.removeItem('token');
        window.location.href = 'login.html';
    });
});

document.addEventListener('DOMContentLoaded', function() {
    const logoutBtn = document.getElementById('logoutBtn');
    if (logoutBtn) {
        logoutBtn.addEventListener('click', function(e) {
            e.preventDefault();
            const token = localStorage.getItem('token');
            if (token) {
                fetch('/api/auth/logout', {
                    method: 'POST',
                    headers: {
                        'Authorization': token
                    }
                }).finally(() => {
                    localStorage.removeItem('token');
                    window.location.href = '/login.html';
                });
            }
        });
    }
});