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

document.getElementById('sidebarToggler').addEventListener('click', function() {
    document.getElementById('sidebar').classList.toggle('collapsed');
  });