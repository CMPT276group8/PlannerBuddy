const body = document.querySelector("body");
const sidebar = body.querySelector("nav");
const toggle = body.querySelector(".toggle");
const modeSwitch = body.querySelector(".toggle-switch");
const modeText = body.querySelector(".mode-text");
const logo = body.querySelector("#logo");

function mydate1() {
	d = new Date(document.getElementById("dt").value);
	dt = d.getDate();
	mn = d.getMonth();
	mn++;
	yy = d.getFullYear();
	document.getElementById("ndt").value = dt + "/" + mn + "/" + yy;
	document.getElementById("ndt").hidden = false;
	document.getElementById("dt").hidden = true;
}

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

//function to show the map from google map
function showMap(event, modal) {
	event.preventDefault();
	var locationInput = modal.querySelector(".inputText").value;
	var mapUrl =
		"https://www.google.com/maps/embed/v1/place?key=AIzaSyCYEJlP-DTr05LBEXcZ7m_W5fcvVmKAN_g&q=" +
		encodeURIComponent(locationInput);
	var mapFrame = modal.querySelector("#mapFrame");
	mapFrame.src = mapUrl;
}
wrappers.forEach((wrapper) => {
    const buttons = wrapper.querySelectorAll(".map-button");
    const modal = wrapper.querySelector(".mapModal");
  
    buttons.forEach((button) => {
      button.addEventListener("click", (event) => {
        const uid = button.getAttribute("data-uid");
        const activity = button.getAttribute("data-activity");
  
        modal.querySelector(".uidInput").value = uid;
        modal.querySelector(".activityInput").value = activity;
  
        openModal(modal, button); //passing the modal and the button to openModal
      });
    });
  
    function openModal(modal, button) { 
       //close the modal when the close button is clicked
      	modal.querySelector(".close").addEventListener("click", () => {
        	modal.close();
      	});
  
      	//close the modal when clicking outside the modal
      	modal.addEventListener("click", (event) => {
        	if (event.target === modal) {
          	modal.close();
       	 }
      	});
  
      	modal.showModal();
  
      	// save-button will display the place in the task
      	modal.querySelector(".save-button").addEventListener("click", () => {
			const place = modal.querySelector(".inputText").value;
			const taskWrapper = button.closest('.right-wrapper'); 
			const label = taskWrapper.querySelector('.place-label'); //get the label within the right-wrap
			label.textContent = place;
	
			// submit the form after label is updated
			modal.querySelector('form').submit();
		});
	
		modal.querySelector("form").addEventListener("submit", (event) => {
			event.preventDefault();
			showMap(event, modal); //pass modal to showMap function
		});
	
    }
});

  

//getting calendar ref from upcoming
const wrappersCalendar = document.querySelectorAll(".wrapper")[2];

const buttons = wrappersCalendar.querySelectorAll(".calendar-button");
const modal = wrappersCalendar.querySelector(".calendarModal");

buttons.forEach((button) => {
	button.addEventListener("click", () => {
		const uid = button.getAttribute("data-uid");
		const activity = button.getAttribute("data-activity3");
		openModal(modal); //call function when the map button is clicked
		modal.style.display = "block";
		modal.querySelector(".uidInput").value = uid;
		modal.querySelector(".activityInput").value = activity;
	});
});

function openModal(modal) {
	modal.querySelector(".close").addEventListener("click", () => {
		modal.close();
		modal.style.display = "none";
	});

	modal.addEventListener("click", (event) => {
		if (event.target === modal) {
			modal.close();
			modal.style.display = "none";
		}
	});

	modal.showModal();
}

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
function formatDate(dateValue) {
	const [year, month, day] = dateValue.split("-");
	const formattedMonth = month.replace(/^0+/, ""); // Remove leading zeros from the month
	const formattedDay = day.replace(/^0+/, ""); // Remove leading zeros from the day
	return `${formattedMonth}/${formattedDay}/${year}`;
}
// Event listener to update hidden input when dateValue3 changes
document.getElementById("dateValue3").addEventListener("change", function () {
	const dateValue3 = this.value; // Get the value of dateValue3
	const formattedDate = formatDate(dateValue3); // Convert to desired format
	document.querySelector('input[name="date3"]').value = formattedDate; // Set the value of date3
});
