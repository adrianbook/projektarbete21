import { Link, useNavigate } from "react-router-dom";
import { useState } from "react";
import { sendNewUserToServer, loginCall }  from "../servercalls/Calls"

export default function CreateUser(props) {
  const [userData, setUserData] = useState({
    name: "",
    username: "",
    email: "",
    password: "",
    confirmPassword:""
  });
  const navigate = useNavigate()

  const handleChange = e => {
    setUserData({
      ...userData,
      [e.target.name]: e.target.value
    })
  };

  const handleSubmit = e => {
    e.preventDefault()
    if (userData.password !== userData.confirmPassword) prompt("confirm password does not match password")
    else {
      console.log(userData)
      sendNewUserToServer(
          {name: userData.name,
                  username: userData.username,
                  email: userData.email,
                  password: userData.password
                  })
          .then( userAdded => {
            if (!userAdded) throw new Error("error creating user")
            return loginCall({username: userData.username, password: userData.password})  
          })
          .catch((e) => {
            prompt("Error creating user")
            console.log(e)
          }).then((token) => {
            sessionStorage.setItem("loggedInUser", token)
            //post userdata to server
            setUserData({
              name: "",
              username: "",
              email: "",
              password: "",
              confirmPassword:""
            })
            props.setLoginInfo({
              loggedIn: true
            })
            navigate({
              pathname: "/"
            })
          })
    }
  };

    return (
      <main style={{ padding: "1rem 0" }}>
        <h2>Create User</h2>
        <form onSubmit={handleSubmit}>
          <label>
            Name:
            <input
            type="text"
            value={userData.name}
            name="name"
            placeholder="Enter your name"
            onChange={handleChange}
            />
          </label>
          <br/>
          <label>
            Username:
            <input 
            type="text"
            value={userData.username}
            name="username"
            placeholder="Enter desired username"
            onChange={handleChange}
            />
          </label>
          <br/>
          <label>
            Email:
            <input 
            type="text"
            value={userData.email}
            name="email"
            placeholder="Enter email"
            onChange={handleChange}
            />
          </label>
          <br/>
          <label>
            Password:
            <input 
            type="password"
            value={userData.password}
            name="password"
            placeholder="Enter password"
            onChange={handleChange}
            />
          </label>
          <br/>
          <label>
            Confirm password:
            <input 
            type="password"
            value={userData.confirmPassword}
            name="confirmPassword"
            placeholder="Confirm password"
            onChange={handleChange}
            />
          </label>
          <br/>
          <button>submit</button>
        </form>
        <br/>
      </main>
    );
  }