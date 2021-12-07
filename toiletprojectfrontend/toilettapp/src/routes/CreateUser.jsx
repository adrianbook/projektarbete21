import { Link } from "react-router-dom";
import { useState } from "react";
import { sendNewUserToServer }  from "../servercalls/Calls"

export default function CreateUser() {
  const [userData, setUserData] = useState({
    name: "",
    username: "",
    email: "",
    password: "",
    confirmPassword:""
  });
  const [userCreated, setUserCreated] = useState(false)

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
          .then( res => {
            // TODO fixa bra meddelande i prompten
            if (!res) {prompt("Error creating user")}
            // TODO logga in direkt här
            setUserCreated(res)
            setUserData({
              name: "",
              username: "",
              email: "",
              password: "",
              confirmPassword:""
            })
          })
      //post userdata to server

    }
  };

  const createNewUser = e => {
    setUserCreated(false)
  }

  let createUserMode = {}
  let userCreatedMode = {}

  if (userCreated) createUserMode.display = "none"
  else userCreatedMode.display = "none"

    return (
      <main style={{ padding: "1rem 0" }}>
        <h2>Create User</h2>
        <form onSubmit={handleSubmit} style={createUserMode}>
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
        <div style={userCreatedMode}>
          <h3>Account created</h3>
          <br/>
          <button onClick={createNewUser}>Create another</button>
        </div>
        <Link to="/">To toiletmap</Link>
      </main>
    );
  }