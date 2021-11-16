import React from "react"
import TodosList from "./TodosList"
import Header from "./Header";
import InputTodo from "./InputTodo";


class TodoContainer extends React.Component {
    state = {
        todos: []
    };
    handleChange = id => {
        this.setState(prevState => ({
            todos: prevState.todos.map(todo => {
                if (todo.id === id) {
                    return {
                        ...todo,
                        completed: !todo.completed
                    }
                }
                return todo
            }),
        }))
    }
    deleteClick = id => {
        this.setState({
            todos: [
                ...this.state.todos.filter(todo => {
                    return todo.id !== id
                })
            ]
        })
    }
    addItem = title => {
        const newItem = {
            title: title,
            id: 0,
            completed: false
        }
        this.state.todos.forEach(todo => {
            if (todo.id >= newItem.id) newItem.id = todo.id + 1
        })
        this.setState({
            todos: [...this.state.todos, newItem]
        })
    }
    setUpdate = (updateTitle, id) => {
        this.setState({
            todos: this.state.todos.map(todo => {
                if (todo.id === id) {
                    return {
                        ...todo,
                        title: updateTitle
                    }
                }
                return todo;
            })
        })
    }
    componentDidMount() {
        let todos = localStorage.getItem("todos")
        todos = JSON.parse(todos)
        if (todos) {
            this.setState({
                todos: todos
            })
        } else {

            fetch("https://jsonplaceholder.typicode.com/todos?_limit=10")
                .then(res => res.json())
                .then(res => {
                    const finalRes = res.map(r => {
                        if (r.completed) return { ...r, completed: false }
                        return r
                    })
                    this.setState({
                        todos: [...finalRes]
                    })
                })
                .catch(e => { console.log(`error fetching todos ${e}`) })
        }
    }
    componentDidUpdate(prevProps, prevState) {
        if (prevState === this.state) return
        const temp = JSON.stringify(this.state.todos)
        localStorage.setItem("todos", temp)
    }
    render() {
        return (
            <div className="container">
                <div className="inner">
                    <Header />
                    <InputTodo addItem={this.addItem} />
                    <TodosList
                        todos={this.state.todos}
                        handleChange={this.handleChange}
                        deleteClick={this.deleteClick}
                        setUpdate={this.setUpdate}
                    />
                </div>
            </div>
        )
        //<h3>{this.state.todos[0].completed ? "gjort": "ogjort"}</h3>
    }
}
export default TodoContainer