import {useEffect} from "react";
import {Link, useNavigate} from "react-router-dom";

const LoginComponent = (props) => {
    const navigate = useNavigate()
    let loggedIn = props.loginInfo.loggedIn

    useEffect(() => {
        if (loggedIn){
            navigate({
                pathname: "/"
            })
        }
    }, [loggedIn, navigate])
    return (
        <main>
            <form onSubmit={props.handleSubmit}>
                <label>
                    <input
                        type="text"
                        value={props.loginInfo.username}
                        name="username"
                        placeholder="User name here"
                        onChange={props.handleChange}/>
                </label>
                <label>
                    <input
                        type="password"
                        value={props.loginInfo.password}
                        name="password"
                        placeholder="Password here"
                        onChange={props.handleChange}/>
                </label>
                <button type="submit">
                    submit
                </button>
            </form>
        </main>
    );
}

export default LoginComponent