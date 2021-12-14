import { Link, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";


export default function Login(props) {
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
      <main style={{ padding: "1rem 0" }}>
        <h2>Login</h2>
        <form onSubmit={props.handleSubmit}>
        <label>
          Username:
          <input
          type="text"
          value={props.loginInfo.username}
          name="username"
          placeholder="User name here"
          onChange={props.handleChange}/>
        </label>
        <br/> 
        <label>
          Password:
          <input
          type="password"
          value={props.loginInfo.password}
          name="password"
          placeholder="Password here"
          onChange={props.handleChange}/>
        </label>
        <br/>
        <button type="submit">
          submit
          </button>
          </form>
        <br/>
        <Link to="/createuser">New user?</Link>
        <br/>
        <Link to="/">To toiletmap</Link>
      </main>
    );
  }