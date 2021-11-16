import './App.css';
import { useState } from 'react';

function App() {
  const [state, setState] = useState({
    fname: "",
    lname:"",
    message:"",
    car:"",
    isChecked: false,
    gender: "",
    price: 0
  })

  const handleChange = e => {
    const value = e.target.type === "checkbox" ? e.target.checked : e.target.value
    setState({
      ...state,
      [e.target.name]: value
    })
  }

  return (
    <div>
      <h1>React Form Handling</h1>
      <form>
        <label>
          First Name: <input type="text" name={Object.keys(state)[0]} value={state.fname} onChange={handleChange}/>
        </label>
        <br/>
        <label>
          Last Name: <input type="text" value={state.lname} name={Object.keys(state)[1]} onChange={handleChange}/>
        </label>
        <br/>
        <label>
          Message: <textarea value={state.message} name={Object.keys(state)[2]} onChange={handleChange}/>
        </label>
        <br/>
        <label>
          Car: <select name="car" value={state.car} onChange={handleChange}>
            <option value="volvo">Volvo</option>
            <option value="saab">Saab</option>
            <option value="bög">Ferrari</option>
          </select>
        </label>
        <br/>
        <label>
          I am gay <input type="checkbox" name="isChecked" checked={state.isChecked} onChange={handleChange}/>
        </label>
        <br/>
        <label>
          <input type="radio" name="gender" value="male" checked={state.gender === "male"} onChange={handleChange}/> Male
        </label>
        <label>
          <input type="radio" name="gender" value="female" checked={state.gender === "female"} onChange={handleChange}/> Female
        </label>
        <br/>
        <label>
          <input type="range" value={state.price} name="price" min="0" max="50" onChange={handleChange}/>
        </label>
      </form>
      <h5>Name: {state.fname} {state.lname}</h5>
      <p>Message: {state.message}</p>
      <p>Car: {state.car}</p>
      <p>Du är {state.isChecked ? "" : "inte"} {state.gender === "male" ? "bög" : "flata"}</p>
      <p>Pris: {state.price}</p>
    </div>
  );
}

export default App;
