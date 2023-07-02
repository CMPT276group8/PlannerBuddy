const body = document.querySelector('body');
const sidebar = body.querySelector('nav');
const toggle = body.querySelector('.toggle');
const searchBtn = body.querySelector('.search-box');
const modeSwitch = body.querySelector('.toggle-switch');
const modeText = body.querySelector('.mode-text');
const logo = body.querySelector('#logo');
const taskListLink = body.querySelector('#taskListLink');

const closeNavbar = () => {
  sidebar.classList.add('close');
};

const expandNavbar = () => {
  sidebar.classList.remove('close');
};

searchBtn.addEventListener('click', expandNavbar);
modeSwitch.addEventListener('click', () => {
  body.classList.toggle('dark');
  const isDarkModeEnabled = body.classList.contains('dark');
  localStorage.setItem('darkModeEnabled', isDarkModeEnabled);

  if (isDarkModeEnabled) {
    modeText.innerText = 'Light mode';
    logo.src = '/img/logo2.png';
  } else {
    modeText.innerText = 'Dark mode';
    logo.src = '/img/logo1.png';
  }
});

document.addEventListener('click', (event) => {
  if (!sidebar.contains(event.target) && event.target !== toggle) {
    closeNavbar();
  } else if (sidebar.contains(event.target) && event.target !== toggle) {
    expandNavbar();
  }
});

const wrapper = document.querySelector('.wrapper');
const header = wrapper.querySelector('header');

function onDrag({ movementX, movementY }) {
  let getStyle = window.getComputedStyle(wrapper);
  let leftVal = parseInt(getStyle.left);
  let topVal = parseInt(getStyle.top);
  wrapper.style.left = `${leftVal + movementX}px`;
  wrapper.style.top = `${topVal + movementY}px`;
}

header.addEventListener('mousedown', () => {
  header.classList.add('active');
  header.addEventListener('mousemove', onDrag);
});

document.addEventListener('mouseup', () => {
  header.classList.remove('active');
  header.removeEventListener('mousemove', onDrag);
});
function applyTheme(isDarkMode) {
  if (isDarkMode) {
    body.classList.add('dark');
    modeText.innerText = 'Light mode';
    logo.src = '/img/logo2.png';
  } else {
    modeText.innerText = 'Dark mode';
    logo.src = '/img/logo1.png';
  }
}

const isDarkModeEnabled = localStorage.getItem('darkModeEnabled') === 'true';

applyTheme(isDarkModeEnabled);

modeSwitch.addEventListener('click', () => {
  const isDarkModeEnabled = body.classList.contains('dark');
  localStorage.setItem('darkModeEnabled', isDarkModeEnabled);
});

const taskInput = document.querySelector(".task-input input"),
filters = document.querySelectorAll(".filters span"),
clearAll = document.querySelector(".clear-btn"),
taskBox = document.querySelector(".task-box");

let editId,
isEditTask = false,
todos = JSON.parse(localStorage.getItem("todo-list"));

filters.forEach(btn => {
    btn.addEventListener("click", () => {
        document.querySelector("span.active").classList.remove("active");
        btn.classList.add("active");
        showTodo(btn.id);
    });
});

function showTodo(filter) {
    let liTag = "";
    if(todos) {
        todos.forEach((todo, id) => {
            let completed = todo.status == "completed" ? "checked" : "";
            if(filter == todo.status || filter == "all") {
                liTag += `<li class="task">
                            <label for="${id}">
                                <input onclick="updateStatus(this)" type="checkbox" id="${id}" ${completed}>
                                <p class="${completed}">${todo.name}</p>
                            </label>
                            <div class="settings">
                                <i onclick="showMenu(this)" class="uil uil-ellipsis-h"></i>
                                <ul class="task-menu">
                                    <li onclick='editTask(${id}, "${todo.name}")'><i class="uil uil-pen"></i>Edit</li>
                                    <li onclick='deleteTask(${id}, "${filter}")'><i class="uil uil-trash"></i>Delete</li>
                                </ul>
                            </div>
                        </li>`;
            }
        });
    }
    taskBox.innerHTML = liTag || `<span>You don't have any task here</span>`;
    let checkTask = taskBox.querySelectorAll(".task");
    !checkTask.length ? clearAll.classList.remove("active") : clearAll.classList.add("active");
    taskBox.offsetHeight >= 300 ? taskBox.classList.add("overflow") : taskBox.classList.remove("overflow");
}
showTodo("all");

function showMenu(selectedTask) {
    let menuDiv = selectedTask.parentElement.lastElementChild;
    menuDiv.classList.add("show");
    document.addEventListener("click", e => {
        if(e.target.tagName != "I" || e.target != selectedTask) {
            menuDiv.classList.remove("show");
        }
    });
}

function updateStatus(selectedTask) {
    let taskName = selectedTask.parentElement.lastElementChild;
    if(selectedTask.checked) {
        taskName.classList.add("checked");
        todos[selectedTask.id].status = "completed";
    } else {
        taskName.classList.remove("checked");
        todos[selectedTask.id].status = "pending";
    }
    localStorage.setItem("todo-list", JSON.stringify(todos))
}

function editTask(taskId, textName) {
    editId = taskId;
    isEditTask = true;
    taskInput.value = textName;
    taskInput.focus();
    taskInput.classList.add("active");
}

function deleteTask(deleteId, filter) {
    isEditTask = false;
    todos.splice(deleteId, 1);
    localStorage.setItem("todo-list", JSON.stringify(todos));
    showTodo(filter);
}

clearAll.addEventListener("click", () => {
    isEditTask = false;
    todos.splice(0, todos.length);
    localStorage.setItem("todo-list", JSON.stringify(todos));
    showTodo()
});

taskInput.addEventListener("keyup", e => {
    let userTask = taskInput.value.trim();
    if(e.key == "Enter" && userTask) {
        if(!isEditTask) {
            todos = !todos ? [] : todos;
            let taskInfo = {name: userTask, status: "pending"};
            todos.push(taskInfo);
        } else {
            isEditTask = false;
            todos[editId].name = userTask;
        }
        taskInput.value = "";
        localStorage.setItem("todo-list", JSON.stringify(todos));
        showTodo(document.querySelector("span.active").id);
    }
});