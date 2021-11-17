import React from "react"
import ReactDOM from "react-dom"
import TodoContainer from "./functionBased/components/TodoContainer"
//stylesheets
import "./functionBased/App.css"
//const element = <h1>Hello from Create React App</h1>

ReactDOM.render(
<React.StrictMode>
    <TodoContainer/>
</React.StrictMode>,
document.getElementById("root"))