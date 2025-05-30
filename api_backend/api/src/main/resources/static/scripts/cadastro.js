document.querySelector('.register-form').addEventListener('submit', async function(e) {
    e.preventDefault();
    const nome = document.getElementById('username').value;
    const email = document.getElementById('email').value;
    const senha = document.getElementById('password').value;

    try {
            const API_URL = `${API_BASE_URL}/api/usuarios`;
            const response = await fetch(API_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                 nome, 
                 email, 
                 senha 
                })
        });

        if (response.ok) {
            alert('Cadastro realizado com sucesso!');
            window.location.href = 'login.html';
        } else {
            const errorData = await response.json();
            alert('Erro ao cadastrar: ' + (errorData.message || 'Verifique os dados e tente novamente.'));
        }
    } catch (error) {
        alert('Erro ao tentar cadastrar. Tente novamente mais tarde.');
    }
}); 