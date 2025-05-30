document.addEventListener('DOMContentLoaded', () => {
    const loginForm = document.querySelector('.login-form');

    // Evento para o formulário de login
    loginForm.addEventListener('submit', async function(e) {
        e.preventDefault();
        const email = document.getElementById('email').value;
        const senha = document.getElementById('password').value;

        try {
            const API_URL = `${API_BASE_URL}/api/auth/login`;;
            const response = await fetch(API_URL, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ email, senha })
            });

            if (response.ok) {
                
                const data = await response.json();
                
                localStorage.setItem('token', data.token);

                window.location.href = 'index.html';
                
            } else {
                alert('Usuário ou senha inválidos');
            }
        } catch (error) {
            alert('Erro ao tentar fazer login. Tente novamente mais tarde.');
        }
    });
}); 