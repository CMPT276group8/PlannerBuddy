let nav = 0;
let clicked = null;
let events = localStorage.getItem("events")
	? JSON.parse(localStorage.getItem("events"))
	: [];
let eventArr = [];

const calendar = document.getElementById("calendar");
const newEventModal = document.getElementById("newEventModal");
const deleteEventModal = document.getElementById("deleteEventModal");
const backDrop = document.getElementById("modalBackDrop");
const eventTitleInput = document.getElementById("eventTitleInput");
const weekdays = [
	"Sunday",
	"Monday",
	"Tuesday",
	"Wednesday",
	"Thursday",
	"Friday",
	"Saturday",
];

function load() {
	const dt = new Date();

	if (nav !== 0) {
		dt.setMonth(new Date().getMonth() + nav);
	}

	const day = dt.getDate();
	const month = dt.getMonth();
	const year = dt.getFullYear();

	const firstDayOfMonth = new Date(year, month, 1);
	const daysInMonth = new Date(year, month + 1, 0).getDate();

	const dateString = firstDayOfMonth.toLocaleDateString("en-us", {
		weekday: "long",
		year: "numeric",
		month: "numeric",
		day: "numeric",
	});
	const paddingDays = weekdays.indexOf(dateString.split(", ")[0]);

	document.getElementById("monthDisplay").innerText = `${dt.toLocaleDateString(
		"en-us",
		{ month: "long" }
	)} ${year}`;

	calendar.innerHTML = "";

	// Call retrieveDataFromUl after the page loads and update the events array
	eventArr = retrieveDataFromUl();

	eventArr.forEach((eve) => {
		events.push({
			date: eve.date,
			title: eve.activity,
		});
	});

	localStorage.setItem("events", JSON.stringify(events));

	console.log("Local Storage = ", localStorage);
	localStorage.clear();

	for (let i = 1; i <= paddingDays + daysInMonth; i++) {
		const daySquare = document.createElement("div");
		daySquare.classList.add("day");

		const dayString = `${month + 1}/${i - paddingDays}/${year}`;

		if (i > paddingDays) {
			daySquare.innerText = i - paddingDays;
			const eventsForDay = events.filter((e) => e.date === dayString);
			console.log("event", eventsForDay);

			if (i - paddingDays === day && nav === 0) {
				daySquare.id = "currentDay";
			}

			if (eventsForDay && eventsForDay.length > 0) {
				eventsForDay.forEach((eventForDay) => {
					const eventDiv = document.createElement("div");
					eventDiv.classList.add("event");
					eventDiv.innerText = eventForDay.title;
					daySquare.appendChild(eventDiv);
				});
			}

			// daySquare.addEventListener("click", () => openModal(dayString));
		} else {
			daySquare.classList.add("padding");
		}

		calendar.appendChild(daySquare);
	}
}
function initButtons() {
	document.getElementById("nextButton").addEventListener("click", () => {
		nav++;
		load();
	});

	document.getElementById("backButton").addEventListener("click", () => {
		nav--;
		load();
	});
}

// Add this function to retrieve data from the <ul> element
function retrieveDataFromUl() {
	const ulElement = document.querySelector(".calendar-wrapper"); // Assuming you only have one <ul> element with the class "calendar-wrapper"
	const liElements = ulElement.querySelectorAll("li");

	const data = [];

	liElements.forEach((li) => {
		const inputElements = li.querySelectorAll("input");
		const activity = inputElements[0].value;
		const date = inputElements[1].value;
		const uid = li.getAttribute("data-uid");

		data.push({ activity, date });
	});

	return data;
}

initButtons();
load();
