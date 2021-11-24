import { Link } from "react-router-dom";
import { useState, useEffect } from "react";
import "./App.css"
import MapComponent from "./components/MapComponent";

function App() {
  const [markers, setMarkers] = useState([
    [57.703, 11.964],
    [57.709, 11.97]
  ])

  useEffect(() => {
  let fetching =  fetch("http://localhost:8080/toilets", {method: "GET"})
                fetching.then(res => {
                console.log(res.status)
                return  res.json()
                })
                .then(obj => {
                  console.log(obj)
                    const positions = []
                    obj.toilets.forEach(pos => {
                        positions.push([pos.longitude, pos.latitude])
                    });
                    return positions
                })
                .then(positions => {
                  setMarkers(positions)
                  console.log(fetching)
                })
               .catch(error => {
                 console.log("Error: "+error)
               })
  
  }, [setMarkers])

  return (
  <div>
  <h1>Toilett..</h1>
  <nav style={{
    borderBottom: "solid 1px",
    paddingBottom: "1rem"
  }}>
  <Link to="/login">Login</Link> |{" "}
  <Link to="/createuser">Create User</Link>
  </nav>
  <MapComponent pos={[57.703, 11.964]} zoom={13} scrollwheel={false} markers={markers}/>
  </div>
  );
  /*
  */
}

export default App;
