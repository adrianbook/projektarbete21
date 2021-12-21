import { useState, useEffect } from "react";
import { BrowserRouter, Route, Routes, Link } from "react-router-dom";
import MapPage from "./routes/MapPage";
import Login from "./routes/Login";
import CreateUser from "./routes/CreateUser";
import AddToilet from "./routes/AddToilet";
import { loginCall, verifyUser } from "./servercalls/Calls";


const App = () => {
  const [loginInfo, setLoginInfo] = useState({
    username: "",
    password: "",
    loggedIn: false
  });

  const [display, setDisplay ] = useState({
    loggedInVisible: loginInfo.loggedIn ? {} : {display: "none"},
    loggedOutVisible: loginInfo.loggedIn ? {display: "none"} : {}
  })

  useEffect(() => {
    const token = sessionStorage.getItem("loggedInUser")
    if (token) {
      verifyUser(token)
        .then(tokenValid => {
          if (tokenValid) {
            setLoginInfo({
              loggedIn: true
            })
          }
        })
    }
  }, [setLoginInfo])

  useEffect(() => {
    setDisplay({
      loggedInVisible: loginInfo.loggedIn ? {} : {display: "none"},
      loggedOutVisible: loginInfo.loggedIn ? {display: "none"} : {}
    })
  }, [loginInfo.loggedIn, setDisplay])

  const handleChange = (e) => {
    setLoginInfo({
      ...loginInfo,
      [e.target.name]: e.target.value
    })
  }

  const handleSubmit = (e) => {
    e.preventDefault()
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





    return (
  <BrowserRouter>
  <button onClick={logOut} style={display.loggedInVisible}>LOG OUT</button> | {" "}
  <button style={display.loggedOutVisible}><Link to="/login">Login</Link></button> |{" "}
  <button style={display.loggedOutVisible}><Link to="/createuser">Create User</Link> </button>|{" "}
  <button style={display.loggedInVisible}><Link to="/addtoilet">Add new loo</Link></button>

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