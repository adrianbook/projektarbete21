import React, {useState} from "react";
import { FaPlusCircle } from "react-icons/fa";

const InputTodo = (props) => {
    const [inputText, setInputText] = useState({
        title: ""
    })

    const handleChange = (e) => {
        setInputText({
            ...inputText,
            [e.target.name]: e.target.value
        })
    }

    const handleSubmit = (e) => {
        e.preventDefault()
        if (inputText.title.trim()) {
            props.addTodoProps(inputText.title)
            setInputText({
                title: ""
            })
        } else {
            prompt("Add something to do!")
        }
    }
    return (
        <form onSubmit={handleSubmit} className="form-container">
            <input
            type="text"
            value={inputText.title}
            className="input-text"
            placeholder="Please add todo-item..."
            onChange={handleChange}
            name="title"
            />
            <button
            className="input-submit"    
            >
            <FaPlusCircle className="submit-button"/>
            </button>
        </form>
    )
}

export default InputTodo;