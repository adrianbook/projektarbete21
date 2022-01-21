import {useState, useEffect} from "react";
import { useNavigate } from "react-router-dom";
import {getAllUsers, fetchUserByUsername , getAllReports} from "../servercalls/Calls";
import ShowUsersComponent from "../components/ShowUsersComponent";
import {render} from "react-dom";



export default function Admin() {
    const navigate = useNavigate()
    const [users, setUsers] = useState([])
    const [usernameToSearch, setUsernameToSearch] = useState("")

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
    function myShow() {
        var x = document.getElementById("mySpan");
        if (x.style.display === "none") {
            x.style.display = "block";
        } else {
            x.style.display = "none";
        }
    }

    function searchForUser(e) {
        e.preventDefault()
        fetchUserByUsername(usernameToSearch)
            .then(res => {
                showSingleUser(res)
            }).catch(e => {
                prompt(e.message)
            }

        )
    }
    const handleChange = e => {
        setUsernameToSearch(e.target.value)
    };

    const showSingleUser = (user) => {
      render( <ShowUsersComponent id={user.id} username={user.username} name={user.name} email={user.email} blocked={user.blocked.toString()} roles={user.roles.map(r => r.name + " ")} />,
      document.getElementById("singleUserContainer")
      )
    }
    function getAllReportsClick() {
       getAllReports()
    }
    return(
        <div>
                <h2>ADMINPAGE</h2>
            <div style={{border: "2px solid black", padding: "3px"}}>
            <h2>USERFUNCTIONS</h2>
                <button onClick={myShow}>Show users</button>
            <span id={"mySpan"}  style={{display: "none"}}>
                {users.map( user => (
                    <ShowUsersComponent id={user.id} username={user.username} name={user.name} email={user.email} blocked={user.blocked.toString()} roles={user.roles.map(r => r.name + " ")} />
                ))}
            </span>
            <span>
                <p>Search for user:</p>
                <form onSubmit={e => searchForUser(e)}>
                    <label>
                    Name:
                        <input
                        type="text"
                        name="name"
                        value={usernameToSearch}
                        placeholder="enter username"
                        onChange={handleChange}
                        />
                    </label>
                    <br/>
                    <br/>
                    <button>submit</button>
                </form>
                <span id={"singleUserContainer"}></span>
            </span>
            </div>
            <div style={{border: "2px solid black", padding: "3px"}}>
                <h2>TOILETFUNCTIONS</h2>
                <button onClick={getAllReportsClick}>asdkpoaskfpo</button>
            </div>
        </div>

    )
}