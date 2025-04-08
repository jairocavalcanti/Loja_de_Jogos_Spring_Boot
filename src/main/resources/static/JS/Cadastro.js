document.getElementById('cadastro-form').addEventListener('submit', async (e) => {
   e.preventDefault();

   const pessoaNome = document.getElementById('add-nome-cadastro').value;
   const pessoaIdade = document.getElementById('add-idade-cadastro').value;
   const pessoaCpf = document.getElementById('add-cpf-cadastro').value;

   const response = await fetch('/cadastro/postcadastro', {
       method: 'POST',
       headers: {
           'Content-type': 'application/json',
       },
       body: JSON.stringify({ nome: pessoaNome, idade: pessoaIdade, cpf: pessoaCpf })
   });

   if (response.ok) {
       alert("Usuário cadastrado com sucesso!");
       document.getElementById('cadastro-form').reset();
       window.location.href = "Pagina_Inicial.html";
   } else {
       alert("Erro ao cadastrar usuário.");
   }
});
