import {useState} from "react";
import {fetchUserByUsername} from "../servercalls/Calls";
import {render} from "react-dom";
import ShowUsersComponent from "./ShowUsersComponent";

const SearchForUser = () => {
    const [usernameToSearch, setUsernameToSearch] = useState("")

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
                <span id={"singleUserContainer"}></span>
            </span>
}
export default SearchForUser