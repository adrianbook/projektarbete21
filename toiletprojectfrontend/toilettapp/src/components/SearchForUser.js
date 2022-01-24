import {useState} from "react";
import {fetchUserByUsername} from "../servercalls/Calls";
import {render} from "react-dom";
import ShowUsersComponent from "./ShowUsersComponent";

const SearchForUser = () => {
    const [usernameToSearch, setUsernameToSearch] = useState("")
    const [displaySingleUser, setDisplaySingleUser] = useState("none")

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
        setDisplaySingleUser("block")
        render( <ShowUsersComponent user={user}/>,
            document.getElementById("singleUserContainer")
        )
    }

    return <span>
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
        <div style={{display: displaySingleUser}}>
            <span  id={"singleUserContainer"}>

            </span>
            <button onClick={() => setDisplaySingleUser("none")}>Hide</button>
        </div>
            </span>
}
export default SearchForUser