const body = document.querySelector("body");
const sidebar = body.querySelector("nav");
const toggle = body.querySelector(".toggle");
const modeSwitch = body.querySelector(".toggle-switch");
const modeText = body.querySelector(".mode-text");
const logo = body.querySelector("#logo");

const closeNavbar = () => {
	sidebar.classList.add("close");
};

const expandNavbar = () => {
	sidebar.classList.remove("close");
};

modeSwitch.addEventListener("click", () => {
	body.classList.toggle("dark");
	const isDarkModeEnabled = body.classList.contains("dark");
	localStorage.setItem("darkModeEnabled", isDarkModeEnabled);

	if (isDarkModeEnabled) {
		modeText.innerText = "Light mode";
		logo.src = "/img/logo2.png";
		document.querySelectorAll(".icon").forEach((element) => {
			element.classList.add("white-theme");
		});
	} else {
		modeText.innerText = "Dark mode";
		logo.src = "/img/logo1.png";
		document.querySelectorAll(".icon").forEach((element) => {
			element.classList.remove("white-theme");
		});
	}
});

document.addEventListener("click", (event) => {
	if (!sidebar.contains(event.target) && event.target !== toggle) {
		closeNavbar();
	} else if (sidebar.contains(event.target) && event.target !== toggle) {
		expandNavbar();
	}
});

function applyTheme(isDarkMode) {
	if (isDarkMode) {
		body.classList.add("dark");
		modeText.innerText = "Light mode";
		logo.src = "/img/logo2.png";
		document.querySelectorAll(".icon").forEach((element) => {
			element.classList.add("white-theme");
		});
	} else {
		modeText.innerText = "Dark mode";
		logo.src = "/img/logo1.png";
		document.querySelectorAll(".icon").forEach((element) => {
			element.classList.remove("white-theme");
		});
	}
}

const isDarkModeEnabled = localStorage.getItem("darkModeEnabled") === "true";

applyTheme(isDarkModeEnabled);

modeSwitch.addEventListener("click", () => {
	const isDarkModeEnabled = body.classList.contains("dark");
	localStorage.setItem("darkModeEnabled", isDarkModeEnabled);
});

// Function to close the alert
function closeAlert() {
	var alert = document.querySelector(".alert");
	alert.style.display = "none";
}

// Admin Page
function deleteSomething(id) {
	fetch("/users/delete/" + id, {
		method: "DELETE",
	}).then(() => {
		window.location.reload();
	});
}

// Tasklist Date
const todayDateElement = document.getElementById("today-date");
const tomorrowDateElement = document.getElementById("tomorrow-date");

// Get today's date
const today = new Date();
const options = {
	weekday: "long",
	year: "numeric",
	month: "long",
	day: "numeric",
};
const todayFormatted = today.toLocaleDateString("en-US", options);
const dateTodayFormatted = today.toLocaleDateString("en-US");

// Get tomorrow's date
const tomorrow = new Date(today);
tomorrow.setDate(today.getDate() + 1);
const tomorrowFormatted = tomorrow.toLocaleDateString("en-US", options);
const dateTomorrowFormatted = tomorrow.toLocaleDateString("en-US");

// Update the elements with today's and tomorrow's dates
todayDateElement.textContent = todayFormatted;
document.getElementById("date1").value = dateTodayFormatted;
tomorrowDateElement.textContent = tomorrowFormatted;
document.getElementById("date2").value = dateTomorrowFormatted;

function addTaskToList(listNumber, taskInput) {
	const listContainer = document.getElementById("list-container-" + listNumber);

	let li = document.createElement("li");
	li.addEventListener("click", toggleTask);

	// Create a div to contain task text and delete button
	let taskContainer = document.createElement("div");
	taskContainer.className = "task-container";

	// Create a span for task text
	let taskText = document.createElement("span");
	let inputValue = taskInput.value.trim(); // Trim any leading/trailing whitespace

	// Check if the task input is empty
	if (inputValue === "") {
		alert("Please enter a task.");
		return; // Exit the function if the task input is empty
	}

	// Check if the task input exceeds the maximum character limit (e.g., 50 characters)
	const maxCharacterLimit = 75;
	if (inputValue.length > maxCharacterLimit) {
		alert(
			"Task input exceeds the maximum character limit of " +
				maxCharacterLimit +
				" characters."
		);
		return; // Exit the function if the task input exceeds the character limit
	}

	taskText.innerHTML = inputValue;
	taskText.className = "task-text";
	taskContainer.appendChild(taskText);

	// Add delete button to the task container
	let deleteButton = document.createElement("span");
	deleteButton.innerHTML = "X";
	deleteButton.className = "delete-button";
	taskContainer.appendChild(deleteButton);

	li.appendChild(taskContainer);
	listContainer.appendChild(li);

	// Clear the task input field
	taskInput.value = "";
}

function toggleTask(e) {
	if (e.target.tagName === "LI") {
		e.target.classList.toggle("checked");
	} else if (e.target.className === "delete-button") {
		e.target.parentElement.parentElement.remove();
	}
}

function deleteTodo(uid) {
	fetch("/todo/delete/" + uid, {
		method: "DELETE",
	}).then(() => {
		window.location.reload();
	});
}


//getting ref for today, tomorrow, and upcoming
const wrappers = document.querySelectorAll(".wrapper");

/*function showMap(event) {
    event.preventDefault();
    var locationInput = document.querySelector(".inputText").value;
	var mapUrl = "https://www.google.com/maps/embed/v1/place?key=AIzaSyCYEJlP-DTr05LBEXcZ7m_W5fcvVmKAN_g&q=" + encodeURIComponent(locationInput);
    var mapFrame = document.getElementById("mapFrame");
    mapFrame.src = mapUrl;
  } */

  function showMap(event, modal) {
	event.preventDefault();
	var locationInput = modal.querySelector(".inputText").value;
	var mapUrl = "https://www.google.com/maps/embed/v1/place?key=AIzaSyCYEJlP-DTr05LBEXcZ7m_W5fcvVmKAN_g&q=" + encodeURIComponent(locationInput);
	var mapFrame = modal.querySelector("#mapFrame");
	mapFrame.src = mapUrl;
  }

wrappers.forEach((wrapper) => {
	const buttons = wrapper.querySelectorAll(".map-button");
	const modal = wrapper.querySelector(".mapModal");
	const mapFrame = modal.querySelector(".mapFrame");
	const inputText = modal.querySelector(".inputText");

	buttons.forEach((button) => {
		button.addEventListener("click", () => {
			openModal(modal); //call function when the map button is clicked
		});
	});

	function openModal(modal) {
		// Add event listener to close the modal when the close button is clicked
		modal.querySelector(".close").addEventListener("click", () => {
			modal.close();
		});

		// Add event listener to close the modal when clicking outside the modal
		modal.addEventListener("click", (event) => {
			if (event.target === modal) {
				modal.close();
			}
		});
		modal.showModal();
		modal.querySelector("form").addEventListener("submit", (event) => {
			showMap(event, modal); // Pass the current modal to the showMap function
		  });
	}

	
	
});

function updateCompletionStatus(todoId, isChecked) {
    if (todoId === null) {
        //console.error("Invalid todoId: todoId is null.");
        return;
    }

    // Send an AJAX request to the server to update the "completed" status
    const xhr = new XMLHttpRequest();
    xhr.open("POST", `/setTrue?uid=${todoId}&completed=${isChecked}`);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onload = function () {
        if (xhr.status === 200) {
           // console.log("Status updated successfully.");
        } else {
            //console.error("Failed to update status.");
        }
    };
    xhr.send();
}