import { Link } from "react-router-dom";
import { useState, useEffect } from "react";
import "../App.css"
import MapComponent from "../components/MapComponent";
import scraping from "../util/scraping"

function MapPage(props) {
  const [markers, setMarkers] = useState([
    ...scraping 
  ])

  let newToilet = props?.location?.state || false

  useEffect(() => {
  let fetching =  fetch("http://localhost:9091/api/v1/toilets/getalltoilets", {method: "GET"})
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

  useEffect(() =>{
    if (newToilet) {
      setMarkers((prevState) => ([
        ...prevState.markers,
        newToilet
      ]))
    }
  }, [newToilet])

  const addMarker = marker => {
    setMarkers([
      ...markers,
      marker
    ])
  }

  return (
  <div>
  <h1>Toilett..</h1>
  <MapComponent pos={[57.703, 11.964]} zoom={13} scrollwheel={false} markers={markers}/>
  </div>
  );
  /*
  */
}

export default MapPage;
