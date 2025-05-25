document.addEventListener('DOMContentLoaded', () => {
    const loginForm = document.querySelector('.login-form');

    // Credenciais do admin
//   const adminCredentials = {
//        email: 'admin@admin.com',
//        password: 'admin123'
//    };

    // Evento para o formulário de login
    loginForm.addEventListener('submit', async function(e) {
        e.preventDefault();
        const email = document.getElementById('email').value;
        const senha = document.getElementById('password').value;

        try {   
            const response = await fetch('http://localhost:8080/api/auth/login', {
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