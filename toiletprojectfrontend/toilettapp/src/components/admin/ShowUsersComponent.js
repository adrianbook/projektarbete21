import {useState} from "react";
import {blockUser, unBlockUser , addRole} from "../../servercalls/Calls";
import {render} from "react-dom";

const ShowUsersComponent = (props) => {
    const [userData, setUserData] = useState(
        mapUserdata(props.user)
    )
    const [roleToAdd, setRoleToAdd] = useState("")

    function mapUserdata(userDataToMap) {
            return {
                id: userDataToMap.id,
                username: userDataToMap.username,
                name: userDataToMap.name,
                email: userDataToMap.email,
                blocked: userDataToMap.blocked.toString(),
                roles: userDataToMap.roles.map(r => r.name + " ")
            }
    }
    function blockUserClick(e) {
        blockUser(e.target.value)
            .then(res => {
                setUserData(mapUserdata(res))
            })

    }
    function unBlockUserClick(e) {
        unBlockUser(e.target.value)
            .then(res => {
                setUserData(mapUserdata(res))
            })
    }

        let unblockButton = <button value={userData.username} onClick={e => unBlockUserClick(e)} >Unblock User</button>

        let blockButton = <button value={userData.username} onClick={ e => blockUserClick(e)} >Block User</button>

    let button = userData.blocked === "true" ? unblockButton : blockButton
    let addRoleHtml
    if (userData.blocked === "false") {
        addRoleHtml =  <div><p>Add role to user</p>
        <select onChange={e => setRoleToAdd({username: userData.username, rolename: e.target.value})}>
            <option defaultValue={"Choose here"}>Choose here</option>
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
            .then(res =>
            setUserData(mapUserdata(res)))
            .catch(e => {
                prompt(e.message)
            })
    }

    return (
        <div id={"userContainer"} style={{border: "2px solid black", padding: "3px"}}>
            <ul key={"userdata: " + userData.id}>
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