import React, { useState, useEffect } from "react"
import TodosList from "./TodosList"
import Header from "./Header";
import InputTodo from "./InputTodo";
import { Route } from "react-router-dom"
import { Switch } from "react-router"
import About from "../pages/About"
import NotMatch from "../pages/NotMatch"


const TodoContainer = props => {
    const [todos, setTodos] = useState(getInitialTodos())

    function getInitialTodos() {
        const temp = localStorage.getItem("todos")
        const todos = JSON.parse(temp)

        return todos || []
    }

    const handleChange = id => {
        setTodos(prevState => prevState.map(todo => (todo.id === id) ? { ...todo, completed: !todo.completed } : todo))
    }

    const deleteClick = id => {
        setTodos(todos.filter(todo => todo.id !== id))
    }
    const addItem = title => {
        const newItem = {
            title: title,
            id: 0,
            completed: false
        }
        todos.forEach(todo => {
            if (todo.id >= newItem.id) newItem.id = todo.id + 1
        })
        setTodos([
            ...todos,
            newItem
        ])
    }
    const setUpdate = (updateTitle, id) => {
        setTodos(todos.map(todo => todo.id === id ? { ...todo, title: updateTitle } : todo))
    }

    useEffect(() => {
        const storedItems = JSON.parse(localStorage.getItem("todos"))

        if (storedItems) setTodos(storedItems)
    }, [setTodos])

    useEffect(() => {
        const todosJson = JSON.stringify(todos)
        localStorage.setItem("todos", todosJson)
    }, [todos])

    return (
        
                <div className="container">
                    <div className="inner">
                        <Header />
                        <InputTodo addTodoProps={addItem} />
                        <TodosList
                            todos={todos}
                            handleChange={handleChange}
                            deleteClick={deleteClick}
                            setUpdate={setUpdate}
                        />
                    </div>
                </div>
    )
    //<h3>{this.state.todos[0].completed ? "gjort": "ogjort"}</h3>

}
export default TodoContainer