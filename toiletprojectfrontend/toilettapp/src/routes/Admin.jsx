import {useState, useEffect} from "react";
import { useNavigate } from "react-router-dom";
import {getAllUsers , getAllReports} from "../servercalls/Calls";
import ShowUsersComponent from "../components/ShowUsersComponent";
import SearchForUser from "../components/SearchForUser";




export default function Admin() {
    const navigate = useNavigate()
    const [users, setUsers] = useState([])


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


    function getAllReportsClick() {
       getAllReports()
           .then(res => {
               console.log(res)
           })
    }
    return(
        <div>
                <h2>ADMINPAGE</h2>
            <div style={{border: "2px solid black", padding: "3px"}}>
            <h2>USERFUNCTIONS</h2>
                <button onClick={myShow}>Toggle users</button>
            <span id={"mySpan"}  style={{display: "none"}}>
                {users.map( user => (
                    <ShowUsersComponent user={user}  />
                ))}
            </span>
                <SearchForUser/>
            </div>
            <div style={{border: "2px solid black", padding: "3px"}}>
                <h2>TOILETFUNCTIONS</h2>
                <button onClick={getAllReportsClick}>asdkpoaskfpo</button>
            </div>
        </div>

    )
}