import React, {Component} from "react";

class InputTodo extends Component {
    state = {
        title: ""
    }
    handleChange = e => {
        this.setState({
            [e.target.name]: e.target.value
        })
    }
    handleSubmit = e => {
        if (this.state.title.trim()) {

            e.preventDefault()
            this.props.addItem(this.state.title)
            this.setState({
                title: ""
            })
        } else {
            alert("Please enter description before submitting")
        }
    }
    render() {
        return (
            <form onSubmit={this.handleSubmit} className="form-container">
                <input
                type="text"
                className="input-text"
                placeholder="input todo-item..."
                name="title" value={this.state.title}
                onChange={this.handleChange}
                />
                <button className="input-submit">Submit</button>
            </form>
        )
    }
}

export default InputTodo;