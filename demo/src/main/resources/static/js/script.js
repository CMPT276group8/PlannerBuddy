const body = document.querySelector('body');
const sidebar = body.querySelector('nav');
const toggle = body.querySelector('.toggle');
const modeSwitch = body.querySelector('.toggle-switch');
const modeText = body.querySelector('.mode-text');
const logo = body.querySelector('#logo');

const closeNavbar = () => {
  sidebar.classList.add('close');
};

const expandNavbar = () => {
  sidebar.classList.remove('close');
};


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

   // Function to close the alert
   function closeAlert() {
    var alert = document.querySelector('.alert');
    alert.style.display = 'none';
}

//Admin Page
function deleteSomething(id) {
  fetch('/users/delete/' + id,  {
    method: 'DELETE'
  })
  .then(() => {
    window.location.reload();
})
}

// Tasklist
const listContainer1 = document.getElementById("list-container-1");
const addButton1 = document.getElementById("addButton-1");

addButton1.addEventListener("click", function() {
  addTaskToList(1);
});

const listContainer2 = document.getElementById("list-container-2");
const addButton2 = document.getElementById("addButton-2");

addButton2.addEventListener("click", function() {
  addTaskToList(2);
});

const listContainer3 = document.getElementById("list-container-3");
const addButton3 = document.getElementById("addButton-3");

addButton3.addEventListener("click", function() {
  addTaskToList(3);
});

function addTaskToList(listNumber) {
  const listContainer = document.getElementById("list-container-" + listNumber);

  let li = document.createElement("li");
  li.addEventListener("click", toggleTask);

  // Create a div to contain task text and delete button
  let taskContainer = document.createElement("div");
  taskContainer.className = "task-container";

  // Create a span for task text
  let taskText = document.createElement("span");
  let taskInput = prompt("Enter a task:");

  // Check if the task input is empty
  if (taskInput.trim() === "") {
    alert("Please enter a task.");
    return; // Exit the function if the task input is empty
  }

  // Check if the task input exceeds the maximum character limit (e.g., 50 characters)
  const maxCharacterLimit = 75;
  if (taskInput.length > maxCharacterLimit) {
    alert("Task input exceeds the maximum character limit of " + maxCharacterLimit + " characters.");
    return; // Exit the function if the task input exceeds the character limit
  }

  taskText.innerHTML = taskInput;
  taskText.className = "task-text";
  taskContainer.appendChild(taskText);

  // Add delete button to the task container
  let deleteButton = document.createElement("span");
  deleteButton.innerHTML = "X";
  deleteButton.className = "delete-button";
  taskContainer.appendChild(deleteButton);

  li.appendChild(taskContainer);
  listContainer.appendChild(li);
}

function toggleTask(e) {
  if (e.target.tagName === "LI") {
    e.target.classList.toggle("checked");
  } else if (e.target.className === "delete-button") {
    e.target.parentElement.parentElement.remove();
  }
}

// Tasklist Date
// Tasklist
const todayDateElement = document.getElementById("today-date");
const tomorrowDateElement = document.getElementById("tomorrow-date");

// Get today's date
const today = new Date();
const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' };
const todayFormatted = today.toLocaleDateString('en-US', options);

// Get tomorrow's date
const tomorrow = new Date(today);
tomorrow.setDate(today.getDate() + 1);
const tomorrowFormatted = tomorrow.toLocaleDateString('en-US', options);

// Update the elements with today's and tomorrow's dates
todayDateElement.textContent = todayFormatted;
tomorrowDateElement.textContent = tomorrowFormatted;

