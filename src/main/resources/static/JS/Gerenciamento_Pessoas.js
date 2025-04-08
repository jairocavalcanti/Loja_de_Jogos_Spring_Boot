// definiçao de uma função assíncrona
async function fetchPessoas() {
   // realizando uma requisição HTTP do tipo get para o endpoint correspondente
   const response = await fetch('/pessoa')
   // Converte a resposta da requisição HTTP para um objeto JSON 
   const pessoas = await response.json();
   // Seleciona o elemento HTML com o ID 'pessoas_list'
   const pessoasList = document.getElementById("pessoas_list");
   // Limpa o conteúdo existente no elemento 'pessoas_list'
   pessoasList.innerHTML = '';
   // iterando sobre o array de pessoas retornado pelo servidor
   pessoas.forEach(pessoa => {
      // Cria um novo elemento <li> para cada pessoa
      const lis = document.createElement('li');
      // Define o texto do elemento <li> com as informações da pessoa
      lis.textContent = `ID: ${pessoa.id} - Nome: ${pessoa.nome} - Idade: ${pessoa.idade} - CPF: ${pessoa.cpf}`
      // Adiciona o elemento <li> criado como filho do elemento 'pessoas_list'
      pessoasList.appendChild(lis)
   });
}


// Tornando a função acessível globalmente
window.fetchPessoas = fetchPessoas;

// Adiciona um evento ao documento que aguarda o carregamento completo do DOM
// e chama a função fetchPessoas assim que o DOM estiver pronto
document.addEventListener('DOMContentLoaded', fetchPessoas)

// Função responsavel por adicionar cadastro de pessoa na lista
// Respectivos atributos da classe passados como valores abaixo
document.getElementById('add-pessoa-form').addEventListener('submit', async (e) => {
   e.preventDefault();

   const pessoaNome = document.getElementById('add-nome').value;
   const pessoaIdade = document.getElementById('add-idade').value;
   const pessoaCpf = document.getElementById('add-cpf').value;

   await fetch('/pessoa/postpessoa', {
      method: 'POST',
      headers: {
         'Content-type': 'application/json',
      },
      body: JSON.stringify({ nome: pessoaNome, idade: pessoaIdade, cpf: pessoaCpf })
   });

   // Chamando função de lista após o cadastro para que a lista seja atualizada
   fetchPessoas();

   document.getElementById('add-pessoa-form').reset();
});


document.getElementById('form-buscar_por_id').addEventListener('submit', async (e) => {
   e.preventDefault();

   const id = document.getElementById('insert-id').value;
   const response = await fetch(`/pessoa/getbyid/${id}`);

   // utilizando query selector para selecionar o elemento pela classe
   // com o query selector conseguimos 
   const pessoainfo = document.querySelector('.form-busca-pessoa-id');

   if (response.ok) {
      const pessoa = await response.json();

      pessoainfo.innerHTML = ` 
         <h3>Dados da pessoa</h3>
         Nome: ${pessoa.nome}
         Idade: ${pessoa.idade}
         Cpf: ${pessoa.cpf}
     `;
   } else {
      pessoainfo.innerHTML = `<p>ID informado não existe no banco de dados!</p>`;
   }
});

// função javascript definindo exclusão de usuário através de metodos definidos no backend java
document.getElementById("form-excluir_pessoa").addEventListener('submit', async (e) => {
   // metodo dedicado a evitar retornos padrão do navegador
   e.preventDefault();
   // variavel criada com intuito de armazenar valor do elemento "pessoa-id" na div html
   const pessoaId = document.getElementById("pessoa-id").value;

   // passando a rota do metodo de exclusão backend e o valor do do id como requerido no metodo
   await fetch(`/pessoa/deletepessoa/${pessoaId}`, {
      // define o metodo HTTP como DELETE
      method: "DELETE",
      headers: {
         "Content-Type": 'application/json',
      },
      body: JSON.stringify({ id: pessoaId })
   });
   // chamando o metodo fetch para reiniciar a lista de usuários do sistema e atualiza-la após exclusão
   fetchPessoas();
   document.getElementById('form-excluir_pessoa').reset();
});

document.getElementById("form-alterar_pessoa").addEventListener('submit', async (e) => {
   e.preventDefault();
   const pessoaId = document.getElementById('update-cadastro-id').value;
   const pessoaNome = document.getElementById('update-cadastro-nome').value;
   const pessoaIdade = document.getElementById('update-cadastro-idade').value;
   const pessoaCpf = document.getElementById('update-cadastro-cpf').value;

   await fetch(`/pessoa/updatepessoa/${pessoaId}`, {
      method: "PUT",
      headers: {
         "Content-Type" : 'application/json',
      },
      body: JSON.stringify({id: pessoaId, nome: pessoaNome, idade: pessoaIdade, cpf: pessoaCpf})
   });
   
   fetchPessoas();
   document.getElementById('form-alterar_pessoa').reset();
});
