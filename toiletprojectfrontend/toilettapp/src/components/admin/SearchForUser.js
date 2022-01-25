import {useState} from "react";
import {fetchUserByUsername} from "../../servercalls/Calls";
import ShowUsersComponent from "./ShowUsersComponent";

const SearchForUser = () => {
    const [usernameToSearch, setUsernameToSearch] = useState("")
    const [displaySingleUser, setDisplaySingleUser] = useState("none")
    const [userToShow, setUserToShow] = useState()

    function searchForUser(e) {
        e.preventDefault()
        fetchUserByUsername(usernameToSearch)
            .then(res => {
                setUserToShow(res)
                setDisplaySingleUser("block")
            }).catch(e => {
                prompt(e.message)
            }
        )
    }

    const handleChange = e => {
        setUsernameToSearch(e.target.value)
    };



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
                {userToShow?
                <ShowUsersComponent user={userToShow}/>
                    : null}
            </span>
            <button onClick={() => setDisplaySingleUser("none")}>Hide</button>
        </div>
            </span>
}
export default SearchForUser