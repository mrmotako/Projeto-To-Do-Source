const API_URL = "http://localhost:8080";
const inputBox = document.getElementById("inputBox");
const listcontainer = document.getElementById("listContainer");

let currentPage = 1;
let tasksPerPage = 5;
let allTasks = [];
let currentFilter = 'all';

// ========== CARREGAR TAREFAS ==========
async function loadTasks() {
    try {
        const response = await fetch(`${API_URL}/api/tasks`);
        if (!response.ok) throw new Error(`Erro: ${response.status}`);
        allTasks = await response.json();
        renderTasks();
    } catch (error) {
        console.error("Erro ao carregar tarefas:", error);
    }
}

// ========== ADICIONAR TAREFA ==========
async function addTask() {
    const nome = inputBox.value.trim();
    if (nome === '') {
        alert("Por favor, digite uma tarefa!");
        return;
    }
    try {
        const response = await fetch(`${API_URL}/api/tasks`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ nome: nome })
        });
        if (!response.ok) throw new Error(`Erro: ${response.status}`);
        inputBox.value = "";
        inputBox.focus();
        await loadTasks();
    } catch (error) {
        alert("Não foi possível conectar ao servidor. Verifique se o back-end está rodando.");
    }
}

// ========== MARCAR / DESMARCAR ==========
async function toggleTask(id, feito) {
    const rota = feito ? "undone" : "done";
    try {
        const response = await fetch(`${API_URL}/api/tasks/${id}/${rota}`, {
            method: "PUT"
        });
        if (!response.ok) throw new Error(`Erro: ${response.status}`);
        await loadTasks();
    } catch (error) {
        alert("Erro ao atualizar tarefa. Verifique se o back-end está rodando.");
    }
}

// ========== DELETAR ==========
async function deleteTask(id) {
    try {
        const response = await fetch(`${API_URL}/api/tasks/${id}`, {
            method: "DELETE"
        });
        if (!response.ok) throw new Error(`Erro: ${response.status}`);
        await loadTasks();
    } catch (error) {
        alert("Erro ao deletar tarefa. Verifique se o back-end está rodando.");
    }
}

// ========== RENDERIZAR ==========
function renderTasks() {
    let filtered = allTasks;

    if (currentFilter === 'pending') {
        filtered = allTasks.filter(t => !t.feito);
    } else if (currentFilter === 'completed') {
        filtered = allTasks.filter(t => t.feito);
    }

    const start = (currentPage - 1) * tasksPerPage;
    const end = start + tasksPerPage;
    const paginated = filtered.slice(start, end);

    listcontainer.innerHTML = "";

    paginated.forEach(task => {
        let li = document.createElement("li");
        li.className = task.feito ? "checked" : "";
        li.innerHTML = task.nome;

        
        li.addEventListener("click", () => toggleTask(task.id, task.feito));

        
        let span = document.createElement("span");
        span.innerHTML = "\u00d7";
        span.addEventListener("click", (e) => {
            e.stopPropagation(); 
            deleteTask(task.id);
        });

        li.appendChild(span);
        listcontainer.appendChild(li);
    });

    document.getElementById("pageInfo").innerText = `Página ${currentPage}`;
}

// ========== FILTROS ==========
function filterTasks(type) {
    currentFilter = type;
    currentPage = 1;

    document.querySelectorAll(".filters button").forEach(btn => btn.classList.remove("active"));
    event.target.classList.add("active");

    renderTasks();
}

// ========== PESQUISA ==========
function searchTask() {
    const input = document.getElementById("searchBox").value.toLowerCase();
    const items = listcontainer.getElementsByTagName("li");

    for (let i = 0; i < items.length; i++) {
        const text = items[i].innerText.toLowerCase();
        items[i].style.display = text.includes(input) ? "block" : "none";
    }
}

// ========== PAGINAÇÃO ==========
function changePage(direction) {
    let filtered = allTasks;
    if (currentFilter === 'pending') filtered = allTasks.filter(t => !t.feito);
    if (currentFilter === 'completed') filtered = allTasks.filter(t => t.feito);

    const totalPages = Math.ceil(filtered.length / tasksPerPage);

    currentPage += direction;
    if (currentPage < 1) currentPage = 1;
    if (currentPage > totalPages) currentPage = totalPages;

    renderTasks();
}

// ========== INICIALIZAR ==========
loadTasks(); // carrega as tarefas ao abrir a página