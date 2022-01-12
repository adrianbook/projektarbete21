import { Link } from "react-router-dom";
import { useState, useEffect } from "react";
import "../App.css"
import MapComponent from "../components/MapComponent";
import scraping from "../util/scraping"
import {getAllToiletsCall} from "../servercalls/Calls";


function MapPage(props) {
  const [markers, setMarkers] = useState([
    ...scraping,
  ])

  let newToilet = props?.location?.state || false


  useEffect(() => {
    getAllToiletsCall()
        .then(res => {
          setMarkers(res)
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
