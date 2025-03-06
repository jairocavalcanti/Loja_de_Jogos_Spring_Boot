document.getElementById("login-form").addEventListener("submit", async function(event) {
    event.preventDefault(); // Impede o recarregamento da página

    const cpf = document.getElementById("cpf").value;
    const nome = document.getElementById("nome").value;

    const response = await fetch(`/login/autenticar`, { // Ajuste a URL conforme necessário
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ cpf: cpf , nome: nome })
    });

    const message = await response.text();
    document.getElementById("message").innerText = message;

    if (message === "Login bem-sucedido!") {
        window.location.href = "Lista_Pessoas.html"; // Redireciona para outra página após login bem-sucedido
    }
});