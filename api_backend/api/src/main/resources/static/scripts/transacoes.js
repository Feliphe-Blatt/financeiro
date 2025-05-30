function formatarReal(valor) {
    return valor.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
}

async function buscarTransacoesPaginadas(page = 0, size = 10) {
    const token = localStorage.getItem('token');
    const response = await fetch(`/api/movimentacoes/usuario-movimentacoes-paginado?page=${page}&size=${size}`, {
        headers: { 'Authorization': token }
    });
    if (!response.ok) throw new Error('Erro ao buscar transações');
    return await response.json();
}

function renderizarTransacoes(transacoes) {
    const tabela = document.getElementById('tabelaTransacoes');
    tabela.innerHTML = `
        <tr>
            <th>Data</th>
            <th>Tipo</th>
            <th>Categoria</th>
            <th>Valor</th>
            <th>Descrição</th>
        </tr>
    `;
    transacoes.forEach(transacao => {
        const linha = document.createElement('tr');
        linha.innerHTML = `
            <td>${transacao.data}</td>
            <td>${transacao.tipoDaMovimentacao}</td>
            <td>${transacao.categoria}</td>
            <td>${formatarReal(transacao.valor)}</td>
            <td>${transacao.descricao || ''}</td>
        `;
        tabela.appendChild(linha);
    });
}

let paginaAtual = 0;
let totalPaginas = 1;

async function atualizarTabela(page = 0) {
    try {
        const data = await buscarTransacoesPaginadas(page, 10);
        renderizarTransacoes(data.content);
        paginaAtual = data.number;
        totalPaginas = data.totalPages;
        document.getElementById('paginacao').textContent = `Página ${paginaAtual + 1} de ${totalPaginas}`;
    } catch (e) {
        alert('Erro ao carregar transações');
    }
}

document.addEventListener('DOMContentLoaded', () => {
    atualizarTabela();

    document.getElementById('btnAnterior').onclick = () => {
        if (paginaAtual > 0) atualizarTabela(paginaAtual - 1);
    };
    document.getElementById('btnProxima').onclick = () => {
        if (paginaAtual < totalPaginas - 1) atualizarTabela(paginaAtual + 1);
    };
});

document.getElementById('btnExportarCsv').addEventListener('click', function() {
    const form = document.getElementById('filtroExportacao');
    const params = new URLSearchParams(new FormData(form)).toString();
    const token = localStorage.getItem('token');
    fetch(`/api/movimentacoes/relatorios/csv?${params}`, {
        headers: { 'Authorization': token }
    })
    .then(response => response.blob())
    .then(blob => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'relatorio.csv';
        document.body.appendChild(a);
        a.click();
        a.remove();
        window.URL.revokeObjectURL(url);
    });
});