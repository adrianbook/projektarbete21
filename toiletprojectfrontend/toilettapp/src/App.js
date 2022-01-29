import { useState, useEffect } from "react";
import {BrowserRouter, Route, Routes, Link} from "react-router-dom";
import MapPage from "./routes/MapPage";
import CreateUserComponent from "./components/CreateUserComponent";
import Admin from "./routes/Admin";
import { loginCall, verifyUser } from "./servercalls/Calls";
import 'bootstrap/dist/css/bootstrap.min.css';
import Navbar from 'react-bootstrap/Navbar'
import Container from "react-bootstrap/Container";
import toiletIcon from "./static/icons/toilet3.png";
import LoginComponent from "./components/LoginComponent";


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
              ...loginInfo,
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
  }





    return (
  <BrowserRouter>
      <Navbar expand="lg" variant="dark" bg="dark">
        <Container>
          <Navbar.Brand href="/">
            <img
                src={toiletIcon}
                width="30"
                height="30"
                className="d-inline-block align-top"
                alt="Toilet-logo"
            />{' '}
            LooCation
          </Navbar.Brand>
        </Container>
        <div style={display.loggedOutVisible}>

        <Container>
          Login:
            <LoginComponent
                loginInfo={loginInfo}
                handleChange={handleChange}
                handleSubmit={handleSubmit}
            />
          <CreateUserComponent setLoginInfo={setLoginInfo}/>
          </Container>

        </div>
        <button onClick={logOut} style={display.loggedInVisible}><Link to={'/'}>LOG OUT</Link> </button>
      </Navbar>





    <Routes>
      <Route path="/" element={<MapPage loggedIn={loginInfo.loggedIn} />} />
      <Route path="/admin" element={<Admin/>}/>
    </Routes>
  </BrowserRouter>
        )}

export default App;