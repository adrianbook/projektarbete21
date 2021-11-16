import React from "react";
import TodoItem from "./TodoItem";

function TodosList(props) {
    return (
        <ul>
            {props.todos.map(todo => (
                <TodoItem todo={todo} key={todo.id} />
            ))}
        </ul>
    )
}

export default TodosList