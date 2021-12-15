import { useState } from "react";
import { BrowserRouter, Route, Routes, Link } from "react-router-dom";
import MapPage from "./routes/MapPage";
import Login from "./routes/Login";
import CreateUser from "./routes/CreateUser";
import AddToilet from "./routes/AddToilet";
import { loginCall } from "./servercalls/Calls";


const App = () => {
  const [loginInfo, setLoginInfo] = useState({
    username: "",
    password: "",
    loggedIn: false
});

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
    loginCall(loginInfo)
        .then( token => {
            sessionStorage.setItem("loggedInUser", token) 
      setLoginInfo({
        ...loginInfo,
        loggedIn: true
      })
    }).catch((e) => {
      prompt("failed to log in")
      console.log(e)
    })
  }

  const logOut = () => {
    sessionStorage.setItem("loggedInUser", "") 
    setLoginInfo({
      ...loginInfo,
      loggedIn: false
    })
  setLoginInfo({
    username: "",
    password: ""
  })
  }

  let loggedInVisible = loginInfo.loggedIn ? {} : {display: "none"}
  let loggedOutVisible = loginInfo.loggedIn ? {display: "none"} : {}




    return (
  <BrowserRouter>
  <button onClick={logOut} style={loggedInVisible}>LOG OUT</button> | {" "}
  <button style={loggedOutVisible}><Link to="/login">Login</Link></button> |{" "}
  <button style={loggedOutVisible}><Link to="/createuser">Create User</Link> </button>|{" "}
  <button style={loggedInVisible}><Link to="/addtoilet">Add new loo</Link></button>

    <Routes>
      <Route path="/" element={<MapPage loggedIn={loginInfo.loggedIn} />} />
      <Route path="/login" element={<Login
                                    loginInfo={loginInfo}
                                    handleChange={handleChange}
                                    handleSubmit={handleSubmit}/>}/>
      <Route path="/createuser" element={<CreateUser setLoginInfo={setLoginInfo}/>}/>
        <Route path="/addToilet" element={<AddToilet/>}/>
    </Routes>
  </BrowserRouter>
        )}

export default App;