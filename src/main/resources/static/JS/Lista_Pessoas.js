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

// Adiciona um evento ao documento que aguarda o carregamento completo do DOM
// e chama a função fetchPessoas assim que o DOM estiver pronto
document.addEventListener('DOMContentLoaded', fetchPessoas)

document.getElementById('add-pessoa-form').addEventListener('submit', async(e) => {
    e.preventDefault();
 
    const pessoaNome = document.getElementById('add-nome').value; 
    const pessoaIdade = document.getElementById('add-idade').value;
    const pessoaCpf = document.getElementById('add-cpf').value;
 
    await fetch('/pessoa/postpessoa', {
       method: 'POST',
       headers:{
         'Content-type': 'application/json',
       },
       body: JSON.stringify({nome: pessoaNome, idade: pessoaIdade, cpf: pessoaCpf })
    });
 
    fetchPessoas();
    
    document.getElementById('add-pessoa-form').reset();
 });