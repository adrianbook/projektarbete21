import React from "react";
import TodoItem from "./TodoItem";

const TodosList = props => (
    <ul>
        {props.todos.map(todo => (
            <TodoItem
                todo={todo}
                key={todo.id}
                handleChange={props.handleChange}
                deleteClick={props.deleteClick}
                setUpdate={props.setUpdate}
            />
        ))}
    </ul>
)

export default TodosList