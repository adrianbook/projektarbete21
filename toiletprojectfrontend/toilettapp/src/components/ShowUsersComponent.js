import {useState} from "react";
import {blockUser, unBlockUser , addRole} from "../servercalls/Calls";

const ShowUsersComponent = (props) => {
    const [userData] = useState({
        id: props.id,
        username: props.username,
        name: props.name,
        email: props.email,
        blocked: props.blocked,
        roles: props.roles
    })
    const [roleToAdd, setRoleToAdd] = useState("")

    function blockUserClick(e) {
        blockUser(e.target.value)
        window.location.reload(false)
    }
    function unBlockUserClick(e) {
        unBlockUser(e.target.value)
        window.location.reload(false)
    }
    let button
    if (userData.blocked === "true") {
        button = <button value={userData.username} onClick={e => unBlockUserClick(e)}>Unblock User</button>
    } else {
        button = <button value={userData.username} onClick={ e => blockUserClick(e)}>Block User</button>
    }
    let addRoleHtml
    if (userData.blocked === "false") {
        addRoleHtml =  <div><p>Add role to user</p>
        <select onChange={e => setRoleToAdd({username: userData.username, rolename: e.target.value})}>
            <option defaultValue={""} disabled hidden>Choose here</option>
            <option value={"ROLE_ADMIN"}>Admin</option>
            <option value={"ROLE_SUPER_ADMIN"}>Super Admin</option>
        </select>

        <button onClick={addRoleToThisUser}>Add Role</button>
        </div>
    }  else {
        addRoleHtml = null
    }
    function addRoleToThisUser() {
        addRole(roleToAdd)
            .catch(e => {
                prompt(e.message)
            })
    }

    return (
        <div style={{border: "2px solid black", padding: "3px"}}>
            <ul key={"userdata"}>
                <li key={"id " + userData.id}>id: {userData.id}</li>
                <li key={"username " + userData.username}>username: {userData.username}</li>
                <li key={"name " + userData.name}>name: {userData.name}</li>
                <li key={"email " +userData.email}>email: {userData.email}</li>
                <li key={userData.id + userData.blocked}>blocked: {userData.blocked}</li>
                <li key={"roles" + userData.roles}>roles: {userData.roles}</li>
            </ul>
            {button}
            {addRoleHtml}
        </div>
    )
}
export default ShowUsersComponent