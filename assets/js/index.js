class Renda {
    constructor(nome, descricao, tipo, valor) {
        this._nome = nome;
        this._descricao = descricao;
        this._tipo = tipo;
        this._valor = valor;
    }

    // Getters
    get nome() {
        return this._nome;
    }

    get descricao() {
        return this._descricao;
    }

    get tipo() {
        return this._tipo;
    }

    get valor() {
        return this._valor;
    }

    // Setters
    set nome(nome) {
        this._nome = nome;
    }

    set descricao(descricao) {
        this._descricao = descricao;
    }

    set tipo(tipo) {
        this._tipo = tipo;
    }

    set valor(valor) {
        this._valor = valor;
    }
}

class Despesa {
    constructor(nome, descricao, tipo, valor) {
        this._nome = nome;
        this._descricao = descricao;
        this._tipo = tipo;
        this._valor = valor;
    }

    // Getters
    get nome() {
        return this._nome;
    }

    get descricao() {
        return this._descricao;
    }

    get tipo() {
        return this._tipo;
    }

    get valor() {
        return this._valor;
    }

    // Setters
    set nome(nome) {
        this._nome = nome;
    }

    set descricao(descricao) {
        this._descricao = descricao;
    }

    set tipo(tipo) {
        this._tipo = tipo;
    }

    set valor(valor) {
        this._valor = valor;
    }
}

class Mes {
  constructor(id) {
      this._id = id;
      this._rendas = [];
      this._despesas = [];
  }

  // Getters
  get id() {
      return this._id;
  }

  get rendas() {
      return this._rendas;
  }

  get despesas() {
      return this._despesas;
  }

  // Setters
  set id(id) {
      this._id = id;
  }

  //////////////////////////////////////////////////////////////
  // CRUD Renda

  criaRenda(nome, descricao, tipo, valor) {
      this._rendas.push(new Renda(nome, descricao, tipo, valor));
  }

  atualizaRenda(index, nome, descricao, tipo, valor) {
      this._rendas[index].nome = nome;
      this._rendas[index].descricao = descricao;
      this._rendas[index].tipo = tipo;
      this._rendas[index].valor = valor;
  }

  deletaRenda(index) {
      this._rendas.splice(index, 1);
  }

  //////////////////////////////////////////////////////////////
  // CRUD Despesa

  criaDespesa(nome, descricao, tipo, valor) {
      this._despesas.push(new Despesa(nome, descricao, tipo, valor));
  }

  atualizaDespesa(index, nome, descricao, tipo, valor) {
      this._despesas[index].nome = nome;
      this._despesas[index].descricao = descricao;
      this._despesas[index].tipo = tipo;
      this._despesas[index].valor = valor;
  }

  deletaDespesa(index) {
      this._despesas.splice(index, 1);
  }
}

//////////////////////////////////////////////////////////////
// DOM

document.addEventListener('DOMContentLoaded', () => {
  const mes = new Mes(1);

  const rendaForm = document.getElementById('renda-form');
  const rendaList = document.getElementById('renda-list');

  rendaForm.addEventListener('submit', (event) => {
    event.preventDefault();
    const nome = document.getElementById('nome').value;
    const descricao = document.getElementById('descricao').value;
    const tipo = document.getElementById('tipo').value;
    const valor = document.getElementById('valor').value;

    mes.criaRenda(nome, descricao, tipo, valor);
    atualizarTabelaRendas();
    rendaForm.reset();
  });

  function atualizarTabelaRendas() {
    rendaList.innerHTML = '';
    mes.rendas.forEach((renda, index) => {
      const row = document.createElement('tr');
      row.innerHTML = `
        <td>${renda.nome}</td>
        <td>${renda.descricao}</td>
        <td>${renda.tipo}</td>
        <td>${renda.valor}</td>
        <td>
          <button class="btn btn-warning btn-sm" onclick="editarRenda(${index})">Editar</button>
          <button class="btn btn-danger btn-sm" onclick="deletarRenda(${index})">Deletar</button>
        </td>
      `;
      rendaList.appendChild(row);
    });
  }

  window.editarRenda = (index) => {
    const renda = mes.rendas[index];
    document.getElementById('nome').value = renda.nome;
    document.getElementById('descricao').value = renda.descricao;
    document.getElementById('tipo').value = renda.tipo;
    document.getElementById('valor').value = renda.valor;

    rendaForm.onsubmit = (event) => {
      event.preventDefault();
      mes.atualizaRenda(index, document.getElementById('nome').value, document.getElementById('descricao').value, document.getElementById('tipo').value, document.getElementById('valor').value);
      atualizarTabelaRendas();
      rendaForm.reset();
      rendaForm.onsubmit = adicionarRenda;
    };
  };

  window.deletarRenda = (index) => {
    mes.deletaRenda(index);
    atualizarTabelaRendas();
  };

  function adicionarRenda(event) {
    event.preventDefault();
    const nome = document.getElementById('nome').value;
    const descricao = document.getElementById('descricao').value;
    const tipo = document.getElementById('tipo').value;
    const valor = document.getElementById('valor').value;

    mes.criaRenda(nome, descricao, tipo, valor);
    atualizarTabelaRendas();
    rendaForm.reset();
  }

  rendaForm.onsubmit = adicionarRenda;
});

document.addEventListener('DOMContentLoaded', () => {
    const sidebar = document.getElementById('sidebar');
    const sidebarToggler = document.getElementById('sidebarToggler');
  // Eu adicionei essa linha de código para consertar o problema de a sidebar abrir sempre que o index é carregado.
    sidebar.classList.add('collapsed');
  
    sidebarToggler.addEventListener('click', () => {
      sidebar.classList.toggle('collapsed');
    });
  });

  // Linha para 'hiddar' o balance
  document.addEventListener('DOMContentLoaded', () => {
    const balanceAmount = document.getElementById('balance-amount');
    const toggleBalanceButton = document.getElementById('toggle-balance');
    const eyeIcon = document.getElementById('eye-icon');
    let isBalanceHidden = false;
     let balance = 0; // Valor inicial do saldo

     //transforma o 'bi-eye' em 'bi-eye-slash' e vice-versa
  
    toggleBalanceButton.addEventListener('click', () => {
      if (isBalanceHidden) {
        balanceAmount.textContent = `${balance.toFixed(2)}R$`;
        eyeIcon.classList.remove('bi-eye-slash');
        eyeIcon.classList.add('bi-eye');
      } else {
        balanceAmount.textContent = '...';
        eyeIcon.classList.remove('bi-eye');
        eyeIcon.classList.add('bi-eye-slash');
      }
      isBalanceHidden = !isBalanceHidden;
    });
  
    const transactionForm = document.getElementById('add-transaction-form');
    const transactionList = document.getElementById('transaction-list');
    const transactionType = document.getElementById('transaction-type');
    const transactionSpecification = document.getElementById('transaction-specification');

    const rendaOptions = ['Fixa', 'Adicional'];
    const despesaOptions = ['Rent', 'Groceries', 'Utilities', 'Entertainment'];
  
    function updateSpecificationOptions() {
    const selectedType = transactionType.value;
    let options = [];

    if (selectedType === 'renda') {
      options = rendaOptions;
    } else if (selectedType === 'despesa') {
      options = despesaOptions;
    }
    else {
        options = [];
    }

    transactionSpecification.innerHTML = '';
    options.forEach(option => {
      const optionElement = document.createElement('option');
      optionElement.value = option.toLowerCase();
      optionElement.textContent = option;
      transactionSpecification.appendChild(optionElement);
    });
  }

  // Atualizar as opções de especificação ao carregar a página
  updateSpecificationOptions();

  // Atualizar as opções de especificação ao mudar o tipo de transação
  transactionType.addEventListener('change', updateSpecificationOptions);

  transactionForm.addEventListener('submit', (event) => {
    event.preventDefault();
    const transactionTypeValue = transactionType.value;
    const transactionSpecificationValue = transactionSpecification.value;
    const transactionAmount = parseFloat(document.getElementById('transaction-amount').value);

    if (transactionTypeValue === 'renda') {
      balance += transactionAmount;
    } else if (transactionTypeValue === 'despesa') {
      balance -= transactionAmount;
    }

    const listItem = document.createElement('li');
    listItem.className = 'list-group-item';
    listItem.textContent = `${transactionTypeValue === 'renda' ? 'Renda' : 'Despesa'} (${transactionSpecificationValue}): ${transactionAmount.toFixed(2)}R$`;
    transactionList.appendChild(listItem);

    balanceAmount.textContent = `${balance.toFixed(2)}R$`;
    transactionForm.reset();
    updateSpecificationOptions(); // Resetar as opções de especificação
  });

    const data = [
        { label: 'Rent', value: 500 },
        { label: 'Groceries', value: 300 },
        { label: 'Utilities', value: 200 },
        { label: 'Entertainment', value: 100 }
      ];

      const width = 450;
      const height = 450;
      const margin = 40;

      const radius = Math.min(width, height) / 2 - margin;  

      const svg = d3.select("#pie-chart")
        .append("svg")
        .attr("width", width)
        .attr("height", height)
        .append("g")
        .attr("transform", `translate(${width / 2}, ${height / 2})`);

      const color = d3.scaleOrdinal()
        .domain(data.map(d => d.label))
        .range(d3.schemeCategory10);

      const selectedLabels = new Set(data.map(d => d.label));

      const arc = d3.arc()
        .innerRadius(130)
        .outerRadius(radius);

      const pie = d3.pie()
        .sort(null)
        .value(d => d.value);

      const updateChart = () => {
        const filteredData = data.filter(d => selectedLabels.has(d.label));
        const data_ready = pie(filteredData);

        const path = svg
          .selectAll('path')
          .data(data_ready, d => d.data.label);

        path.join(
          enter => enter.append('path')
            .attr('fill', d => color(d.data.label))
            .attr("stroke", "black")
            .style("stroke-width", "0.5px") // Ajustar espessura da borda
            .style("opacity", 0.7)
            .style("stroke-linejoin", "round")
            .each(function(d) { this._current = d; }), // store the initial angles
          update => update,
          exit => exit.remove()
        )
        .transition()
        .duration(750)
        .attrTween("d", arcTween);

        legend.selectAll("li")
          .data(data)
          .attr("class", d => selectedLabels.has(d.label) ? "legend-item active" : "legend-item inactive");
      };

      function arcTween(a) {
        const i = d3.interpolate(this._current, a);
        this._current = i(0);
        return (t) => arc(i(t));
      }

      const legend = d3.select("#legend")
        .append("ul")
        .style("list-style", "none");

      legend.selectAll("li")
        .data(data)
        .enter()
        .append("li")
        .attr("class", d => selectedLabels.has(d.label) ? "legend-item active" : "legend-item inactive")
        .html(d => `<div style="width: 20px; height: 20px; background-color: ${color(d.label)}; margin-right: 10px;"></div>${d.label}`)
        .on("click", function(event, d) {
          if (selectedLabels.has(d.label)) {
            selectedLabels.delete(d.label);
          } else {
            selectedLabels.add(d.label);
          }
          updateChart();
        });

      updateChart();
  });