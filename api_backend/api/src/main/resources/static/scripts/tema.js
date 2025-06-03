document.addEventListener('DOMContentLoaded', () => {
    const temaSalvo = localStorage.getItem('tema') || 'claro';
    document.body.classList.remove('tema-claro', 'tema-escuro');
    document.body.classList.add(`tema-${temaSalvo}`);
});