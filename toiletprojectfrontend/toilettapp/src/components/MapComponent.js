import { MapContainer, TileLayer, Marker, Popup , useMap, useMapEvents} from 'react-leaflet';
import React, { useEffect, useState } from "react";
import PopupContainer from "./popups/PopupContainer";
import L from "leaflet";
import toiletIcon from "../static/icons/toilet3.png";

const icon = new L.Icon({
    iconUrl: toiletIcon,
    iconRetinaUrl: toiletIcon,
    iconSize: [60, 45],
    iconAnchor: [29, 45],
    className: "toiletIcon"
    /*shadowSize: [50, 64],
    ,
    shadowAnchor: [4, 62],
    popupAnchor: [-3, -76],*/
})

function ClickEvent(props) {
    const [position, setPosition] = useState(null);
    const map = useMapEvents({
        click: (e) => {
          setPosition(e.latlng);
          map.flyTo(e.latlng)
        }
      })
    return position ? (
        <>
        <Marker position={position}>
            <Popup >
            <PopupContainer
            marker={{thispos : [position.lat, position.lng]}}
            addMarker={props.addMarker}
            type="toilet"/>
            </Popup>
        </Marker>
        </>
    ) : null;
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

  return (
      <MapContainer center={props.pos} zoom={props.zoom} id="map">
        <TileLayer
            attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"/>
        <LocationMarker />
        <ClickEvent addMarker={props.addMarker}/>

            {props.markers?.map( marker =>  (
            <Marker position={marker.thispos} key={props.markers.indexOf(marker)} icon={icon}>
              <Popup>
                <PopupContainer
                marker={marker}
                addMarker={props.addMarker}
                />
              </Popup>
            </Marker>))}

      </MapContainer>
  )
}

export default MapComponent