import { MapContainer, TileLayer, Marker, Popup , useMap, useMapEvents} from 'react-leaflet';
import React, { useEffect, useState } from "react";
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
                .then(r => props.addMarker([r.latitude, r.longitude]))}> 
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
  return (
      <MapContainer center={props.pos} zoom={props.zoom} id="map">
        <TileLayer
            attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"/>
        <LocationMarker />
        <ClickEvent addMarker={props.addMarker}/>
        { props.markers?.map( marker => (
            <Marker position={marker} key={ props.markers.indexOf(marker)}>
              <Popup>
                Latitude: {marker[0].toFixed(3)} <br /> 
                Longitude: {marker[1].toFixed(3)}
              </Popup>
            </Marker>
        ))
        }
        
      </MapContainer>
  )}

export default MapComponent