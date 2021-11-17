import React from "react";
import styles from "./TodoItem.module.css"

class TodoItem extends React.Component {
    state = {
        editing: false,
    }
    handleEditing = () => {
        this.setState({
            editing: true
        })
    }
    handleUpdateDone = e => {
        if (e.key === 'Enter') {
            this.setState({
                editing: false
            })
        }
    }
    render() {
        const { completed, id, title } = this.props.todo
        const hc = this.props.handleChange
        const dc = this.props.deleteClick

        let viewMode = {}
        let editingMode = {}

        if (this.state.editing) viewMode.display = "none"
        else editingMode.display = "none"

        const completedStyle = {
            fontStyle: "italic",
            color: "#595959",
            opacity: 0.4,
            textDecoration: "line-through",
        }
        return (
            <li className={styles.item}>
                <div onDoubleClick={this.handleEditing} style={viewMode}>
                    <input
                        type="checkbox"
                        className={styles.checkbox}
                        checked={completed}
                        onChange={() => { hc(id) }}
                    />
                    <button onClick={() => { dc(id) }}>Delete</button>
                    <span style={completed ? completedStyle : null}>
                        {title}
                    </span>
                </div>
                <input type="text"
                className={styles.textInput}
                value={title}
                onChange={e => {
                    this.props.setUpdate(e.target.value, id)
                }}
                onKeyDown={this.handleUpdateDone}
                style={editingMode}
                />
            </li>
        )
    }

}

export default TodoItem;