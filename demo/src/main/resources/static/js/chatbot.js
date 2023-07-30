
const form = document.getElementById("input-group");
const input = document.getElementById("user-input");
const messages = document.getElementById("chat-messages");

// Comment the import out if want to test locally
// import {apiKey} from './apikey.js';
// import {apiKey} from '/etc/secrets/apikey.js';

// Uncomment the const apiKey = ... to test locally 
const apiKey = "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";

console.log(apiKey);

form.addEventListener("submit", async (e) => {
  e.preventDefault();
  const message = input.value;
  input.value = "";

  messages.innerHTML += 
  `
    <div class="d-flex justify-content-end mb-4">
        <div class="msg_cotainer_send">
            ${message}
        </div>
        <div class="img_cont_msg">
            <img src="/img/user.png" class="rounded-circle user_img_msg">
        </div>
    </div>
  `;

  // Use axios library to make a POST request to the OpenAI API
  const response = await axios.post(
    "https://api.openai.com/v1/completions",
    {
      prompt: message,
      model: "text-davinci-003",
      temperature: 0,
      max_tokens: 1000,
      top_p: 1,
      frequency_penalty: 0.0,
      presence_penalty: 0.0,
    },
    {
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${apiKey}`,
      },
    }
  );
  const chatbotResponse = response.data.choices[0].text;

  messages.innerHTML += 
//   `<div class="message bot-message">
//   <img src="/img/chatbot.png" alt="bot icon"> <span>${chatbotResponse}</span>
//   </div>`;
  `
    <div class="d-flex justify-content-start mb-4">
        <div class="img_cont_msg">
            <img src="/img/robot.png" class="rounded-circle user_img_msg">
        </div>
        <div class="msg_cotainer">
            ${chatbotResponse}
        </div>
    </div>
  `;
});
