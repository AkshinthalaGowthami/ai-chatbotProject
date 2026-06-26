import { useState } from "react";
import axios from "axios";
import "./App.css";

function App() {

  const [input, setInput] = useState("");

  const [messages, setMessages] = useState([]);

  const [loading, setLoading] = useState(false);

  const sendMessage = async () => {

    if (!input.trim()) {
      return;
    }

    const userMessage = {
      role: "user",
      text: input
    };

    setMessages(prev => [...prev, userMessage]);

    const currentInput = input;

    setInput("");

    setLoading(true);

    try {

      const response = await axios.post(
        "http://localhost:8080/api/chat",
        {
          message: currentInput
        }
      );

      const botMessage = {
        role: "assistant",
        text: response.data
      };

      setMessages(prev => [...prev, botMessage]);

    } catch (error) {

      const errorMessage = {
        role: "assistant",
        text: "Something went wrong."
      };

      setMessages(prev => [...prev, errorMessage]);

    } finally {

      setLoading(false);

    }
  };

  return (
    <div className="container">

      <div className="chat-box">

        <div className="header">
          🤖 Gemini Assistant
        </div>

        <div className="messages">

          {messages.map((msg, index) => (

            <div
              key={index}
              className={
                msg.role === "user"
                  ? "message user"
                  : "message assistant"
              }
            >
              {msg.text}
            </div>

          ))}

          {loading && (

            <div className="message assistant">
              Gemini is thinking...
            </div>

          )}

        </div>

        <div className="input-area">

          <input
            type="text"
            placeholder="Ask anything..."
            value={input}
            onChange={(e) => setInput(e.target.value)}
            onKeyDown={(e) => {
              if (e.key === "Enter") {
                sendMessage();
              }
            }}
          />

          <button onClick={sendMessage}>
            Send
          </button>

        </div>

      </div>

    </div>
  );
}

export default App;