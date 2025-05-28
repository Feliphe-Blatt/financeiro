// Gráfico de Rosca (Resumo Semanal)
const pieChart1 = new Chart(document.getElementById('pieChart1'), {
    type: 'doughnut',
    data: {
        labels: ['Alimentação', 'Transporte', 'Lazer', 'Contas'],
        datasets: [{
            data: [35, 25, 20, 20],
            backgroundColor: [
                '#4CAF50', // verde
                '#FF9800', // laranja
                '#2196F3', // azul
                '#E91E63'  // rosa
            ]
        }]
    },
    options: {
        responsive: true,
        cutout: '70%',
        plugins: {
            legend: {
                position: 'right',
                labels: {
                    boxWidth: 16,
                    boxHeight: 16
                }
            },
            title: {
                display: false
            }
        }
    }
});

// Gráfico de Rosca (Metas)
const metaPercent = 82; // valor aleatório para "Atingido"
const pieChart2 = new Chart(document.getElementById('pieChart2'), {
    type: 'doughnut',
    data: {
        labels: ['Atingido', 'Restante'],
        datasets: [{
            data: [metaPercent, 100 - metaPercent],
            backgroundColor: [
                '#00BCD4', // ciano
                '#FFC107'  // amarelo
            ]
        }]
    },
    options: {
        responsive: true,
        cutout: '70%',
        plugins: {
            legend: {
                position: 'right',
                labels: {
                    boxWidth: 16,
                    boxHeight: 16
                }
            },
            title: {
                display: false
            },
            tooltip: {
                enabled: false
            }
        },
        animation: {
            onComplete: function() {
                const chart = pieChart2;
                const ctx = chart.ctx;
                ctx.save();
                ctx.font = 'bold 2.2rem Poppins, Arial';
                ctx.fillStyle = '#00BCD4';
                ctx.textAlign = 'center';
                ctx.textBaseline = 'middle';
                ctx.fillText(metaPercent + '%', chart.getDatasetMeta(0).data[0].x, chart.getDatasetMeta(0).data[0].y);
                ctx.restore();
            }
        }
    }
});

function formatarReal(valor) {
    return valor.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
}

// Definir as categorias por tipo (baseado na imagem fornecida)
const categoriasPorTipo = {
    true: [ // Receita
        "SALARIO", "BONUS", "FREELANCER", "VENDA",
        "RENDIMENTO", "INVESTIMENTO", "OUTROS"
    ],
    false: [ // Despesa
        "LAZER", "EDUCACAO", "MORADIA", "TRANSPORTE",
        "ALIMENTACAO", "SAUDE", "PRESENTES", "PET",
        "INVESTIMENTOS", "ASSINATURAS", "OUTROS"
    ]
};

document.addEventListener('DOMContentLoaded', function() {
    const token = localStorage.getItem('token');

    // Buscar saldo
    fetch('/api/movimentacoes/usuario/saldo', {
        headers: { 'Authorization': token }
    })
    .then(response => {
        if (!response.ok) throw new Error('Erro ao buscar saldo');
        return response.json();
    })
    .then(saldo => {
        document.getElementById('saldoAtual').textContent = formatarReal(saldo);
    })
    .catch(() => {
        document.getElementById('saldoAtual').textContent = 'Erro ao carregar saldo';
    });

    // Buscar movimentações e alimentar gráfico
    fetch('/api/movimentacoes/usuario-movimentacoes', {
        headers: { 'Authorization': token }
    })
    .then(response => {
        if (!response.ok) throw new Error('Erro ao buscar movimentações');
        return response.json();
    })
    .then(movimentacoes => {
        // Agrupar por categoria
        const categoriaTotais = {};
        movimentacoes.forEach(mov => {
            const categoria = mov.categoria;
            const valor = mov.valor;
            if (!categoriaTotais[categoria]) {
                categoriaTotais[categoria] = 0;
            }
            categoriaTotais[categoria] += valor;
        });

        // Preparar dados para o gráfico
        const labels = Object.keys(categoriaTotais);
        const data = Object.values(categoriaTotais);

        // Atualizar gráfico
        pieChart1.data.labels = labels;
        pieChart1.data.datasets[0].data = data;
        pieChart1.update();
    })
    .catch(() => {
        // Trate o erro conforme necessário
    });
    
    
    // Função para criar movimentação
    document.getElementById('formMovimentacao').addEventListener('submit', function(event) {
        event.preventDefault();

        const form = event.target;
        const movimentacao = {
            isReceita: form.isReceita.value === "true",
            valor: parseFloat(form.valor.value),
            categoria: form.categoria.value,
            data: form.data.value,
            descricao: form.descricao.value
        };

        fetch('/api/movimentacoes', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            },
            body: JSON.stringify(movimentacao)
        })
        .then(response => {
            if (response.status !== 201) throw new Error('Erro ao criar movimentação');
            location.reload();
        })
        .catch(error => {
            alert('Erro ao criar movimentação');
        });
    });

    // Mostrar/ocultar formulário de movimentação
    document.getElementById('btnMostrarFormMovimentacao').addEventListener('click', function() {
        const form = document.getElementById('formMovimentacao');
        if (form.style.display === 'none' || form.style.display === '') {
            form.style.display = 'flex';
            // Quando o formulário abrir, carregar as categorias iniciais (Despesa)
            atualizarCategorias(false);
        } else {
            form.style.display = 'none';
        }
    });

    // Função para atualizar as opções de categoria
    function atualizarCategorias(isReceita) {
        const selectCategoria = document.getElementById('categoria');
        const categorias = categoriasPorTipo[isReceita];

        // Limpa as opções atuais
        selectCategoria.innerHTML = '<option value="">Selecione a Categoria</option>';

        // Adiciona as novas opções
        categorias.forEach(categoria => {
            const option = document.createElement('option');
            option.value = categoria;
            option.textContent = categoria;
            selectCategoria.appendChild(option);
        });
    }

    // Adicionar evento para o select de Tipo (isReceita)
    document.getElementById('isReceita').addEventListener('change', function(event) {
        const isReceitaSelecionada = event.target.value === "true";
        atualizarCategorias(isReceitaSelecionada);
    });

    // Carregar categorias iniciais ao carregar a página (para o estado inicial do select de tipo)
    const isReceitaInicial = document.getElementById('isReceita').value === "true";
    atualizarCategorias(isReceitaInicial);
});