import {useState, useEffect} from "react";
import { useNavigate } from "react-router-dom";
import {getAllUsers } from "../servercalls/Calls";
import ShowUsersComponent from "../components/admin/ShowUsersComponent";
import SearchForUser from "../components/admin/SearchForUser";
import ShowReportsComponent from "../components/admin/ShowReportsComponent";




export default function Admin() {
    const navigate = useNavigate()
    const [users, setUsers] = useState([])
    const [showUsers, setShowUsers] = useState("none")
    const [toggleUsers, setToggleUsers] = useState("Show users")




    useEffect( () => {
            getAllUsers()
                .then(res => {
                    setUsers(res)
                }).catch(e => {
                    navigate( {
                        pathname: '/',
                    })
            })
        }, [setUsers]
    )
    function hideAndShowUsers() {
        if (showUsers === "none") {
            setShowUsers("block")
            setToggleUsers("Hide Users")
        } else {
            setShowUsers("none")
            setToggleUsers("Show Users")
        }

    }

    return(
        <div>
                <h2>ADMINPAGE</h2>
            <div style={{border: "2px solid black", padding: "3px"}}>
            <h2>USERFUNCTIONS</h2>
                <button onClick={hideAndShowUsers}>{toggleUsers}</button>
            <span style={{display: showUsers}} >
                {users.map( user => (
                    <ShowUsersComponent user={user}  />
                ))}
            </span>
                <SearchForUser/>
            </div>
            <div style={{border: "2px solid black", padding: "3px"}}>
                <h2>TOILETFUNCTIONS</h2>
                <ShowReportsComponent />
            </div>
        </div>

    )
}