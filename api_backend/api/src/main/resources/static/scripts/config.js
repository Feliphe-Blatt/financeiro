const API_BASE_URL = 'http://localhost:8080';

// função para mudar tema da página
document.addEventListener('DOMContentLoaded', () => {
    const seletorTema = document.getElementById('seletorTema');
    if (!seletorTema) return;

    function aplicarTema(tema) {
        document.body.classList.remove('tema-claro', 'tema-escuro');
        document.body.classList.add(`tema-${tema}`);
        localStorage.setItem('tema', tema);
    }

    // Carrega o tema salvo ou padrão
    const temaSalvo = localStorage.getItem('tema') || 'claro';
    seletorTema.value = temaSalvo;
    aplicarTema(temaSalvo);

    seletorTema.addEventListener('change', (e) => {
        aplicarTema(e.target.value);
    });
});