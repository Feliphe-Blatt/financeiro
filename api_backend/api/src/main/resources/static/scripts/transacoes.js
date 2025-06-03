function formatarReal(valor) {
    return Number(valor).toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
}

function obterFiltrosDoFormulario() {
    const form = document.getElementById('filtroExportacao');
    const formData = new FormData(form);
    const filtros = {};
    for (const [key, value] of formData.entries()) {
        if (value !== '') filtros[key] = value;
    }
    return filtros;
}

async function buscarTransacoesPaginadasComFiltro(page = 0, size = 10, filtros = {}) {
    const token = localStorage.getItem('token');
    const params = new URLSearchParams({ page, size, ...filtros }).toString();
    const response = await fetch(`/api/movimentacoes/usuario-movimentacoes-paginado?${params}`, {
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
    if (!transacoes || transacoes.length === 0) {
        const linha = document.createElement('tr');
        linha.innerHTML = `<td colspan="5" style="text-align:center;">Nenhuma transação encontrada.</td>`;
        tabela.appendChild(linha);
        return;
    }
    transacoes.forEach(transacao => {
        const linha = document.createElement('tr');
        linha.innerHTML = `
            <td>${transacao.data || ''}</td>
            <td>${transacao.tipoDaMovimentacao || ''}</td>
            <td>${transacao.categoria || ''}</td>
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
        const filtros = obterFiltrosDoFormulario();
        const data = await buscarTransacoesPaginadasComFiltro(page, 10, filtros);
        renderizarTransacoes(data.content);
        paginaAtual = data.number;
        totalPaginas = data.totalPages;
        document.getElementById('paginacao').textContent = `Página ${paginaAtual + 1} de ${totalPaginas}`;
    } catch (e) {
        renderizarTransacoes([]);
        document.getElementById('paginacao').textContent = '';
        alert('Erro ao carregar transações');
    }
}

document.getElementById('btnBuscar').addEventListener('click', function() {
    atualizarTabela(0);
});

document.addEventListener('DOMContentLoaded', () => {
    atualizarTabela();

    document.getElementById('btnAnterior').onclick = () => {
        if (paginaAtual > 0) atualizarTabela(paginaAtual - 1);
    };
    document.getElementById('btnProxima').onclick = () => {
        if (paginaAtual < totalPaginas - 1) atualizarTabela(paginaAtual + 1);
    };
});

document.getElementById('btnExportarCsv').addEventListener('click', async function() {
    const filtros = obterFiltrosDoFormulario();
    const params = new URLSearchParams(filtros).toString();
    const token = localStorage.getItem('token');
    try {
        const response = await fetch(`/api/movimentacoes/relatorios/csv?${params}`, {
            headers: { 'Authorization': token }
        });
        if (!response.ok) {
            alert('Erro ao exportar CSV');
            return;
        }
        const blob = await response.blob();
        if (!blob || blob.size === 0) {
            alert('Nenhum dado encontrado para exportação.');
            return;
        }
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'relatorio.csv';
        document.body.appendChild(a);
        a.click();
        a.remove();
        window.URL.revokeObjectURL(url);
    } catch (e) {
        alert('Erro ao exportar CSV');
    }
});