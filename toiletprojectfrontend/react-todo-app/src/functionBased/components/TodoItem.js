import React, { useState, useEffect } from "react";
import { FaTrash } from "react-icons/fa";
import styles from "./TodoItem.module.css"

const TodoItem = props => {
    const [editing, setEditing] = useState(false)
    const { completed, id, title } = props.todo

    const handleEditing = () => {
        setEditing(true)
    }
    const handleUpdateDone = e => {
        if (e.key === 'Enter') setEditing(false)
    }
    
    useEffect(() => {
        return () => {
            console.log("cleaning up")
        }
    }, [])

    let viewMode = {}
    let editingMode = {}

    if (editing) viewMode.display = "none"
    else editingMode.display = "none"

    const completedStyle = {
        fontStyle: "italic",
        color: "#595959",
        opacity: 0.4,
        textDecoration: "line-through",
    }
    return (
        <li className={styles.item}>
            <div onDoubleClick={handleEditing} style={viewMode}>
                <input
                    type="checkbox"
                    className={styles.checkbox}
                    checked={completed}
                    onChange={() => { props.handleChange(id) }}
                />
                <span style={completed ? completedStyle : null}>
                    {title}
                </span>
                <FaTrash className={styles.delete} onClick={() => { props.deleteClick(id) }}/>
            </div>
            <input type="text"
                className={styles.textInput}
                value={title}
                onChange={e => {
                    props.setUpdate(e.target.value, id)
                }}
                onKeyDown={handleUpdateDone}
                style={editingMode}
            />
        </li>
    )
    
//<button onClick={() => { props.deleteClick(id) }}>Delete</button>
    
}

export default TodoItem;