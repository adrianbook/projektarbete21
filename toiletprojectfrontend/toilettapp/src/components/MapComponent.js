import { MapContainer, TileLayer, Marker, Popup , useMap, useMapEvents} from 'react-leaflet';
import React, { useEffect, useState } from "react";
import AddRatingComponent from "./AddRatingComponent";
import { sendNewToiletToServer } from '../servercalls/Calls';


function ClickEvent(props) {
    const [position, setPosition] = useState(null);
    const map = useMapEvents({
        click: (e) => {
          setPosition(e.latlng);
          map.flyTo(e.latlng)
        }
      })
    return position === null ? null : (
    <Marker position={position}>
        <Popup >
        Latitude : {position.lat.toFixed(3)} <br />
        Longitude : {position.lng.toFixed(3)}<br />
        <button  onClick={ () => 
            sendNewToiletToServer({latitude: position.lat, longitude: position.lng})
                .then(r => props.addMarker({thispos:[r.latitude, r.longitude], id: r.id}))
                .catch((error) =>{
                    sessionStorage.setItem("loggedInUser", "")
                    window.location.reload(false)
                    console.log(error)
                    prompt(error.message)
                })}>
        Add loo 
        </button>
        </Popup>
    </Marker>
    );
}

function LocationMarker() {
  const [position, setPosition] = useState(null);
  const mymap = useMap();
 
  useEffect(() => {
    mymap.locate().on("locationfound", function (e) {
      setPosition(e.latlng);
      mymap.flyTo(e.latlng, mymap.getZoom());
    });
  }, [mymap]);

  return position === null ? null : (
      <Marker position={position}>
        <Popup>
          You are here. <br />
        </Popup>
      </Marker>
  );
}

const MapComponent = (props) => {
    const saveToiletToRate = (e) => {
        e.preventDefault();

        sessionStorage.setItem("toiletToRate", e.target.name)
        console.log(e.target.name); //will give you the value continue
    }
    function showRatingForm(e) {
        let loggedIn = sessionStorage.getItem("loggedInUser")
        if(loggedIn === null || !loggedIn.startsWith("Bearer") ) {
            prompt("You have to be logged in to rate")
        } else {
            saveToiletToRate(e)
            var x = document.getElementById("rateButton");
            if (x.style.display === "none") {
                x.style.display = "flex";
            } else {
                x.style.display = "none";
            }
            var x = document.getElementById("mySpan");
            if (x.style.display === "none") {
                x.style.display = "flex";
            } else {
                x.style.display = "none";
            }
        }

    }

  return (
      <MapContainer center={props.pos} zoom={props.zoom} id="map">
        <TileLayer
            attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"/>
        <LocationMarker />
        <ClickEvent addMarker={props.addMarker}/>

          {props.markers?.map( marker =>  (

            <Marker position={marker.thispos} key={props.markers.indexOf(marker)}>
              <Popup>
                  ID: {marker.id}<br />
                  Avarege rating {marker.avgRat}
                  <br/>
                  <button id="rateButton" name={marker.id} onClick={showRatingForm}>Rate this toilet</button>
                  <span id={"mySpan"}  style={{display: "none"}}>
                    <AddRatingComponent />
                  </span>


              </Popup>
            </Marker>
        ))
        }

      </MapContainer>
  )}

export default MapComponent