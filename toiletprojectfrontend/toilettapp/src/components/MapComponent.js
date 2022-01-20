import { MapContainer, TileLayer, Marker, Popup , useMap, useMapEvents} from 'react-leaflet';
import React, { useEffect, useState } from "react";
import PopupContainer from "./popups/PopupContainer"


function ClickEvent(props) {
    const [position, setPosition] = useState(null);
    const map = useMapEvents({
        click: (e) => {
          setPosition(e.latlng);
          map.flyTo(e.latlng)
        }
      })
    return position ? (
    <Marker position={position}>
        <Popup >
          <PopupContainer
          marker={{latitude: position.lat.toFixed(3), longitude: position.lng.toFixed(3)}}
          addMarker={props.addMarker}
          type="toilet"/>
        </Popup>
    </Marker>
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

            <Marker position={marker.thispos} key={props.markers.indexOf(marker)}>
              <Popup>
                 
              <PopupContainer marker={marker} />

              </Popup>
            </Marker>
        ))
        }

      </MapContainer>
  )}

export default MapComponent