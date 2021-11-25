import React from "react";
import TodoItem from "./TodoItem";

class TodosList extends React.Component {
    render() {
        return (
            <ul>
            {this.props.todos.map(todo => (
                <TodoItem 
                todo={todo}
                key={todo.id} 
                handleChange={this.props.handleChange}
                deleteClick={this.props.deleteClick}
                setUpdate={this.props.setUpdate}
                />
                ))}
        </ul>
    )
}
}

export default TodosList