import {React, useState} from "react";

function TodoItem(props) {
    /*
    const [isChecked, setChecked] = useState(false)
    
    const handleChange = e => {
        setChecked(!isChecked)
    }
    */
    return (
        <li>
            <input type="checkbox" name="isChecked" checked={props.todo.completed} onChange={console.log("checked")} />{props.todo.title}
        </li>
    )
}

export default TodoItem;