import { Link } from "react-router-dom";
import { useState } from "react";

export default function Login() {
  const [loginInfo, setLoginInfo] = useState({
      username: "",
      password: ""
  });
  const [loggedIn, setLoggedIn] = useState(false)

  const handleChange = (e) => {
    setLoginInfo({
      ...loginInfo,
      [e.target.name]: e.target.value
    })
  }

  const handleSubmit = (e) => {
    e.preventDefault()
    console.log(`name: ${loginInfo.username} \npassword: ${loginInfo.password}`)
    //login to server
    const loginInfoJson = JSON.stringify(loginInfo)
    localStorage.setItem("loggedInUser", loginInfoJson) //kan beskådas i devTools -> application -> localstorage på chrome
    setLoggedIn(true)
  }

  const logOut = () => {
    setLoggedIn(false)
    setLoginInfo({
      username: "",
      password: ""
    })
  }

  let loggedInMode = {}
  let notLoggedInMode = {}

  if (loggedIn) notLoggedInMode.display = "none"
  else loggedInMode.display = "none"

    return (
      <main style={{ padding: "1rem 0" }}>
        <h2>Login</h2>
        <form onSubmit={handleSubmit} style={notLoggedInMode}>
        <label>
          Username:
          <input
          type="text"
          value={loginInfo.username}
          name="username"
          placeholder="User name here"
          onChange={handleChange}/>
        </label>
        <br/> 
        <label>
          Password:
          <input
          type="password"
          value={loginInfo.password}
          name="password"
          placeholder="Password here"
          onChange={handleChange}/>
        </label>
        <br/>
        <button type="submit">
          submit
          </button>
          </form>
        <br/>
        <div style={loggedInMode}>
          <h3>You are logged in</h3>
          <br/>
          <button onClick={logOut}>Log out</button>
        </div>
        <Link to="/createuser">New user?</Link>
        <br/>
        <Link to="/">To toiletmap</Link>
      </main>
    );
  }