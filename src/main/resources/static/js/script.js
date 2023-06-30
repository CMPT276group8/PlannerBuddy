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

  if (body.classList.contains('dark')) {
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

const wrapper = document.querySelector(".wrapper"),
header = wrapper.querySelector("header");
function onDrag({movementX, movementY}){
  let getStyle = window.getComputedStyle(wrapper);
  let leftVal = parseInt(getStyle.left);
  let topVal = parseInt(getStyle.top);
  wrapper.style.left = `${leftVal + movementX}px`;
  wrapper.style.top = `${topVal + movementY}px`;
}
header.addEventListener("mousedown", ()=>{
  header.classList.add("active");
  header.addEventListener("mousemove", onDrag);
});
document.addEventListener("mouseup", ()=>{
  header.classList.remove("active");
  header.removeEventListener("mousemove", onDrag);
});