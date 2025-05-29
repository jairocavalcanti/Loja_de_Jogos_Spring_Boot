document.getElementById('cadastro-form').addEventListener('submit', async (e) => {
    e.preventDefault();
 
    const pessoaNome = document.getElementById('add-nome-cadastro').value;
    const pessoaIdade = document.getElementById('add-idade-cadastro').value;
    const pessoaCpf = document.getElementById('add-cpf-cadastro').value;
 
    await fetch('/cadastro/postcadastro', {
       method: 'POST',
       headers: {
          'Content-type': 'application/json',
       },
       body: JSON.stringify({ nome: pessoaNome, idade: pessoaIdade, cpf: pessoaCpf })
    });
 
    // Chamando função de lista após o cadastro para que a lista seja atualizada
    fetchPessoas();
 
    document.getElementById('cadastro-form').reset();
 });