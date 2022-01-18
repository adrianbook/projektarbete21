import { MapContainer, TileLayer, Marker, Popup , useMap} from 'react-leaflet';
import React, { useEffect, useState } from "react";
import AddRatingComponent from "./AddRatingComponent";



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
        if(sessionStorage.getItem("loggedInUser").startsWith("Bearer")) {
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
        } else {
            prompt("You have to be logged in to rate")
        }

    }

  return (
      <MapContainer center={props.pos} zoom={props.zoom} scrollWheelZoom={props.scrollwheel} id="map">
        <TileLayer
            attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        />
        <LocationMarker />

          {props.markers?.map( marker =>  (

            <Marker position={marker.thispos} key={props.markers.indexOf(marker)}>
              <Popup>
                  {marker.id}<br />
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