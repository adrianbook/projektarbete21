import { MapContainer, TileLayer, Marker, Popup , useMap, useMapEvents} from 'react-leaflet';
import React, { useEffect, useState } from "react";
import PopupContainer from "./popups/PopupContainer";
import L from "leaflet";
import toiletIcon from "../static/icons/toilet3.png";
import positionIcon from "../static/icons/happiness.png";

const icon = new L.Icon({
    iconUrl: toiletIcon,
    iconRetinaUrl: toiletIcon,
    iconSize: [43, 45],
    iconAnchor: [20, 45],
    className: "toiletIcon",
    popupAnchor: [2, -35]
})

const happyIcon = new L.Icon({
    iconUrl: positionIcon,
    iconRetinaUrl: positionIcon,
    iconSize: [35, 35],
    iconAnchor: [19, 10],
    className: "toiletIcon"
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
      <Marker icon={happyIcon} position={position}>
        <Popup>
          You are here. <br />
        </Popup>
      </Marker>
  );
}

const MapComponent = (props) => {

  const [popupOpen, setPopupOpen] = useState(true)

  return (
      <MapContainer center={props.pos} zoom={props.zoom} id="map">
        <TileLayer
            attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"/>
        <LocationMarker />
        <ClickEvent addMarker={props.addMarker}/>

            {props.markers?.map( marker =>  (
            <Marker
              position={marker.thispos}
              key={props.markers.indexOf(marker)}
              icon={icon}
              eventHandlers={{
                popupclose: (e) => {setPopupOpen(false)},
                popupopen: (e) => {setPopupOpen(true)}
              }}
              >
              <Popup>
                <PopupContainer
                marker={marker}
                addMarker={props.addMarker}
                open={popupOpen}
                />
              </Popup>
            </Marker>))}

      </MapContainer>
  )
}

export default MapComponent